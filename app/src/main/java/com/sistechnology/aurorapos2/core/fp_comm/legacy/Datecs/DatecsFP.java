package com.sistechnology.aurorapos2.core.fp_comm.legacy.Datecs;

import android.util.Log;

import com.sistechnology.aurorapos2.core.fp_comm.legacy.CommandSetFP;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.PrinterFunctions;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.CommonFn;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.VAT_SUMS;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MARIELA on 5.5.2017 г..
 */

public class DatecsFP extends CommandSetFP
{
    @Override
    public void InitParams()
    {
        fiscal = true;
        password = "1";
        STX = 0x01;
        ETX = 0x03;

        seq = 0;
        taxLetter[0] = 'А';
        taxLetter[1] = 'Б';
        taxLetter[2] = 'В';
        taxLetter[3] = 'Г';
        paymentLetter[0] = 'P'; // в брой
        paymentLetter[1] = 'L'; // чек
        paymentLetter[2] = 'C'; // карта
        paymentLetter[3] = 'N'; // кредит
        paymentLetter[4] = ' ';
        paymentLetter[5] = ' ';
        paymentLetter[6] = ' ';
        paymentLetter[7] = ' ';
        paymentLetter[8] = ' ';
        paymentLetter[9] = ' ';
        paymentLetter[10] = ' ';

        separator = "";
    }
    public DatecsFP() { super(); }

    @Override
    public void initStatusFP(){ statusFP = new DatecsStatusFP(); }

    @Override
    public boolean fpGetDiagInfo(boolean shooting) {
        // 90 (5Ah) ДИАГНОСТИЧНА ИНФОРМАЦИЯ
        // Област за данни:	{Calculate}
        // Отговор:		{FirmwareRev}{Space}{FirmwareDate}{Space}{FirmwareTime},{ChekSum},{Sw},{Country},{SerNum},{FM}

        boolean res = false;

        if (!checkComm())
            return false;

        //LogToMail.AddToLog("fpGetDiagInfo");

        if (comm.request((byte) 0x5A, "1")) {
            res = analyzeAnswer();
            if (res) {
                if (ansItemNum >= 6) {
                    setVersion(ansItems[0]);
                    setSerial(ansItems[ansItemNum - 2]);
                    setFm_num(ansItems[ansItemNum - 1]);
                }
            }
        }

//        if (!res)
//            LogToMail.AddToLog("ERROR: fpGetDiagInfo");

        return res;
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
        if(comm.request((byte)0x6E, "0"))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 5)
                {
                    // Closure
                    setClosure(CommonFn.ParseInt(ansItems[ansItemNum - 2]));
                    // Receipt
                    nBonFisc = (int) CommonFn.ParseDouble(ansItems[ansItemNum - 1]); // Номер на следващия фискален бон

                    // плащания за деня
//                    PrinterFunctions.cashData.paymentsCash = CommonFn.ParseLong(ansItems[0]) / 100.0;
//                    PrinterFunctions.cashData.paymentsCredit = CommonFn.ParseLong(ansItems[1]) / 100.0;
//                    PrinterFunctions.cashData.paymentsAccount = CommonFn.ParseLong(ansItems[2]) / 100.0;
//                    PrinterFunctions.cashData.paymentsCheque = CommonFn.ParseLong(ansItems[5]) / 100.0;
                }
            }
        }

        // за сторна
        if(comm.request((byte)0x6E, "1"))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 5)
                {
                    // плащания за деня
//                    PrinterFunctions.cashData.paymentsCash -= CommonFn.ParseLong(ansItems[0]) / 100.0;
//                    PrinterFunctions.cashData.paymentsCredit -= CommonFn.ParseLong(ansItems[1]) / 100.0;
//                    PrinterFunctions.cashData.paymentsAccount -= CommonFn.ParseLong(ansItems[2]) / 100.0;
//                    PrinterFunctions.cashData.paymentsCheque -= CommonFn.ParseLong(ansItems[5]) / 100.0;
                }
            }
        }

        // todo да се пробва 0x46 с нулева сума за четене на наличността

        if(comm.request((byte)0x46, "0.00"))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 3)
                {
//                    PrinterFunctions.cashData.cashAvail = CommonFn.ParseDouble(ansItems[0]) / 100.0;
//                    PrinterFunctions.cashData.paymentsCashIn = CommonFn.ParseDouble(ansItems[1]) / 100.0;
//                    PrinterFunctions.cashData.paymentsCashOut = CommonFn.ParseDouble(ansItems[2]) / 100.0;
                }
            }
        }



