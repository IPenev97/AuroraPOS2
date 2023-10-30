package com.sistechnology.aurorapos2.core.fp_comm.legacy.Datecs;

import android.util.Log;


import com.sistechnology.aurorapos2.App;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.CommandSetFP;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.PrinterFunctions;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.CommonFn;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.VAT_SUMS;
import com.sistechnology.aurorapos2.core.utils.DateUtil;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MARIELA on 5.5.2017 г..
 */

public class DatecsDP25x extends CommandSetFP
{
    @Override
    public void InitParams()
    {
        fiscal = true;
        password = "1";
        STX = 0x01;
        ETX = 0x03;

        seq = 0x20;
        taxLetter[0] = '1';
        taxLetter[1] = '2';
        taxLetter[2] = '3';
        taxLetter[3] = '4';
        paymentLetter[0] = '0';
        paymentLetter[1] = '3';
        paymentLetter[2] = '2';
        paymentLetter[3] = '1';
        paymentLetter[4] = '4';
        paymentLetter[5] = '5';
        paymentLetter[6] = ' ';
        paymentLetter[7] = ' ';
        paymentLetter[8] = ' ';
        paymentLetter[9] = ' ';
        paymentLetter[10] = ' ';
        separator = "\t";
    }
    public DatecsDP25x() { super(); }

    @Override
    public void initStatusFP(){ statusFP = new DatecsDP25xStatusFP(); }

    @Override
    public byte[] packCommand(byte cmd, String data)
    {
        //                 STX   len x 4    seq      cmd            data        postAmble  BCC 1 BCC 2 BCC 3 BCC 4  ETX
        //                 01  30 30 32 3F   24   30 30 32 3A    54 65 73 74 09     05       30    33   36    3F    03

        int i;
        byte[] bytes = new byte[488];
        //int offs = 0;
        int crc = 0;
        int len = data != null ? data.length() : 0;
        if (len > 488) {
            throw new IllegalArgumentException("Lenght of the packet exceeds the limits!");
        }

        comm.lenToSend = 0;
        int alllen = len + 32;
        alllen += 10;

        seq++;
        if(seq > 0xdf)
            seq = 0x20;

        bytes[comm.lenToSend++] = STX;
        bytes[comm.lenToSend++] = (byte)((alllen >> 12 & 15) + 48);
        bytes[comm.lenToSend++] = (byte)((alllen >> 8 & 15) + 48);
        bytes[comm.lenToSend++] = (byte)((alllen >> 4 & 15) + 48);
        bytes[comm.lenToSend++] = (byte)((alllen >> 0 & 15) + 48);
        bytes[comm.lenToSend++] = (byte)seq;
        bytes[comm.lenToSend++] = (byte)((cmd >> 12 & 15) + 48);
        bytes[comm.lenToSend++] = (byte)((cmd >> 8 & 15) + 48);
        bytes[comm.lenToSend++] = (byte)((cmd >> 4 & 15) + 48);
        bytes[comm.lenToSend++] = (byte)((cmd >> 0 & 15) + 48);
        for(i = 0; i < data.length(); i++)
            bytes[comm.lenToSend++] = CommonFn.getCyrByte(data.charAt(i));
        //toAnsi(data, bytes, lenToSend, this.mEncoding); // todo
        bytes[comm.lenToSend++] = postAmble;

        calcBCC(bytes);

        for(i = 0; i<4; i++)
            bytes[comm.lenToSend++] = bccBytes[i];

        bytes[comm.lenToSend++] = ETX;

        return bytes;
    }

    @Override
    public byte[] calcBCC(byte[] bytes)
    {
        int i, BCC = 0;
        int pos;
        int crc = 0;

        for(i=0; i<4; i++)
            bccBytes[i]=0;

        if(bytes.length < 3)
            return bccBytes; // error

        for(pos = 1; pos < comm.lenToSend; ++pos) {
            crc += bytes[pos] & 255;
            if(bytes[pos] == postAmble)
                break;
        }

        bccBytes[0] = (byte)((crc >> 12 & 15) + 0x30);
        bccBytes[1] = (byte)((crc >> 8 & 15) + 0x30);
        bccBytes[2] = (byte)((crc >> 4 & 15) + 0x30);
        bccBytes[3] = (byte)((crc >> 0 & 15) + 0x30);

        return bccBytes;
    }
    public int getLenFromAnswer() {
        int i;
        // LEN == bytes[1..4]
        int len = 0;

        for(i = 1; i < 5; ++i) {
            len = (len << 4) + answer[i] - 0x30;
        }

        return len - 0x20;
    }

    @Override
    public boolean checkFormat()
    {
        //                                              postAmble2                  postAmble
        // <01>     <LEN>     <SEQ> <CMD>        <DATA>   <04>   <STATUS>              <05>     <BCC>    <03>
        // Example:
        //   01  30 30 33 35   24     30    30 32 3A 30 09 04   80 80 A0 80 86 9A 80 80 05   30 36 33 3A  03

        int i;
        // LEN == bytes[1..4]
        int len = getLenFromAnswer();

        if (len < 9 || len >= answer.length - 5)
            return false;

        //   0   1 2 3 4   5   6 7 8 9      10      length-9     length-8     length    length + 1 length + 5
        // STX     LEN    SEQ    CMD       DATA     postAmble2    STATUS     postAmble    BCC          ETX
        if (answer[0] != STX ||
            (answer[5] & 0xffl) != seq ||
            answer[len - 9] != postAmble2 ||
            answer[len] != postAmble ||
            answer[len + 5] != ETX) {
            // error in format

            String output = "";
            for (int ii=0; ii < len + 6; ii++) {
                output += String.format("%02x ", answer[ii]);

            }
            Log.i("FPComm check format", output);

            Log.i("FPComm check format", "len = " + len + " answer[len] = " + String.format("%02x ", answer[len]));

            if(answer[0] != STX)
                Log.i("FPComm check format", "answer[0] != STX");
            if((answer[5] & 0xffl) != seq)
                Log.i("FPComm check format", "(answer[5] & 0xffl) != seq");
            if(answer[len - 9] != postAmble2)
                Log.i("FPComm check format", "answer[len - 9] != postAmble2");
            if(answer[len] != postAmble)
                Log.i("FPComm check format", "answer[len] != postAmble");
            if(answer[len + 5] != ETX)
                Log.i("FPComm check format", "answer[len + 5] != ETX");

            return false;
        }
        return true;
    }

    @Override
    public boolean analyzeAnswer(String charsetName)
    {
        int i;
        int lenLen = 4;
        int lenSeq = 1;
        int lenCmd = 4;
        int lenSpec4 = 1;
        int lenStatus = 8;
        int lenSpec5 = 1;

        // <01><LEN><SEQ><CMD><DATA><04><STATUS><05><BCC><03>
        int startStatus = 1 + getLenFromAnswer() - lenStatus - lenSpec5;
        int startData = 1 + lenLen + lenSeq + lenCmd;
        int lenData = getLenFromAnswer() - lenLen - lenSeq - lenCmd - lenSpec4 - lenStatus - lenSpec5;

        fpError = 0;

        for(i=0; i < 6; i++)
        {
            // status bytes 7 и 8 не се ползват! резервирани
            statusFP.statusBytes[i] = answer[startStatus + i];
        }
        if(getError())
            return false;

        ansItemNum = 0;
        ansItems = null;

        // data
        if(lenData > 0) {
            String str;
            byte[] bytes = new byte[lenData];

            for (i = 0; i < lenData; i++)
                bytes[i] = answer[startData + i];

            if(charsetName != null && !charsetName.equals(""))
                try
                {
                    str = new String(bytes, Charset.forName(charsetName));
                }
                catch (UnsupportedCharsetException ex)
                {
                    str = new String(bytes, Charset.defaultCharset());
                }
            else
                str = new String(bytes, Charset.defaultCharset());
            ansItems = str.split("\t");

            ansItemNum = ansItems.length;

            if(!ansItems[0].equals("0"))
                fpError = CommonFn.ParseInt(ansItems[0]);
            else
            {
                for(i = 0; i < ansItemNum - 1; i++ )
                    ansItems[i] = ansItems[i + 1];
                ansItems[ansItemNum - 1] = null;
                ansItemNum--;
            }
        }
        return fpError == 0;
    }

    @Override
    public boolean fpGetDiagInfo(boolean shooting) {
        // 90 (5Ah) ДИАГНОСТИЧНА ИНФОРМАЦИЯ
        // Област за данни:	{Calculate}
        // Отговор:		{FirmwareRev}{Space}{FirmwareDate}{Space}{FirmwareTime},{ChekSum},{Sw},{Country},{SerNum},{FM}

        boolean res = false;

        if (!checkComm())
            return false;

        if (comm.request((byte) 0x5A, "1\t")) {
            res = analyzeAnswer();
            if (res) {
                if (ansItemNum >= 6) {
                    setVersion(ansItems[0]);
                    setSerial(ansItems[ansItemNum - 2]);
                    setFm_num(ansItems[ansItemNum - 1]);
                }
            }
        }
        return res;
    }