//        if(!res)
//            LogToMail.AddToLog("ERROR: fpGetInfo");
//        else
//            setBonFisc(nBonFisc);

        return res;
    }

    @Override
    public String getClosureParam() { return "0"; }
    @Override
    public int getClosureIndexParam() { return 1; }


    @Override
    public boolean fpOpenReceipt(int TermCode, int opCode, byte recType) {
        // 48 (30h) НАЧАЛО НА ФИСКАЛЕН БОН
        // Данни: <OpCode>,<OpPwd>,<NSale>,<TillNmb>[,<Invoice>]
        // Отговор: <ErrCode>
        // или
        // <AllReceipt>,<FiscalReceipt>

        fpError = 0;

        if (noCommSetError())
            return false;

        boolean res = false;
        byte cmd = (byte) 0x30;
        String data = "";

        Date date = null;
        if (recType == FP_RETURN_RECEIPT || recType == FP_KI_RECEIPT) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            /* try {
                date = dateFormat.parse(App.getCtrl().currentPrintData.getBonParent().originalReceiptDate + " " +
                                        App.getCtrl().currentPrintData.getBonParent().originalReceiptTime);
            } catch (ParseException e) {
                return false;
            } */
        }

        switch (recType) {
            case FP_FISCAL_RECEIPT:
                if(usn.equals(""))
                    data = String.format("%d,%s,%d", 1, password, TermCode);
                else
                    data = String.format("%d,%s,%s,%d", 1, password, usn, TermCode);
                break;
            case FP_RETURN_RECEIPT:
            case FP_KI_RECEIPT:
                if(usn.equals(""))
                    data = String.format("%d,%s,%s,%d", 1, password, stornoUSNEmpty, TermCode);
                else
                    data = String.format("%d,%s,%s,%d", 1, password, usn, TermCode);
                break;
            case FP_INVOICE_RECEIPT:
                if(usn.equals(""))
                    data = String.format("%d,%s,%d,I", 1, password, TermCode);
                else
                    data = String.format("%d,%s,%s,%d,I", 1, password, usn, TermCode);
                break;
            case FP_SERVICE_RECEIPT:
                cmd = (byte) 0x26;
                break;
        }

       /* switch (recType) {
            case FP_RETURN_RECEIPT:
            case FP_KI_RECEIPT:
                // Данни: ...,<DocType>,<DocNumber>,<DocDateTime>,<FMNumber>
                // Отговор: <ErrCode>
                // или
                //          <AllReceipt>,<StrReceipt>
                cmd = (byte) 0x2E;
                data += String.format(",%d,%d,%s,%s",
                                      App.getCtrl().currentPrintData.getBonParent().reversalReason,
                                      CommonFn.ParseLong(App.getCtrl().currentPrintData.getBonParent().originalBonGlobal),
                                      (new SimpleDateFormat("ddMMyyHHmmss", Locale.US)).format(date),
                                      fm_num);
                break;
        }
        if (recType == FP_KI_RECEIPT)
            // [,<Invoice>,<InvNumber>,<Reason>]
            data += String.format(",I,%s,%s",
                                  App.getCtrl().currentPrintData.getBonParent().originalInvoiceNumber,
                                  " ");

        LogToMail.AddToLog("fpOpenReceipt " + (cmd == (byte) 0x30 ? "fiscal No." + getBonFisc() : "service") + " usn " + usn);

        */
            //data = "1,1,DT123456-0001-0000001,1";
        if (comm.request(cmd, data)) {
            res = analyzeAnswer();
            if (res) {
                if (ansItemNum >= 1)
                    setBonAll(CommonFn.ParseInt(ansItems[0]));
                if (cmd == (byte) 0x30 && ansItemNum >= 2)
                    setBonFisc((int) CommonFn.ParseDouble(ansItems[1]) + 1);
            }
        }

//        if (!res) {
//            LogToMail.AddToLog("ERROR: fpOpenReceipt " + (cmd == 48 ? "fiscal" : "КИ/сторно") + " data = " + data);
//        }

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

        //LogToMail.AddToLog("fpCancelReceipt");

        if (comm.request((byte) 0x3C, "")) {
            res = analyzeAnswer();
        }

//        if (!res)
//            LogToMail.AddToLog("ERROR: fpCancelReceipt");

        return res;
    }

    @Override
    public boolean fpPrintClientData()
    {
        boolean res = true;
        if(/* App.getCtrl().getCurrentPrintBon().client != null && */ client != null)
        {
            // 39h (57) ПЕЧАТ НА ИНФОРМАЦИЯ ЗА КЛИЕНТА
            // Данни: <EIK><Tab><EIKType>[<Tab><Seller>[<Tab><Receiver>[<Tab><Client>[<Tab><TaxNo>[<Tab><Address>]]]]]
            // Отговор: <ErrCode>
            //  Задължителни параметри:
            //EIK ЕИК номер на купувача. Между 9 и 14 символа
            //Tab Табулация (09H). Разделител между параметрите
            //EIKType ЕИК тип. 0 - Булстат, 1 - ЕГН, 2 - ЛНЧ, 3 - Сл. номер
            //
            // 4 символа.
            //  Address Адрес на купувача. До два реда текст максимално от 36 символа, разделени с LF(0AH).

            fpError = 0;
            if (noCommSetError())
                return false;

            res = false;
            String data = "";
            String vatNumber = client.getVATNumber() != null ? client.getVATNumber() : "";
            if(vatNumber.equals("") || vatNumber.length() < 10)
                vatNumber = "BG" + client.getRegNo();

            if(vatNumber.length() > 14)
                vatNumber = vatNumber.substring(0,14);

            if(client.getEikType() == 1 && client.getRegNo().length() != 10)
                if(client.getRegNo().length() == 9 || client.getRegNo().length() == 13)
                    client.setEikType(0);
                else
                    client.setEikType(3);

            //LogToMail.AddToLog("Datecs 150 fpCloseReceipt client EIK = " + client.getRegNo() + " EikType = " + client
                    //.getEikType() + " vatNumber=" + vatNumber);

            String address = getValidAddress();
            data += client.getRegNo() + "\t" + client.getEikType() + "\t" +
                    " " + "\t" +
                    " " + "\t" +
                    client.getName() + "\t" + vatNumber +
                    (address == "" ? "" : "\t" + address);

            //LogToMail.AddToLog("data = " + data);

            if (comm.request((byte) 0x39, data))
                res = analyzeAnswer();


        }

        return res;
    }

    @Override
    public boolean fpArticleSell(String name, double dPrice, int bVatGr, double dQuan,
                                 double dPercDiscount, double dDiscount,
                                 String linePre, String linePost,
                                 byte display, int departmentList)  {
        // 49
        // [<L1>][<Lf><L2>]<Tab><TaxCd><[Sign]Price>[*<Qwan>][,Perc|;Abs]
        //или
        //[<L1>][<Lf><L2>]<Tab><Dept><Tab><[Sign]Price>[*<Qwan>][,Perc|;Abs]

        // Отговор:		Няма данни

        boolean res = false;

        if(noCommSetError())
            return false;

        if(Math.abs(dPrice) < 0.001)
            return false; // нулева цена
        if(Math.abs(dQuan) < 0.001)
            dQuan = 1.0;

        int coefStorno = (dQuan < -0.0001 /* &&  App.getCtrl().getCurrentPrintBon().isfReversal() */ ? -1 : 1 );

//        if(false && App.getCtrl().getCurrentPrintBon().isInvoice())
//        {
//            // dPrice е сумата без ДДС, да се подаде dPice * (1 + vat)
//            VAT vat = CommonFn.getVatObject(bVatGr + 1);
//            if(vat != null)
//                dPrice *= (1 + vat.getPerc());
//        }

        String strPrice = String.format("%.2f",dPrice).replaceAll(",", ".");
        String strQuan = String.format("%.3f", coefStorno * dQuan).replaceAll(",", ".");
        String strPercent = String.format("%.2f",dPercDiscount).replaceAll(",", ".");
        String strDiscount = String.format("%.2f",dDiscount).replaceAll(",", ".");

        String data;
        if(Math.abs(dQuan - 1.00) < 0.000)
            data = String.format("%s\t%c%s", name, taxLetter[bVatGr],strPrice);
        else
            data = String.format("%s\t%c%s*%s", name, taxLetter[bVatGr],strPrice,strQuan);

        if(Math.abs(dPercDiscount) > 0.0001)
            data += String.format(",%s",strPercent);
        if(Math.abs(dDiscount) > 0.0001)
            data += String.format(";%s",strDiscount);

       // LogToMail.AddToLog("fpArticleSell");

        if(comm.request((byte)0x31, data))
            res = analyzeAnswer();

//        if(!res)
//            LogToMail.AddToLog("ERROR: fpArticleSell");

        return res;
    }

    @Override
    public boolean fpGetCurrentZData() {
        // 65 (41h) ТЕКУЩИ НЕТНИ/ОБЩИ СУМИ
        // Област за данни:	[Type]
        // Отговор:	Zlate,Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7, Tax8
        // Отговор версия 2020г.: Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7, Tax8

        boolean res = false;
        int i;

        if(!checkComm())
            return false;

        if(comm.request((byte)0x41, "0")) // общо
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= VAT_SUMS.numVats)
                {
                    PrinterFunctions.cashData.closure = getClosure();
                    for(i = 0; i < VAT_SUMS.numVats; i++)
                    {
                        PrinterFunctions.cashData.vatSumsBrutto.sum[i] = CommonFn.ParseDouble(ansItems[i]) / 100.0;
                    }
                }
            }
        }

        if(comm.request((byte)0x41, "1")) // ДДС-та
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= VAT_SUMS.numVats)
                {
                    PrinterFunctions.cashData.closure = getClosure();
                    for(i = 0; i < VAT_SUMS.numVats; i++)
                    {
                        PrinterFunctions.cashData.vatSumsNetto.sum[i] = PrinterFunctions.cashData.vatSumsBrutto.sum[i] - CommonFn.ParseDouble(ansItems[i]) / 100.0;
                    }
                }
            }
        }

        if(comm.request((byte)0x41, "5")) // сторно оборот по ДДС групи
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= VAT_SUMS.numVats)
                {
                    for(i = 0; i < VAT_SUMS.numVats; i++)
                    {
                        PrinterFunctions.cashData.vatSumsStorno.sum[i] = CommonFn.ParseDouble(ansItems[i]) / 100.0;
                    }
                }
            }
        }

        //LogToMail.AddToLog(String.format("Datecs 150 sumStorno = %f", PrinterFunctions.cashData.vatSumsStorno.Total()));

        PrinterFunctions.cashData.numStorno = 0;
        for(int op = 1; op < 2/*31*/; op++) // използвали сме само оператор 1!!!
        {
            if (comm.request((byte) 0x70, String.valueOf(op) + ",1")) // Информация за сторно операции
            {
                res = analyzeAnswer();
                if (res) {
                    // За Type = 1: ErrCode[,<STRReceipts>,<STRTotal>,<STRReturn>,<STRError>,<STRTaxbase>,<Name>[,Password]]
                    if (ansItemNum >= 1 && !ansItems[0].equals("F")) {
                        PrinterFunctions.cashData.numStorno += CommonFn.ParseInt(ansItems[0]);
                        //LogToMail.AddToLog(String.format("numStorno for op %d = %s", op, ansItems[0]));
                    }
                }
                else
                    break;
            }
            else
                break;
        }


        //LogToMail.AddToLog(String.format("Datecs 150 numStorno %d, sumStorno %f", PrinterFunctions.cashData.numStorno, PrinterFunctions.cashData.vatSumsStorno.Total()));