    @Override
    public boolean fpGetStatus()
    {
        // 74 (4Ah) СТАТУС НА ФУ
        // Област за данни:	няма данни
        // Отговор:		{S0}{S1}{S2}{S3}{S4}{S5}

        if(!checkComm(true))
            return false;
        boolean res = false;
        if(comm.request((byte)0x4a, ""))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 1)
                    for(int i=0; i < 6; i++)
                    {
                        statusFP.statusBytes[i] = answer[i+11+1];
                    }
                else
                    res = false;
            }
        }

        return true;
    }

    @Override
    public boolean fpGetInfo() {
        // 110 (6Eh) ИНФОРМАЦИЯ ЗА ДЕНЯ
        // Данни:	[Type] 0 - продажби; 1 - сторна
        // Отговор:	 	ErrCode[,<Cash>,<Credit>,<Debit>,<Pay4>,<Pay5>,<Pay6>,<Rsrv1>,<Rsrv2>,<Closure>,<Receipt>]

        boolean res = false;
        int nBonFisc = 0;

        fpError = 0;

        if(noCommSetError())
            return false;

        if(!checkComm())
            return false;

        // за продажби, не-сторна
        if(comm.request((byte)0x40, "0\t")) {
            res = analyzeAnswer();
            if (res) {
                if (ansItemNum >= 5)
                    setClosure(CommonFn.ParseInt(ansItems[0]) + 1);
            }
        }


        if(comm.request((byte)0x6E, "2\t")) {
            res = analyzeAnswer();
            if (res) {
                // Receipt
                nBonFisc = (int) CommonFn.ParseDouble(ansItems[0]) + 1; // брой клиенти (издадени бонове)
            }
        }

        if(comm.request((byte)0x6E, "0\t")) {
            res = analyzeAnswer();
            if (res)
            {
                // плащания за деня
//                PrinterFunctions.cashData.paymentsCash = CommonFn.ParseLong(ansItems[0]);
//                PrinterFunctions.cashData.paymentsCredit = CommonFn.ParseLong(ansItems[1]);
//                PrinterFunctions.cashData.paymentsAccount = CommonFn.ParseLong(ansItems[2]);
//                PrinterFunctions.cashData.paymentsCheque = CommonFn.ParseLong(ansItems[3]);
            }
        }

        // за сторна
        if(comm.request((byte)0x6E, "1\t"))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 5)
                {
                    // плащания за деня
//                    PrinterFunctions.cashData.paymentsCash -= CommonFn.ParseLong(ansItems[0]);
//                    PrinterFunctions.cashData.paymentsCredit -= CommonFn.ParseLong(ansItems[1]);
//                    PrinterFunctions.cashData.paymentsAccount -= CommonFn.ParseLong(ansItems[2]);
//                    PrinterFunctions.cashData.paymentsCheque -= CommonFn.ParseLong(ansItems[3]);
                }
            }
        }

        // todo check
        if(comm.request((byte)0x46, "0\t0.00\t"))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 3)
                {
//                    PrinterFunctions.cashData.cashAvail = CommonFn.ParseDouble(ansItems[0]);
//                    PrinterFunctions.cashData.paymentsCashIn = CommonFn.ParseDouble(ansItems[1]);
//                    PrinterFunctions.cashData.paymentsCashOut = CommonFn.ParseDouble(ansItems[2]);
                }
            }
        }

        if(res)
            setBonFisc(nBonFisc);

        return res;
    }

    @Override
    public String getClosureParam() { return "0\t"; }
    @Override
    public int getClosureIndexParam() { return 0; }

    @Override
    public boolean fpOpenReceipt(int TermCode, int opCode, byte recType) {
        // 48 (30h) НАЧАЛО НА ФИСКАЛЕН БОН
        // Данни: {OpCode}<SEP>{OpPwd}<SEP>{TillNmb}<SEP>{Storno}<SEP>{DocNum}<SEP>{DateTime}<SEP>{FM Number}<SEP>{Invoice}<SEP>{ToInvoice}<SEP>{Reason}<SEP>{NSale}<SEP>
        // Отговор: <ErrCode>
        // или
        // <AllReceipt>,<FiscalReceipt>

        fpError = 0;

        if (noCommSetError())
            return false;

        boolean res = false;
        byte cmd = (byte) 0x30;
        String data = "";
        String usnTabbed = (usn.equals("") ? "" : ("\t" + usn));

        int trials = 2;

        Date date = null;
        if (recType == FP_RETURN_RECEIPT || recType == FP_KI_RECEIPT) {
//            // DD-MM-YY hh:mm:ss
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
//            try {
//                date = dateFormat.parse(App.getCtrl().currentPrintData.getBonParent().originalReceiptDate + " " +
//                                        App.getCtrl().currentPrintData.getBonParent().originalReceiptTime);
//            } catch (ParseException e) {
//                return false;
//            }
        }

        switch (recType) {
            case FP_FISCAL_RECEIPT:
                data = String.format("%d\t%s%s\t%d\t\t", 1, password, usnTabbed, TermCode);
                break;
            case FP_INVOICE_RECEIPT:
                data = String.format("%d\t%s%s\t%d\tI\t", 1, password, usnTabbed, TermCode);
                break;
            case FP_RETURN_RECEIPT:
            case FP_KI_RECEIPT:
                data = String.format("%d\t%s\t%d", 1, password, TermCode);
                break;
            case FP_SERVICE_RECEIPT:
                cmd = (byte) 0x26;
                break;
        }

//        long originalBonNum = CommonFn.ParseLong(App.getCtrl().currentPrintData.getBonParent().originalBonGlobal);
//        if(originalBonNum==0)
//            originalBonNum = CommonFn.ParseLong(App.getCtrl().getCurrentBon().getOriginalBonGlobal());
//        if(originalBonNum==0)
//            originalBonNum = 1;
//
//        switch (recType) {
//            case FP_RETURN_RECEIPT:
//            case FP_KI_RECEIPT:
//                cmd = (byte) 0x2B;
//                data += String.format("\t%d\t%d\t%s\t%s",
//                                      App.getCtrl().currentPrintData.getBonParent().reversalReason,
//                                      originalBonNum,
//                                      (new SimpleDateFormat("dd-MM-yy HH:mm:ss", Locale.US)).format(date),
//                                      fm_num);
//
//                if (recType == FP_KI_RECEIPT)
//                    // [,<Invoice>,<InvNumber>,<Reason>]
//                    data += String.format("\tI\t%s\t%s",
//                                          App.getCtrl().currentPrintData.getBonParent().originalInvoiceNumber,
//                                          " ");
//                else
//                    data += "\t\t\t";
//
//                if(usn.equals(""))
//                    usnTabbed = "\t" + stornoUSNEmpty;
//
//                data += String.format("%s\t", usnTabbed);
//
//                break;
//        }

        //LogToMail.AddToLog("fpOpenReceipt " + "fiscal No." + getBonFisc() + " usn " + usn);

        // {OpCode}<SEP>{OpPwd}<SEP>{NSale}<SEP>{TillNmb}<SEP>{Invoice}<SEP> или
        // {OpCode}<SEP>{OpPwd}<SEP>{TillNmb}<SEP>{Storno}<SEP>{DocNum}<SEP>{DateTime}<SEP>{FM Number}<SEP>{Invoice}<SEP>{ToInvoice}<SEP>{Reason}<SEP>{NSale}<SEP>
        if (comm.request(cmd, data)) {
            res = analyzeAnswer();
            if (res) {
                if (ansItemNum >= 1)
                    setBonGlobal(CommonFn.ParseLong(ansItems[0]));
            }
        }

//        if(!res)
//            LogToMail.AddToLog("Datecs 25x: fpOpenReceipt: " + fpError);


        return res;
    }

    @Override
    public boolean fpCancelReceipt() {
        // 3Ch (60) АНУЛИРАНЕ НА БОН
        // Данни: 	Няма данни
        // Отговор: 		Няма данни

        boolean res = false;

        fpError = 0;

        if (noCommSetError())
            return false;

        if (comm.request((byte) 0x3C, "")) {
            res = analyzeAnswer();
        }

        return res;
    }


    @Override
    public boolean fpCloseReceipt(boolean fFiscal)
    {
//        if(App.getCtrl().getCurrentPrintBon().client != null && client != null)
//        {
//            // 39h (57) ПЕЧАТ НА ИНФОРМАЦИЯ ЗА КЛИЕНТА
//            // Данни: <EIK><Tab><EIKType>[<Tab><Seller>[<Tab><Receiver>[<Tab><Client>[<Tab><TaxNo>[<Tab><Address>]]]]]
//            // Отговор: <ErrCode>
//            //  Задължителни параметри:
//            //EIK ЕИК номер на купувача. Между 9 и 14 символа
//            //Tab Табулация (09H). Разделител между параметрите
//            //EIKType ЕИК тип. 0 - Булстат, 1 - ЕГН, 2 - ЛНЧ, 3 - Сл. номер
//            //
//            // 4 символа.
//            //  Address Адрес на купувача. До два реда текст максимално от 36 символа, разделени с LF(0AH).
//
//            boolean res = false;
//            fpError = 0;
//            if (noCommSetError())
//                return false;
//
//            String data = "";
//            String vatNumber = client.getVATNumber() != null ? client.getVATNumber() : "";
//            if(vatNumber.equals("") || vatNumber.length() < 10)
//                vatNumber = "BG" + client.getRegNo();
//
//            if(vatNumber.length() > 14)
//                vatNumber = vatNumber.substring(0,14);
//
//
//            if(client.getEikType() == 1 && client.getRegNo().length() != 10)
//                if(client.getRegNo().length() == 9 || client.getRegNo().length() == 13)
//                    client.setEikType(0);
//                else
//                    client.setEikType(3);
//
//            // {Seller}<SEP>{Receiver}<SEP>{Buyer}<SEP>{Address1}<SEP>{Address2}<SEP>{TypeTAXN}<SEP>{TAXN}<SEP>{VATN}<SEP>
//            data +=  "\t" + "\t" + client.getName() + "\t" + getValidAddress() + "\t" + client.getEikType() + "\t" +
//                    client.getRegNo() + "\t" + vatNumber + "\t";
//
//            if (comm.request((byte) 0x39, data))
//                res = analyzeAnswer();
//
//
//        }

        return super.fpCloseReceipt(fFiscal);
    }

    @Override
    public boolean fpGetCurrentZData() {
        // 65 (41h) ТЕКУЩИ НЕТНИ/ОБЩИ СУМИ
        // Област за данни:	[Type]{SEP}
        // Отговор:	Zlate,Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7, Tax8

        boolean res = false;
        int i;

        if(!checkComm())
            return false;

        if(comm.request((byte)0x41, "0\t")) // общо
        {
            res = analyzeAnswer();
            if(res)
            {
//                if(ansItemNum >= VAT_SUMS.numVats + 1)
//                {
//                    PrinterFunctions.cashData.closure = getClosure();
//                    for(i = 0; i < VAT_SUMS.numVats; i++)
//                    {
//                        PrinterFunctions.cashData.vatSumsBrutto.sum[i] = CommonFn.ParseDouble(ansItems[i + 1]);
//                    }
//                }
            }
        }

        if(comm.request((byte)0x41, "1\t")) // ДДС-та
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= VAT_SUMS.numVats + 1)
                {
                    PrinterFunctions.cashData.closure = getClosure();
                    for(i = 0; i < VAT_SUMS.numVats; i++)
                    {
                        PrinterFunctions.cashData.vatSumsNetto.sum[i] = PrinterFunctions.cashData.vatSumsBrutto.sum[i] - CommonFn.ParseDouble(ansItems[i + 1]);
                    }
                }
            }
        }

        if(comm.request((byte)0x41, "2\t")) // сторно
        {
            res = analyzeAnswer();
            if(res && ansItemNum >= 1)
            {
                if(ansItemNum >= VAT_SUMS.numVats + 1)
                {
                    for(i = 0; i < VAT_SUMS.numVats; i++)
                    {
                        PrinterFunctions.cashData.vatSumsStorno.sum[i] = CommonFn.ParseDouble(ansItems[i + 1]);
                    }

                    res = true;
                }
//
//                LogToMail.AddToLog(String.format("Datecs 25x storno = %f", PrinterFunctions.cashData.vatSumsStorno.Total()));
            }
        }

        PrinterFunctions.cashData.numStorno = 0;
        for(int op = 1; op < 2/*31*/; op++) // използвали сме само оператор 1!!!
            if(comm.request((byte)0x70, String.valueOf(op)+'\t')) // Информация за сторно операции
            {
                res = analyzeAnswer();
                if(res)
                {
                    // За Type = 1: ErrCode[,<Receipts>,<Total>,<SReceipts>,<STotal>,<nDiscount>,<Discount>,<nSurcharge>,<Surcharge>,<nVoid>,<Void>]
                    if(ansItemNum >= 1 && !ansItems[0].equals("F"))
                    {
                        PrinterFunctions.cashData.numStorno += CommonFn.ParseInt(ansItems[2]);
//                        LogToMail.AddToLog(String.format("numStorno for op %d = %s", op, ansItems[2]));
                    }
                }
                else
                    break;
            }
            else
                break;

//        LogToMail.AddToLog(String.format("Datecs 25x numStorno %d, sumStorno %f", PrinterFunctions.cashData.numStorno, PrinterFunctions.cashData.vatSumsStorno.Total()));


        return res;
    }

    @Override
    public boolean fpPrintBonFromKLEN(int start, int end)
    {
        // Command: 125 (7Dh) Information from EJ
        // Parameters of the command:
        // Syntax 1:
        // {Option}<SEP>{DocNum}<SEP>{RecType}<SEP>
        //                  Syntax 2 ( read CSV data):
        // {Option}<SEP>{FirstDoc}<SEP>{LastDoc}<SEP>
        //                  Syntax 3 ( read CSV data):
        // {Option}<SEP>


        boolean res = false;
        String data;

        if(!checkComm(true))
            return false;

        for(int i = start; i <= end; i++)
        {
            data = String.format("3\t%d\t0\t", i);
            res = comm.request((byte) 0x7D, data);
            if (res) res = analyzeAnswer();
            if (!res)
                break;
        }
        return res;
    }

    @Override
    public boolean fpArticleSell(String name, double dPrice, int bVatGr, double dQuan,
                                 double dPercDiscount, double dDiscount,
                                 String linePre, String linePost,
                                 byte display, int departmentList) {
        // 49
        // Област за данни:	{PluName}<SEP>{TaxCd}<SEP>{Price}<SEP>{Quantity}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>{Department}<SEP>
        // Отговор:		Няма данни
        //                   [<L1>][<Lf><L2>]<Tab><TaxCd><[Sign]Price>[*<Qwan>][,Perc|;Abs]
        // или
        //                   [<L1>][<Lf><L2>]<Tab><Dept><Tab><[Sign]Price>[*<Qwan>][,Perc|;Abs]

        boolean res = false;

        fpError = 0;

        if(noCommSetError())
            return false;

        if(Math.abs(dPrice) < 0.001)
            return false; // нулева цена
        if(Math.abs(dQuan) < 0.001)
            dQuan = 1.0;

//        int coefStorno = (dQuan < -0.0001 && App.getCtrl().getCurrentPrintBon().isfReversal() ? -1 : 1);

//        if(false && App.getCtrl().getCurrentPrintBon().isInvoice())
//        {
//            // dPrice е сумата без ДДС, да се подаде dPice * (1 + vat)
//            VAT vat = CommonFn.getVatObject(bVatGr + 1);
//            if(vat != null)
//                dPrice *= (1 + vat.getPerc());
//        }

        String strPrice = String.format("%.2f",dPrice).replaceAll(",", ".");
        String strQuan = String.format("%.3f", /* coefStorno * */ dQuan).replaceAll(",", ".");
        String strPercent = String.format("%.2f",dPercDiscount).replaceAll(",", ".");
        String strDiscount = String.format("%.2f",dDiscount).replaceAll(",", ".");

        //{PluName}<SEP>{TaxCd}<SEP>{Price}<SEP>{Quantity}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>{Department}<SEP>
        //    sprintf(szParams,"%s\t%d\t%.2f\t%.3f\t\t\t%d\t",
        //	   sz,
        //	   g_toupper_bg(cTxCd) - 'À' + 1,
        //	   /*dPrice*/ (dQuan < -0.0001 ? /*сторно:*/ fabs(dPrice) : /*нормална продажба или войд:*/dPrice),
        //	   g_RoundN(fabs(dQuan), 3),
        //	   department);
        String data;
        data = String.format("%s\t%c\t%s\t%s", name, taxLetter[bVatGr],strPrice,strQuan);

        if(Math.abs(dPercDiscount) > 0.0001)
            if(dPercDiscount > 0)
                data += String.format("\t1\t%s",strPercent); // surcharge
            else
                data += String.format("\t2\t%s",strPercent);
        else
            if(Math.abs(dDiscount) > 0.0001)
                if(dDiscount > 0)
                    data += String.format("\t3\t%s",strDiscount); // surcharge
                else
                    data += String.format("\t4\t%s",strDiscount);
            else
                data += "\t\t";

        data += "\t0\t";

        if(comm.request((byte)0x31, data))
            res = analyzeAnswer();

        return res;
    }

    @Override
    public boolean fpGetGlobalBon()
    {
        boolean res = false;

        fpError = 0;

        if(noCommSetError())
            return false;

        res = comm.request((byte)0x4C, "");
        if(res)
        {
            res = analyzeAnswer();
            if(res)
                if(ansItemNum >= 1)
                    // {ErrorCode}<SEP>{IsOpen}<SEP>{Number}<SEP>{Items}<SEP>{Amount}<SEP>{Payed}<SEP>
                    setBonGlobal(CommonFn.ParseLong(ansItems[1]) + (isReceiptOpen() ? 0 : 1)); // ако бонът е затворен, връща номера на предходния бон, ако е отворен - текущия
        }

        return res;
    }

    public boolean fpPayment(double dPayValue, byte bPayType, byte display, String line) {
        // 53 (35h) ОБЩА СУМА (ТОТАЛ)
        // Област за данни:	{PaidMode}<SEP>{Amount}<SEP>{Type}<SEP>
        // Отговор:		{ErrorCode}<SEP>{Status}<SEP>{Amount}<SEP>

        // '0' - cash;
        // '1' - credit card;
        // '2' - debit card;
        // '3' - other pay#3
        // '4' - other pay#4
        // '5' - other pay#5

        boolean res = false;

        fpError = 0;

        if(noCommSetError())
            return false;

//        int coefStorno = (dPayValue < -0.0001 && App.getCtrl().getCurrentPrintBon().isfReversal() ? -1 : 1);

        String strPayValue = String.format("%.2f", /* coefStorno * */ dPayValue).replaceAll(",", ".");
        String data = String.format("%c\t%s\t1\t", paymentLetter[bPayType], strPayValue);

        if(comm.request((byte)0x35, data))
        {
            res = analyzeAnswer();
            if(res && ansItemNum >= 1)
            {
                // {ErrorCode}<SEP>{Status}<SEP>{Amount}<SEP>
                double dRest = CommonFn.ParseDouble(ansItems[1]);
            }
        }

        return res;
    }

    @Override
    public long fpGetInvRange(boolean fReversal) {
        boolean res = false;

        if (noCommSetError())
            return 0;

        long current = 0;

//        LogToMail.AddToLog("fpGetInvRange");

        // номер на следващата фактура
        if (comm.request((byte) 0x42, "")) {
            if (analyzeAnswer()) {
                if (ansItemNum >= 3) {
                    invRangeStart = CommonFn.ParseLong(ansItems[0]);
                    invRangeEnd = CommonFn.ParseLong(ansItems[1]);
                    invNum = ansItems[2];
                }
            }
        }

        Log.i("GET_INV_RANGE:", String.valueOf(current));
        return current;
    }

    @Override
    public boolean fpSetInvRange(boolean fReversal, long start, long end) {
        // input: <StartNum[10]> <;> <EndNum[10]>
        // output: ACK

        boolean res = false;
        long current = 0;

        fpError = 0;

        if (noCommSetError())
            return false;

        if(start == 0 && end == 0)
            start = 1;
        if(start == end)
            end++;

        String data = String.format("%010d\t%010d\t", start, end);
        if (comm.request((byte) 0x42, data)) {
            if (analyzeAnswer()) {
                if (ansItemNum >= 3) {
                    invRangeStart = CommonFn.ParseLong(ansItems[0]);
                    invRangeEnd = CommonFn.ParseLong(ansItems[1]);
                    invNum = ansItems[2];

                    return invRangeStart <= invRangeEnd;
                }
            }
//            else
//                LogToMail.AddToLog(getLastErrorText());
        }
        return false;
    }

    @Override
    public boolean fpSubtotal(boolean print, double dTotal, double percent, double discountSum) {
        // 51
        // Област за данни:	{Print}<SEP>{Display}<SEP>{DiscountType}<SEP>{DiscountValue}<SEP>
        // Отговор:		SubTotal,Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7,Tax8,

        boolean res = false;

        fpError = 0;

        if(noCommSetError())
            return false;

        String data = (print ? "1" : "0") + "\t1\t";
        if(Math.abs(percent) > 0.001)
        {
            if(percent > 0)
                data += "1\t";
            else
                data += "2\t";

            data += String.format("%s\t", String.format("%.2f",Math.abs(percent)).replace(',', '.'));
        }
        else
            if(Math.abs(discountSum) > 0.001 && Math.abs(dTotal - discountSum) > 0.001)
            {
                if(discountSum > 0)
                    data += "3\t";
                else
                    data += "4\t";

                data += String.format("%s\t", String.format("%.2f", Math.abs(discountSum)).replace(',', '.'));
            }
            else
                data += "\t\t";

        if(comm.request((byte)0x33, data))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 9) {
                    for (int i = 0; i < 8; i++)
                        vatSums.sum[i] = CommonFn.ParseDouble(ansItems[i + 2]);

                    res = Math.abs(vatSums.Total() - CommonFn.ParseDouble(ansItems[1])) < 0.001;
                }

                if(res)
                {
                    if(Math.abs(CommonFn.ParseDouble(ansItems[1]) - dTotal) > 0.001)
                    {
                        // тоталите от ФП и програмата се различават
                        res = false;
                    }
                }

            }
        }

        return res;
    }

    @Override
    public boolean fpXReport()
    {
        setLastError("");
        // 69 (45h) ДНЕВЕН ФИНАНСОВ ОТЧЕТ С ИЛИ БЕЗ НУЛИРАНЕ
        // Област за данни:	[[Item]Option]
        // Отговор: 	[Closure,FM_Total,Total1,Total2,Total3,Total4,Total5,Total6,Total7, Total8]

        boolean res = false;

        if(!checkComm(true))
            return false;

        res = comm.request((byte)0x45, "X\t");
        if(res) res = analyzeAnswer();

        return res;
    }

    @Override
    public boolean fpZReport()
    {
        setLastError("");
        // 69 (45h) ДНЕВЕН ФИНАНСОВ ОТЧЕТ С ИЛИ БЕЗ НУЛИРАНЕ
        // Област за данни:	[[Item]Option]
        // Отговор: 	[Closure,FM_Total,Total1,Total2,Total3,Total4,Total5,Total6,Total7, Total8]

        boolean res = false;

        if(!checkComm(true))
            return false;

        res = comm.request((byte)0x45, "Z\t", 10000);
        if(res) res = analyzeAnswer();

        if(res && ansItemNum >= 17)
        {
            setClosure(CommonFn.ParseInt(ansItems[0]) + 1); // next closure
            setBonFisc(1);

            for(int i = 0; i < VAT_SUMS.numVats && (i+1 < ansItemNum); i++)
                vatSums.sum[i] = CommonFn.ParseDouble(ansItems[i + 1]) - CommonFn.ParseDouble(ansItems[i + 1 + 8]);

            fpSetPayments();
        }

        return res;
    }
    @Override
    public String getLastErrorText(int err)
    {
        return ErrorCodes.getErrorTextV2(err);
    }

    @Override
    public int readLastError() {
        return fpError;
    }

    @Override
    public boolean fpGetCurrentVATRates(VAT_SUMS vatSum)
    {
        // ТЕКУЩИ ДАНЪЧНИ СТАВКИ
        // Област за данни:	Няма данни
        // Отговор:		{ErrorCode}<SEP>{nZreport}<SEP>{TaxA}<SEP>{TaxB}<SEP>{TaxC}<SEP>{TaxD}<SEP>{TaxE}<SEP>{TaxF}<SEP>{TaxG}<SEP>{TaxH}<SEP>{EntDate}<SEP>

        boolean res = false;
        int i;

        if(!checkComm())
            return false;

        if(comm.request((byte)0x32, ""))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 8 && ansItemNum >= VAT_SUMS.numVats)
                {
                    for(i = 0; i < VAT_SUMS.numVats; i++)
                    {
                        if(vatSum == null) {
                            PrinterFunctions.cashData.vatSumsBrutto.rate[i] = CommonFn.ParseDouble(ansItems[i + 1]);
                            PrinterFunctions.cashData.vatSumsNetto.rate[i] = CommonFn.ParseDouble(ansItems[i + 1]);
                            vatSums.rate[i] = CommonFn.ParseDouble(ansItems[i + 1]);
                        }
                        else
                            vatSum.rate[i] = CommonFn.ParseDouble(ansItems[i + 1]);
                    }
                }
            }
        }

        if(!res)
            fpError = readLastError();

        return res;
    }

    /************** reports **************/
    @Override
    public boolean fpReportByNumber(int start, int end)
    {
        return reportByNumber(1, start, end);
    }

    @Override
    public boolean fpReportByNumberShort(int start, int end)
    {
        return reportByNumber(0, start, end);
    }

    // Type - 0 - short; 1 - detailed;
    public boolean reportByNumber(int type, int start, int end)
    {
        setLastError("");
        // 95 (5Fh) СЪКРАТЕН ОТЧЕТ ОТ ФП ПО НОМЕР НА Z-ОТЧЕТ
        // Област за данни:	{Type}<SEP>{First}<SEP>{Last}<SEP>
        // Type - 0 - short; 1 - detailed;

        boolean res = false;
        String data = String.format("%d\t%d\t%d\t",type, start, end);

        if(!checkComm(true))
            return false;

        res = comm.request((byte)0x5F, data);
        if(res)
            res = analyzeAnswer();

        return res;
    }

    @Override
    public boolean fpReportByDateShort(String dtFrom, String dtTo)
    {
        return reportByDate(0, dtFrom, dtTo);
    }

    @Override
    public boolean fpReportByDate(String dtFrom, String dtTo) {
        return reportByDate(0, dtFrom, dtTo);
    }

    public boolean reportByDate(int type, String dtFrom, String dtTo) {

        // dt: DDMMYY -> DD-MM-YY

        if(dtFrom.length() == 6)
            dtFrom = dtFrom.substring(0, 2) + "-" + dtFrom.substring(2, 4) + "-" + dtFrom.substring(4, 6);
        if(dtTo.length() == 6)
            dtTo = dtTo.substring(0, 2) + "-" + dtTo.substring(2, 4) + "-" + dtTo.substring(4, 6);

        setLastError("");
        // 94 (5Eh) ОТЧЕТ ОТ ФП ПО ДАТА
        // Област за данни:	{Type}<SEP>{Start}<SEP>{End}<SEP>
        // Отговор:		Няма данни

        // StartDate 	 	Начална дата с дължина 6 символа (DD-MM-YY)
        // EndDate		Крайна дата с дължина 6 символа (DD-MM-YY)

        boolean res = false;
        String data = String.format("%d\t%s\t%s\t", type, dtFrom, dtTo);

        if(!checkComm(true))
            return false;

        res = comm.request((byte)0x5E, data);
        if(res)
            res = analyzeAnswer();

        return res;
    }

    @Override
    public double fpCashInOut(double amount)
    {
        // 70 (46h) СЛУЖЕБНО ВЪВЕДЕНИ И ИЗВЕДЕНИ СУМИ
        // Област за данни:	{Type}<SEP>{Amount}<SEP>
        //  Type - type of operation;
        // '0' - cash in;
        // '1' - cash out;
        // '2' - cash in - (foreign currency);
        // '3' - cash out - (foreign currency); Optional parameters: Amount - the sum ( 0.00...9999999.99 or 0...999999999 ) If Amount=0, the only Answer is returned, and receipt does not print.
        // Отговор:		{ErrorCode}<SEP>{CashSum}<SEP>{CashIn}<SEP>{CashOut}<SEP>

        setLastError("");

        boolean res = false;
        char type = (amount > 0.0001 ? '0' : '1');

        String data = type + "\t" + String.format("%.2f", Math.abs(amount)).replace(',', '.') + "\t";

        if(!checkComm(true))
            return -1;

        if(comm.request((byte)0x46, data))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 3)
                    return CommonFn.ParseDouble(ansItems[0]);
            }
        }

        return -1;
    }

    @Override
    public boolean fpSetDateTime(LocalDateTime date)
    {
        // 61 (3Dh) ВЪВЕЖДАНЕ НА ДАТА И ЧАС
        // Област за данни:	{DateTime}<SEP>
        //    DateTime -> DD-MM-YY hh:mm:ss DST
        // Отговор:	{ErrorCode}<SEP>

        boolean res = false;
        String data = (date == null ? DateUtil.Companion.getCurrentDateTimeWithPatternAndDate(LocalDateTime.now(),new SimpleDateFormat("dd-MM-yy HH:mm")) : (new SimpleDateFormat("dd-MM-yy HH:mm").format(date)));

        res = comm.request((byte)0x3D, data);
        if(res)
            res = analyzeAnswer();

        return res;
    }

    @Override
    public boolean fpPrintFiscalText(String text)
    {
        // 54 (36h) ПЕЧАТ НА ФИСКАЛЕН ТЕКСТ
        // Област за данни:	{Text}<SEP>Bold<SEP>Italic<SEP>DoubleH<SEP>Underline<SEP>alignment<SEP>
        // Отговор:		{ErrorCode}<SEP>

        boolean res;

        if(noCommSetError())
            return false;

        res = comm.request((byte)0x36, text + "\t");
        if(res)
            res = analyzeAnswer();

        return res;
    }

    @Override
    public boolean fpGetReceiptPayment()
    {
        // Parameters of the command: none
        // Answer: {ErrorCode}<SEP>{IsOpen}<SEP>{Number}<SEP>{Items}<SEP>{Amount}<SEP>{Payed}<SEP>

        boolean res = false;
        paidFP = 0;

        if(!checkComm(true))
            return false;

        res = comm.request((byte) 0x4C, "");
        if (res)
        {
            res = analyzeAnswer();
            if(res && ansItemNum >= 4)
                paidFP = CommonFn.ParseDouble(ansItems[4]);
        }

        return res;

    }

    @Override
    public int getValidAddressLength() { return 36; }
    @Override
    public String getValidAddressDelim() { return "\t"; }
    @Override
    public String getValidAddressDelim2() { return "\t"; }
}