//
//        if(!res)
//            LogToMail.AddToLog("ERROR: fpGetCurrentZData");


        return res;
    }

    @Override
    public boolean fpPrintBonFromKLEN(int start, int end)
    {
        // 7Dh (125) ЧЕТЕНЕ НА ДАННИ ОТ ДОКУМЕНТ ПО НОМЕР
        // Област за данни: <Option>[,<DocNum>,<RecType>[,<ToNumber>]]

        boolean res = false;
        String data;

        if(!checkComm(true))
            return false;

        for(int i = start; i <= end; i++) {
            data = String.format("3,%d,0", i);
            res = comm.request((byte) 0x7D, data);
            if (res)
                res = analyzeAnswer();

            if (!res)
                break;
        }

        return res;
    }

    @Override
    public String getLastErrorText(int err)
    {
        return ErrorCodes.getErrorTextV1(err);
    }

    @Override
    public long fpGetInvRange(boolean fReversal) {
        boolean res = false;

        if (noCommSetError())
            return 0;

        long current = 0;

        //LogToMail.AddToLog("fpGetInvRange");

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

        if (noCommSetError())
            return false;

        String data = String.format("%d,%d", start, end);
        if (comm.request((byte) 0x42, data)) {
            if (analyzeAnswer()) {
                if (ansItemNum >= 3) {
                    invRangeStart = CommonFn.ParseLong(ansItems[0]);
                    invRangeEnd = CommonFn.ParseLong(ansItems[1]);
                    invNum = ansItems[2];
                    return invRangeStart <= invRangeEnd;
                }
            }
        }
        return false;
    }

}

