package com.sistechnology.aurorapos2.core.fp_comm.legacy;

import android.util.Log;

import com.sistechnology.aurorapos2.R;
import com.sistechnology.aurorapos2.App;
import com.sistechnology.aurorapos2.core.fp_comm.Printer;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.BonPrintData;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.ClientDataProvider;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.CommonFn;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.VAT_SUMS;
import com.sistechnology.aurorapos2.core.utils.DateUtil;
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem;


import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by MARIELA on 5.5.2017 г..
 * base FP commands that cover Daisy and are inherited by Eltrade and tremol
 */

public class CommandSetFP extends CommandSet
{
    public static byte STX = 0x01;
    public static byte ETX = 0x03;
    public static byte postAmble = 0x05;
    public static byte postAmble2 = 0x04;
    public static byte NAK = 0x15;
    public static byte SYN = 0x16;
    public static byte RETRY = 0x0E;
    public int seq = 0;
    public static byte[] bccBytes = new byte[4];

    public static char[] taxLetter = {'А', 'Б', 'В', 'Г'};
    //public static char[] paymentLetter = {'P', 'N', 'D', 'C'};
    public static char[] paymentLetter = {'P', 'N', 'D', 'C', ' ', ' ', ' ', ' ', ' ', ' ', ' '};

    public static byte[] answer = new byte[1024]; // да се ползва като валиден отговор, само ако request() е върнал true
    public String[] ansItems;
    public int ansItemNum = 0;
    public String separator = ""; // tab за datecs 25x

    public VAT_SUMS vatSums = new VAT_SUMS();

    public double paidFP = 0.0;
    public boolean receiptOpen;
    public int closure;
    public int bonFisc;
    public int bonAll;
    public long bonGlobal;
    public String invNum = "";

    public String fm_num = "";
    public String ej_num = "";
    public String ej_serial = "";
    public String version = "";
    public String usn = "";
    public long tempId = 0;
    public ClientDataProvider client;
    public String stornoUSNEmpty = "OO000000-0000-0000000";

    public String originalDateHour = "";








    public static String password = "9999";

    public void InitParams()
    {
        fiscal = true;
        password = "9999";
        STX = 0x01;
        ETX = 0x03;

        seq = 0;
        taxLetter[0] = 'А';
        taxLetter[1] = 'Б';
        taxLetter[2] = 'В';
        taxLetter[3] = 'Г';
        paymentLetter[0] = 'P';
        paymentLetter[1] = 'N';
        paymentLetter[2] = 'D';
        paymentLetter[3] = 'C';
        paymentLetter[4] = ' ';
        paymentLetter[5] = ' ';
        paymentLetter[6] = ' ';
        paymentLetter[7] = ' ';
        paymentLetter[8] = ' ';
        paymentLetter[9] = ' ';
        paymentLetter[10] = ' ';

        separator = "";
    }

    public CommandSetFP()
    {
        InitParams();
    }

    public boolean reInit(){ return true; }

    public int getClosure() { return closure; }
    public void setClosure(int closure) { this.closure = closure; }
    public int getBonFisc() { return bonFisc; }
    public void setBonFisc(int bonFisc) { this.bonFisc = bonFisc; }
    public int getBonAll() { return bonAll; }
    public void setBonAll(int bonAll) { this.bonAll = bonAll; }
    public long getBonGlobal() { return bonGlobal; }
    public void setBonGlobal(long bonGlobal) { this.bonGlobal = bonGlobal; }
    public String getInvNum() { return invNum; }
    public void setInvNum(String invNum) { this.invNum = invNum; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getFm_num() { return fm_num; }
    public void setFm_num(String fm_num) { this.fm_num = fm_num; }
    public String getEj_num() { return ej_num; }
    public void setEj_num(String ej_num) { this.ej_num = ej_num; }
    public String getEj_serial() { return ej_serial; }
    public void setEj_serial(String ej_serial) { this.ej_serial = ej_serial; }

    public byte[] getAnswer() { return answer; }
    public int copyAnswerNoSYN(byte[] readBuffer)
    {
        int i = 0, j;

        for(j = 0; j < readBuffer.length; j++) {
            if(readBuffer[j] != SYN)
                break;
        }

        for(; j < readBuffer.length; j++) {
            try {
                answer[i] = readBuffer[j];
            }
            catch(Exception ex)
            {
                Log.e(TAG,"i = " + i + ", j = " + j + " " + ex.getMessage());
            }
            i++;

        }

        return i;
    }

    public byte[] calcBCC(byte[] bytes)
    {
        int i, BCC = 0;
        int pos;

        for(i=0; i<4; i++)
            bccBytes[i]=0;

        if(bytes.length < 3)
        {
            Log.e(TAG,"ERROR: calcBCC: bytes.length < 3");
            return bccBytes; // error
        }

        // LEN == bytes[1]
        pos = calcLen(bytes);

        // start form position 1 for LEN bytes for request and answer
        for(i = 1; i < 1 + pos; i++)
            BCC += (bytes[i] & 0xffl);

        int bccTemp = BCC;

        int b1, b2, b3, b4;
        b1 = bccTemp / 4096;
        if(b1 > 0)
            bccTemp -= b1 * 4096;
        b2 = bccTemp / 256;
        if(b2 > 0)
            bccTemp -= b2 * 256;
        b3 = bccTemp / 16;
        if(b3 > 0)
            bccTemp -= b3 * 16;
        b4 = bccTemp;

        bccBytes[0] = (byte)(0x30 + b1);
        bccBytes[1] = (byte)(0x30 + b2);
        bccBytes[2] = (byte)(0x30 + b3);
        bccBytes[3] = (byte)(0x30 + b4);

        return bccBytes;
    }

    public byte[] packCommand(byte cmd, String data)
    {
        //                  STX  len   seq      cmd      data  postAmble  BCC 1 BCC 2 BCC 3 BCC 4  ETX
        // byte[] bytes = {0x01, 0x24, 0x21, (byte)0xA6,        0x05,     0x30, 0x30, 0x3F, 0x30, 0x03};

        byte[] bytes = new byte[3 + 1 + data.length() + 1 + 4 + 1];
        comm.lenToSend = 0;
        int i, BCC = 0;

        seq++;
        if(seq > 0xdf)
            seq = 1;

        bytes[comm.lenToSend++] = STX;
        bytes[comm.lenToSend++] = (byte)(0x20 + data.length() + 4); // len+seq+cmd+data+postamble
        bytes[comm.lenToSend++] = (byte)(0x20 + seq);
        bytes[comm.lenToSend++] = cmd;
        for(i = 0; i < data.length(); i++)
            bytes[comm.lenToSend++] = CommonFn.getCyrByte(data.charAt(i));
        bytes[comm.lenToSend++] = postAmble;

        calcBCC(bytes);

        for(i = 0; i<4; i++)
            bytes[comm.lenToSend++] = bccBytes[i];

        bytes[comm.lenToSend++] = ETX;

        return bytes;
    }

    public int calcLen(byte[] bytes)
    {
        int len = (int) ((bytes[1] & 0xffl) - 0x20l);
        //   0   1   2   3    4           5      6          7    8  9
        // STX LEN SEQ CMD DATA Post–amble2 STATUS Post–amble1 BCC ETX

        if((bytes[1] & 0xffl) == 0xFF) // Ако общият брой байтове надхвърля 224, се подава FFh.
        {
            for(int i = 2; i < bytes.length; i++)
                if(bytes[i] == ETX)
                {
                    len = i - 4 - 1 /*BCC EXT*/; // ЗА ДЕЙЗИ! todo datecs 150?
                    break;
                }
        }

        return len;
    }

    public boolean checkFormat() {
        int i;
        int len = calcLen(answer);

        if (len < 9 || len >= answer.length - 5)
        {
            Log.e(TAG,"ERROR: checkFormat: wrong len");
            return false;
        }

        //   0   1   2   3      length-7-6 length-6-6 length-2  length-5
        // STX LEN SEQ CMD DATA postAmble2 STATUS     postAmble BCC ETX
        if (answer[0] != STX ||
            (answer[2] & 0xffl) != 0x20l + seq ||
            answer[len - 7] != postAmble2 ||
            answer[len] != postAmble ||
            answer[len + 5] != ETX) {
            // error in format

            Log.i("PRINTER", "ERROR: checkFormat: error in format");
            Log.e(TAG,"ERROR: checkFormat: error in format");
            return false;
        }
        return true;
    }

    public boolean getError() { return statusFP.getError(); }
    public String getStatusText() { return statusFP.getStatusText(); }



    @Override
    public boolean analyzeAnswer(String charsetName)
    {
        int i;
        int len = calcLen(answer);

        if(len < 9 || len >= answer.length - 5)
        {
            Log.e(TAG,"ERROR: analyzeAnswer: wrong answer len");
            return false;
        }
        // request:
        // 01 25 22 4a 58 05 30 30 3e 3e 03

        //   0   1   2   3      length-7-6 length-6-6 length-5  length-1
        // STX LEN SEQ CMD DATA postAmble2 STATUS     postAmble BCC ETX
        //  0d  0a
        //  45  52  52 4f 52 20 31 30 30 32 20
        //  0d 0a
        //  01 31 22 4a 80 80 80 80
        //  01  31  23 4a 80 80 80 80 86 98 04 80 80 80 80 86 98 05 30 36 3e 33 03
        if(answer[0] != STX ||
           (answer[2]& 0xffl) != 0x20l + seq ||
           answer[len - 7] != postAmble2 ||
           answer[len] != postAmble ||
           answer[len + 5] != ETX)
        {
            // error in format
            Log.e(TAG,"ERROR: analyzeAnswer: error in format");
            return false;
        }

        calcBCC(answer);
        for(i=0; i< 4; i++)
            if(bccBytes[i] != answer[len + 1 + i])
            {
                // error BCC
                Log.e(TAG,"ERROR: analyzeAnswer: error BCC");
                return false;
            }

        for(i=0; i < 6; i++)
        {
            statusFP.statusBytes[i] = answer[len - 6 + i];
        }
        if(getError())
        {
            Log.e(TAG,"ERROR: analyzeAnswer: getStatusText: " + getStatusText());

            return false;
        }

        ansItemNum = 0;
        ansItems = null;

        // data
        if(len > 11) {
            String str;
            byte[] bytes = new byte[len - 11];

            for (i = 4; i <= len - 8; i++)
                bytes[i-4] = answer[i];

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
            ansItems = str.split(",");
            ansItemNum = (ansItems == null ? 0 : ansItems.length);
        }

        Log.i(TAG,"analyzeAnswer: OK");

        return true;
    }

    public int readLastError() {
        if (comm.request((byte) 0x20, ""))
        {
            if (analyzeAnswer())
            {
                if (ansItemNum > 1)
                {
                    return CommonFn.ParseInt(ansItems[1]);
                }
            }
        }
        return fpError;
    }

    public boolean openReceipt(byte receiptType)
    {
        return openReceipt(receiptType, "", null);
    }

    public boolean openReceipt(byte receiptType, String usn)
    {
        return openReceipt(receiptType, usn, null);
    }

    public boolean openReceipt(byte receiptType, String usn, ClientDataProvider client)
    {
        this.usn = (usn);
        this.client = client;

        boolean res = fpOpenReceipt(1, 1, receiptType);
        if(!res)
        {
            if(statusFP.NON_FISCAL_OPENED())
                res = fpCloseReceipt(false);
            else
                if(statusFP.FISCAL_OPENED())
                {
                    res = fpCloseReceipt(true);
                    if(!res)
                        res = fpCancelReceipt();
                }
            if(res)
                // затворили сме/канцелирали сме предходен отворен бон
                res = fpOpenReceipt(1, 1, receiptType);
        }
        return res;
    }

    @Override
    public boolean fpPrintService(BonPrintData printData)
    {
        boolean res = false;
        String text;
        int i;

        Log.i(TAG,"fpPrintService");
        if(printData == null || (printData.getBonParent() != null))
        {
            Log.e(TAG,"fpPrintService exit false:" + (printData == null ? "printData == null" : "not closed?"));

            return false;
        }

        Log.i(TAG,"print service receipt");

        setLastError("");

        if(!checkComm())
        {
            return false;
        }

        res = openReceipt(FP_SERVICE_RECEIPT);

        if(res)
        {
            List<String> strList = printData.toStringListReceipt();

            for(i=0;i<strList.size();i++)
            {
                fpPrintNonFiscalText(strList.get(i));
            }
        }

        if(printData.voucherBarcode != null && !printData.getTextToPrintVoucher().equals(""))
        {
            String str = printData.getTextToPrintVoucher();
            StringTokenizer bonData = new StringTokenizer(str, "\n\r");
            while (bonData.hasMoreTokens())
                fpPrintNonFiscalText(bonData.nextToken());

            fpPrintBarcode(printData.voucherBarcode);
        }

        if(res &&  printData.bonParent != null && printData.bonParent.isReversal() && printData.bonParent.getReversedBon() != null)
        {
            String orig = App.context.getString(R.string.bon_orig_short);

            if(printData.bonParent.getReversedBon().isFiscal())
                orig += String.format(" %d, %s %d\n",
                                      printData.bonParent.getReversedBon().getBonNum(),
                                      App.context.getString(R.string.closure),
                                      printData.bonParent.getReversedBon().getClosure());
            else
                orig += " " + printData.bonParent.getReversedBon().getReceiptNumber() + "\n";

            fpPrintNonFiscalText(orig);
            fpPrintNonFiscalText(String.format("%s %s",
                                               printData.bonParent.getReversedBon().getDate(),
                                               printData.bonParent.getReversedBon().getTime()));

            fpPrintNonFiscalText(App.context.getString(R.string.reversalReason));
        }



        fpGetStatus();

        if(statusFP.NON_FISCAL_OPENED())
            res = fpCloseReceipt(false);

        if(getSerial().equals(""))
            fpGetDiagInfo();

        if(!res)
            fpGetStatus();

        // zagl
        // if(!res)


        return res;
    }

    public boolean fpPrintFiscal(Bon bon)
    {
        boolean res = false;
        String text = "";

        if(bon == null)
            return false;

//        if(bon.isfReversal())
//            return fpPrintService(bon.printData);

        Log.i(TAG,"print fiscal receipt");
        int trials;
        double tempTotal = 0;
        double dDiscountTemp = 0;

        if(!checkComm())
        {
            return false;
        }

        double dSubtotalDiscount = 0;

        if(fpGetStatus()) {
            if (getError())
            {
                if(!getStatusText().equals("")) {

                }
                return false;
            }
        }

        while(true) {
            if(statusFP.isReceiptOpened())
            {
                // канцелирай стара бележка
                if(!fpCancelReceipt()) {

                    if(!statusFP.isFiscalOpened())
                        fpCloseReceipt(false);
                    else {
                        // неуспех -> плати и затвори
                        fpPayment(0.00, (byte) 0, (byte) 0, "");
                        fpCloseReceipt(true);
                    }
                }
            }
            setLastError("");
            if(tempId != 0 && bon.getId() == tempId)
            {
                Log.i("PRINT_FISCAL", "current receipt closed, no need to print it again; id = " + tempId);
                res = true;
                break; // current receipt closed, no need to print it again
            }

            paidFP = 0;

            if(bon.isReversal())
                if (bon.getBonType() == 5)
                    res = openReceipt(FP_KI_RECEIPT, bon.getUsn(), bon.getClient());
                else
                    res = openReceipt(FP_RETURN_RECEIPT, bon.getUsn());
            else
                if (bon.getBonType() == 4) {
                    res = openReceipt(FP_INVOICE_RECEIPT, bon.getUsn(), bon.getClient());
                }
                else
                    res = openReceipt(FP_FISCAL_RECEIPT, bon.getUsn());

            if(!res)
            {
                Log.e(TAG,"Check for error opening receipt:" + getLastErrorText() + " " + getStatusText());
                if(fpError != 0)
                    addLastError("Opening receipt: " + getLastErrorText() + " " + getStatusText());
            }
            else
            {
                Log.i(TAG,"Opening receipt: success");

                if(bon.getId() > 0 && bon.getBonNum() != getBonFisc())
                {
                    // бонът е записан с грешен bonFisc
                    bon.setBonNum(getBonFisc());
                    //App.getDb().tCash.updateBonFisc_FromFP(bon); TODO
                }
            }

            if (res) {
                text = String.format("%s %d %s %d", App.context.getString(R.string.bon_fisc), getBonFisc(), App.context.getString(R.string.closure), getClosure());

                fpPrintFiscalText(text);

                // fpArticleSell(String name, double dPrice, byte bVatGr, double dQuan,String linePre, String linePost,byte display, int departmentList)

                ReceiptItem item;
                for (int i = 0; i < bon.getListArticles().size(); i++)
                {
                        item = bon.getListArticles().get(i);

                        trials = 2;

                        tempTotal += item.getTotal();



                        do {
                            res = fpArticleSell(item.getName(),
                                                item.getPrice(),
                                                item.getVatGroupId(), //TODO
                                                item.getQuantity(),
                                                0.0, -(item.getItemDiscount() /*+ item.getBonItem_subt_discount()*/), "", "", (byte) 0, 0);

                            if (!res) {

                                if (!paperOK())
                                    return false; // after fpArticleSell

                               if(fpError != 0)
                                    addLastError("Article Sell: " + getLastErrorText() + " " + getStatusText());

                                fpGetReceiptVatGroupSales();
                                if (Math.abs(vatSums.Total() - tempTotal) < 0.0001) // подаденото към ФП е регистрирано
                                    break;
                            }
                            trials--;
                        } while (!res && trials > 0);

                        if(isPaperError())
                            break;

                        if(!res)
                            if(fpError != 0)
                                addLastError("Sale: " + getLastErrorText()  + " " + getStatusText());

//                        if (item.getSerialNumber() != null && !item.getSerialNumber().equals("")) { TODO
//                            text = String.format("%s %s", App.GetString(R.string.serialNumber), item.getSerialNumber());
//                            fpPrintFiscalText(text);
//                        }

                        //dSubtotalDiscount += item.getBonItem_subt_discount(); TODO

                }
            }
            else
                if (!paperOK())
                    return false; // after openReceipt
                else
                    if(fpError != 0)
                        addLastError("Open rcpt: " + getLastErrorText() + " " + getStatusText());

//            if (res && dSubtotalDiscount > 0.001 /*&& Math.abs(bon.getTotal()) > 0.001*/) TODO SUBTOTAL
//            {
//                res = fpSubtotal(true, bon.getTotal(), 0.0, -dSubtotalDiscount);
//
//                // ако не е успяла отстъпката субтотал, направи войд по ддс групи
//                if(!res)
//                {
//                    if (!paperOK())
//                        return false; // after fpSubtotal
//
//                    if(fpError != 0)
//                        addLastError("Subtotal: " + getLastErrorText() + " " + getStatusText());
//
//                    fpGetReceiptVatGroupSales(); // попълва vatSums;
//                    if(Math.abs(vatSums.Total()) > 0.0001)
//                    {
//                        VAT_SUMS sumsBeforeDiscount = new VAT_SUMS();
//                        for(int v=0; v<8; v++)
//                        {
//                            sumsBeforeDiscount.sum[v] = vatSums.sum[v];
//                            sumsBeforeDiscount.rate[v] = vatSums.rate[v];
//                        }
//                        for(int v = 0; v < 8; v++)
//                        {
//                            // отстъпка за ДДС група v
//                            dDiscountTemp = sumsBeforeDiscount.sum[v] / sumsBeforeDiscount.Total() * (-dSubtotalDiscount);
//
//                            if (Math.abs(dDiscountTemp) > 0.0001)
//                            {
//                                trials = 2;
//                                do {
//                                    tempTotal += dDiscountTemp;
//
//                                    res = fpArticleSell(App.GetString(R.string.discount),
//                                                        dDiscountTemp,
//                                                        (byte) v,
//                                                        1,
//                                                        0.0, 0, "", "", (byte) 0, 0);
//
//                                    if (!res) {
//
//                                        if (!paperOK())
//                                            return false; // after discount article
//
//                                        fpGetReceiptVatGroupSales();
//                                        if (Math.abs(vatSums.Total() - tempTotal) < 0.0001) // подаденото към ФП е регистрирано
//                                            break;
//                                    }
//                                    trials--;
//                                }
//                                while (!res && trials > 0);
//                            }
//                        }
//                    }
//                }
//
//                if (res) {
//                    if (bon.getVoucherDataPaid() != null) {
//                        fpPrintFiscalText(bon.getVoucherDataPaid().getVoucher_name());
//                        fpPrintFiscalText(bon.getVoucherDataPaid().getBarcode());
//                    }
//                }
//                else
//                {
//                    if (!paperOK())
//                        return false; // not paid yet
//
//                    if(fpError != 0)
//                        addLastError("Discount: " + getLastErrorText() + " " + getStatusText());
//                }
//
//            }

//            if (res && PrinterFunctions.isFiscalDeviceEltrade()) {
//                if (bon.getVoucherData() != null && !bon.printData.getTextToPrintVoucher().equals("")) {
//                    String str = bon.printData.getTextToPrintVoucher();
//                    StringTokenizer bonData = new StringTokenizer(str, "\n\r");
//                    while (bonData.hasMoreTokens())
//                        fpPrintFiscalText(bonData.nextToken());
//
//                    fpPrintBarcode(bon.getVoucherData().getBarcode());
//                }
//            }


            if (res)
            {
                double dPaid = 0;
                int tries = 2;
                for (int i = 0; i < bon.getListPayments().size(); i++) {
                    if (Math.abs(bon.getListPayments().get(i).getAmount()) > 0.0001 || Math.abs(bon.getTotal()) < 0.00001 ) {
                        dPaid += bon.getListPayments().get(i).getAmount();

                        tries = 2;
                        do {
                            res = fpPayment(bon.getListPayments().get(i)
                                                            .getAmount(), (byte) (bon.getListPayments().get(i)
                                                                                                       .getType().getId() - 1), (byte) 0, bon.getListPayments()
                                                    .get(i)
                                                    .getType().getName());

                            if (!res) {

                                if (!paperOK())
                                    return false; // after payment

                                if (fpGetReceiptPayment())
                                    if (Math.abs(paidFP - dPaid) < 0.0001)
                                    {
                                        Log.i("PRINT_FISCAL", "paid sum in FP OK");
                                        res = true;
                                        break;
                                    }
                            }
                            tries--;
                        } while (!res && tries > 0);

                        if(!res)
                        {
                            if(fpError != 0)
                                addLastError("Payment: " + getLastErrorText() + " " + getStatusText());

                            break; // stop fpPayment
                        }
                    }
                }
            }
            else
                if (!paperOK())
                    return false; // after voucher barcode; not paid

            // евентуално PAX data
//            if (!bon.printData.getTextToPrintPAX().equals("")) { TODO PAX
//                String str = bon.printData.getTextToPrintPAX();
//                StringTokenizer bonData = new StringTokenizer(str, "\n\r");
//                while (bonData.hasMoreTokens())
//                    fpPrintFiscalText(bonData.nextToken());
//            }

//            if (res && !PrinterFunctions.isFiscalDeviceEltrade()) {
//                if (bon.getVoucherData() != null && !bon.printData.getTextToPrintVoucher().equals("")) {
//                    String str = bon.printData.getTextToPrintVoucher();
//                    StringTokenizer bonData = new StringTokenizer(str, "\n\r");
//                    while (bonData.hasMoreTokens())
//                        fpPrintFiscalText(bonData.nextToken());
//
//                    fpPrintBarcode(bon.getVoucherData().getBarcode());
//                }
//            }

            // нормално затваряне на бележката:
            if (res) {
                    res = fpCloseReceipt(true);
//                    if(!res && (App.getCtrl().getCurrentPrintBon().bonType == 4 || App.getCtrl().getCurrentPrintBon().bonType == 5)) TODO
//                    {
//                        // проблем при затваряне на бона:
//                        // служебен клиент
//                        client = new ClientDataProvider();
//                        res = fpCloseReceipt(true);
//                        if(res)
//                            CommonFn.Toast(App.GetString(R.string.errcust), Toast.LENGTH_LONG);
//                    }
            }
            else
                if (!paperOK())
                    return false; // after pax data, paid


            if(res)
                if (getStatus().isReceiptOpened())
                    res = false;
                else
                    tempId = bon.getId();

            if(!res)
                if(fpError != 0)
                    addLastError("End rcpt: " + getLastErrorText() + " " + getStatusText());

            if (!res) {
                //if (paperOK())
                {
                    if (!fpCancelReceipt()) {
                        fpPayment(0.00, (byte) 0, (byte) 0, "");
                        fpCloseReceipt(true);
                    }
                }
            }

            if (res && !getStatus().isReceiptOpened())
            {
                bon.setFlagPrinted(true);


            }

            break;
        }

        // zagl
        // if(!res)



        if(!res) {
            if(fpError != 0)
                addLastError("Print rcpt: " + getLastErrorText() + " " + getStatusText());
        }


        return res;
    }



    protected long invRangeStart = 0;
    protected long invRangeEnd = 0;

    public long fpGetInvRange(boolean fReversal) {
        boolean res = false;

        if (noCommSetError())
            return 0;

        Log.i(TAG,"fpGetInvRange");

        // номер на следващата фактура
        if (comm.request((byte) 0x96, "R17")) {
            if (analyzeAnswer()) {
                if (ansItemNum >= 2) {
                    invRangeStart = CommonFn.ParseLong(ansItems[1]);
                }
            }
        }

        if (comm.request((byte) 0x96, "R18")) {
            if (analyzeAnswer()) {
                if (ansItemNum >= 2) {
                    invRangeEnd = CommonFn.ParseLong(ansItems[1]);
                }
            }
        }

        long invNumCrn = Math.max(invRangeStart, CommonFn.ParseLong(getInvNum()));
        setInvNum(String.valueOf(invNumCrn));

        Log.i("GET_INV_RANGE:", getInvNum());
        return CommonFn.ParseLong(getInvNum());
    }

    public boolean fpSetInvRange(boolean fReversal, long start, long end) {
        // input: <StartNum[10]> <;> <EndNum[10]>
        // output: ACK

        if (noCommSetError())
            return false;

        Log.i(TAG, "fpSetInvRange");

        String data = String.format("P17,%d", start);

        if (comm.request((byte) 0x96, data)) {
            if (!analyzeAnswer())
                return false;
        }

        data = String.format("P18,%d", end);

        if (comm.request((byte) 0x96, data)) {
            if (!analyzeAnswer())
                return false;
        }

        fpGetInfo(); // read invNum

        return true;
    }

   // public long fpCheckInvRangeAndGetInvNum(boolean fReversal)
    //{
//        if (noCommSetError() && !PrinterFunctions.isFiscalDeviceErpNet()) TODO
//            return 0;
//
//        Log.i(TAG, "fpCheckInvRange");
//
//        // номер на следващата фактура
//        fpGetInvRange(fReversal);
//
//        long numFromFP = Math.max(invRangeStart, CommonFn.ParseLong(invNum));
//
//        if(invNum.equals(""))
//            invNum = String.valueOf(numFromFP);
//
//        Log.i(TAG, "invoice range from FP: " + invRangeStart + "-" + invRangeEnd + ", current:" + invNum);
//        Log.i(TAG, "next invoice num from FP: " + numFromFP);
//        Log.i(TAG, "next invoice num from Terminal parameters: " + String.valueOf(CommonFn.ParseInt(App.getInvoicePosCodePrefix()) + 1));
//
//        // увеличи posCode за фактури, но не записвай
//        App.setInvoicePosCodePrefix(String.valueOf(CommonFn.ParseInt(App.getInvoicePosCodePrefix()) + 1), false);
//        //num = Math.max(num, CommonFn.ParseInt(App.getInvoicePosCodePrefix()));
//        long num = CommonFn.ParseInt(App.getInvoicePosCodePrefix());
//        if(PrinterFunctions.isFiscalDeviceEltrade())
//            fpSetInvRange(fReversal, num, num+2);
//        else
//            fpSetInvRange(fReversal, num, num);
//
//            if(numFromFP > invRangeEnd) {
//                Log.i(TAG, "setting inv range " + numFromFP + "-" + numFromFP);
//                fpSetInvRange(fReversal, numFromFP, numFromFP);
//            }
//
//        fpGetInvRange(fReversal);
//
//        invNum = String.valueOf(Math.max(invRangeStart, CommonFn.ParseLong(invNum)));
//        LogToMail.AddToLog("current invoice range from FP: " + invRangeStart + "-" + invRangeEnd + ", current:" + invNum);
//        return CommonFn.ParseLong(getInvNum());
    //}

    public boolean fpOpenReceipt(int TermCode, int opCode, byte recType) {
        // 48 (30h) НАЧАЛО НА ФИСКАЛЕН БОН
        // Област за данни: {ClerkNum},{Password},{UnicSaleNum}[{Tab}{Invoice} |{Refund}{Reason},{DocLink},{DocLinkDT}{Tab}{FiskMem}
        //|{Credit}{InvLivk},{Reason},{DocLink},{DocLinkDT}{Tab}{FiskMem}]

        // Отговор:		Allreceipt, FiscReceipt

        if(noCommSetError())
            return false;

        boolean res = false;

        byte cmd = (byte)0x30;

        Date date = null;
//        if (recType == FP_RETURN_RECEIPT || recType == FP_KI_RECEIPT) { TODO KI RETURN
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
//            try {
//                date = dateFormat.parse(App.getCtrl().currentPrintData.getBonParent().originalReceiptDate + " " +
//                                        App.getCtrl().currentPrintData.getBonParent().originalReceiptTime);
//            } catch (ParseException e) {
//                return false;
//            }
//        }

        String data = ""; //(cmd == (byte)0x30) ? String.format("%d,%s",20,password) : "";
        // {ClerkNum},{Password},{UnicSaleNum}
        //  [{Tab}{Invoice}
        //                R              0,1,2    globalOld {DD–MM–YY}{space}{HH:mm[:SS]}        FMemory
        //       |    {Refund}          {Reason},{DocLink},      {DocLinkDT}              {Tab}{FiskMem}
        //                C    inv Old
        //       |    {Credit}{InvLivk},{Reason},{DocLink},      {DocLinkDT}              {Tab}{FiskMem}]
        switch (recType) {
            case FP_FISCAL_RECEIPT:
                data = String.format("%d,%s%s%s", 20, password, usn.equals("") ? "": ",", usn);
                break;
            case FP_RETURN_RECEIPT:
            case FP_KI_RECEIPT:
                data = String.format("%d,%s,%s", 20, password, usn.equals("") ? stornoUSNEmpty : usn);
                break;
            case FP_INVOICE_RECEIPT:
                data = String.format("%d,%s,%s\tI", 20, password, usn.equals("") ? stornoUSNEmpty: usn);
                break;
            case FP_SERVICE_RECEIPT:
                cmd = (byte) 0x26;
                break;
        }

//        switch (recType) { TODO KI RETURN
//            case FP_RETURN_RECEIPT:
//                //                R              0,1,2    globalOld {DD–MM–YY}{space}{HH:mm[:SS]}        FMemory
//                //       |    {Refund}          {Reason},{DocLink},      {DocLinkDT}              {Tab}{FiskMem}
//                // 20,9999,DD121212-3333-1212121R1,123456,12–03–20 05:55:00	36612458
//                // 20,9999,DY454422-0001-0000068	R0,141,13–03–20 09:51:37	36612458
//                data += String.format("\tR%d,%d,%s\t%s",
//                                      App.getCtrl().currentPrintData.getBonParent().reversalReason,
//                                      CommonFn.ParseLong(App.getCtrl().currentPrintData.getBonParent().originalBonGlobal),
//                                      (new SimpleDateFormat("dd–MM–yy HH:mm:ss")).format(date),
//                                      fm_num);
//                break;
//
//            case FP_KI_RECEIPT:
//                //                C    inv Old
//                //       |    {Credit}{InvLivk},{Reason},{DocLink},      {DocLinkDT}              {Tab}{FiskMem}]
//
//                data += String.format("\tC%s,%d,%d,%s\t%s",
//                                      App.getCtrl().currentPrintData.getBonParent().originalInvoiceNumber,
//                                      App.getCtrl().getCurrentBon().reversalReason,
//                                      CommonFn.ParseLong(App.getCtrl().currentPrintData.getBonParent().originalBonGlobal),
//                                      (new SimpleDateFormat("dd–MM–yy HH:mm:ss")).format(date),
//                                      fm_num);
//                break;
//        }

        Log.i(TAG, "fpOpenReceipt " + "fiscal No." + getBonFisc() + " usn " + usn);
        Log.i(TAG, "data:" + data);

        Log.i("PRINT_FISCAL", "data = " + data);

        data = data.replaceAll("–", "-");
        if(comm.request(cmd, data))
        {
            res = analyzeAnswer();
            if(res){
                if(ansItemNum >= 1)
                    setBonAll(CommonFn.ParseInt(ansItems[0]));
                if(cmd == 48 && ansItemNum >= 2)
                    setBonFisc((int)CommonFn.ParseDouble(ansItems[1]) + 1);
            }
        }

        if(!res)
            Log.e(TAG, "ERROR: fpOpenReceipt ");

        return res;
    }

    public boolean fpArticleSell(String name, double dPrice, int bVatGr, double dQuan,
                                        double dPercDiscount, double dDiscount,
                                        String linePre, String linePost,
                       byte display, int departmentList) {
        // 49
        // Област за данни:	[{Text1}][{CR}{Text2}]{Tab}{TaxGr}{[Sign]Price} [*{QTY}][,Percent][$Netto]
        // Отговор:		Няма данни
        //                   [<L1>][<Lf><L2>]<Tab><TaxCd><[Sign]Price>[*<Qwan>][,Perc|;Abs]
        // или
        //                   [<L1>][<Lf><L2>]<Tab><Dept><Tab><[Sign]Price>[*<Qwan>][,Perc|;Abs]

        // 138 (8Ah) ПРОДАЖБА ПО ДЕПАРТАМЕНТ
        // Област за данни:	{[Sign]Dept}{@Price} [*{QTY}][,Percent] [$Netto]
        // Отговор:		Няма данни

        boolean res = false;

        if(noCommSetError())
            return false;

        if(Math.abs(dPrice) < 0.001)
            return false; // нулева цена
        if(Math.abs(dQuan) < 0.001)
            dQuan = 1.0;

        int coefStorno = (dQuan < -0.0001 && Printer.Companion.getCurrentBon().isReversal() ? -1 : 1);



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
            data += String.format("$%s",strDiscount);

        Log.i(TAG,"fpArticleSell");

        if(comm.request((byte)0x31, data))
            res = analyzeAnswer();

        if(!res)
            Log.e(TAG,"ERROR: fpArticleSell");

        return res;
    }



    public boolean fpPrintFiscalText(String text)
    {
        // 54 (36h) ПЕЧАТ НА ФИСКАЛЕН ТЕКСТ
        // Област за данни:	Text
        // Отговор:		Няма данни

        boolean res;

        if(noCommSetError())
            return false;


        Log.i(TAG,"fpPrintFiscalText " + text);

        res = comm.request((byte)0x36, text);
        if(!res)
            Log.e(TAG,"ERROR: fpPrintFiscalText");

        return res;
    }

    public boolean fpPrintNonFiscalText(String text)
    {
        // 42 (2Ah) ПЕЧАТ НА НЕФИСКАЛЕН ТЕКСТ
        // Област за данни:	Text
        // Отговор:		Няма данни

        boolean res;

        if(noCommSetError())
            return false;


        Log.i(TAG,"fpPrintNonFiscalText " + text);

        res = comm.request((byte)0x2A, text + separator);
        if(!res)
            Log.e(TAG,"ERROR: fpPrintNonFiscalText");

        return res;

    }

    public boolean fpPayment(double dPayValue, byte bPayType, byte display, String line) {
        // 53 (35h) ОБЩА СУМА (ТОТАЛ)
        // Област за данни:	[{Text1}][{CR}{Text2}]{Tab}[[{Payment}] {Amount}]
        // Отговор:		{PaidCode}{Amount}

        // Видове плащания Daisy:
        // “P” – "В БРОЙ";
        // “N” – Плащане 1
        // “C” – Плащане 2
        // “D” или "U" – Плащане 3
        // “B” или "E" – Плащане 4

        // Видове плащания Eltrade:
        // “P” – "В БРОЙ";
        // “N” – КРЕДИТ
        // “C” – С ЧЕК
        // “D” - КАРТА
        // “I”, "J", "K", "L" – доп. плащания

        // вече не:
        // ‘P’ - Плащане в брой (по подразбиране);
        // ‘N’ – „С чек",;
        // ‘C’ – Сума по Талони "Талони",
        // ‘D’ - Сума по външни талони "външни талони"
        // ‘I’ - Сума по амбалаж "амбалаж",
        // ‘J’ - Сума по вътрешно обслужване "вътрешно обслужване",
        // ‘K’ - Сума по повреди "повреди",
        // ‘L’ - Сума по кредитни/дебитни карти "кредитни/дебитни карти",
        // ‘M’ - Сума по банкови трансфери "банкови трансфери"
        // ‘Q’ - Плащане НЗОК "НЗОК",
        // ‘R’ - Резерв 2 "Резерв 2"


        boolean res = false;

        if(noCommSetError())
            return false;

        int coefStorno = (dPayValue < -0.0001 && Printer.Companion.getCurrentBon().isReversal() ? -1 : 1);

        // [{Text1}]  [{CR}{Text2}]  {Tab} [   [{Payment}]  {Amount}   ]
        String strPayValue = String.format("%.2f", coefStorno * dPayValue).replaceAll(",", ".");
        String data = String.format("\t%c%s", paymentLetter[bPayType], strPayValue);

        Log.i(TAG, String.format("fpPayment: %c %s", paymentLetter[bPayType], strPayValue));

        if(comm.request((byte)0x35, data))
        {
            res = analyzeAnswer();
            if(res && ansItemNum >= 2)
            {
                if(ansItems[0].equals("F")) // грешка
                    res = false;
                if(ansItems[0].equals("I")) // сумата по някоя данъчна група е отрицателна
                    res = false;
                if(ansItems[0].equals("E")) // отрицателна междинна сума
                    res = false;

                if(res)
                {
                    double dRest = CommonFn.ParseDouble(ansItems[1]) / 100.0;

                }
            }
        }

        if(!res)
            Log.e(TAG,"ERROR: fpPayment");

        return res;
    }

    public boolean fpPrintClientData()
    {
//        boolean res = true; TODO INVOICE
//        if(App.getCurrentPrintBon().client != null && client != null)
//        {
//            // 39h (57) ПЕЧАТ НА ИНФОРМАЦИЯ ЗА КЛИЕНТА
//            // Данни: EIK[<Tab>VATNum[<Tab>Seller[<Tab>Receiver[<Tab>Client[<Tab>Address]]]]
//            // Address: редове, разделени с 0x09
//            // Отговор: няма
//
//            res = false;
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
//            if(client.getEikType() == 1 && client.getRegNo().length() != 10)
//                if(client.getRegNo().length() == 9 || client.getRegNo().length() == 13)
//                    client.setEikType(0);
//                else
//                    client.setEikType(3);
//
//            LogToMail.AddToLog("Daisy fpCloseReceipt client EIK = " + client.getRegNo() + " vatNumber=" + vatNumber);
//
//            String address = getValidAddress();
//            data += client.getRegNo() + "\t" +
//                    vatNumber + "\t" +
//                    " " + "\t" +
//                    " " + "\t" +
//                    client.getName() + "\t" +
//                    (address == "" ? "" : "\t" + address);
//
//            LogToMail.AddToLog("data = " + data);
//
//            if (comm.request((byte) 0x39, data))
//                res = analyzeAnswer();
//        }
//
//        return res;
        return true;
    }

    public boolean fpCloseReceipt(boolean fFiscal)
    {
        // 56 (38h) КРАЙ НА ФИСКАЛЕН БОН
        // Област за данни:	Няма данни
        // Отговор:		Allreceipt, FiscReceipt

        // 39 (27h) КРАЙ НА НЕФИСКАЛЕН БОН
        // Област за данни:	Няма данни
        // Отговор:		Allreceipt

        if(noCommSetError())
            return false;

        fpPrintClientData();

        byte cmd = (byte)(fFiscal ? 0x38 : 0x27);
        boolean res = false;

        Log.i(TAG,"fpCloseReceipt");

        if(comm.request(cmd, ""))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 1) // 39
                    setBonAll(CommonFn.ParseInt(ansItems[0]));
                if(cmd == (byte)0x38 && ansItemNum >= 2) // 56
                    setBonFisc((int)CommonFn.ParseDouble(ansItems[1])); // без Датекс 25x

            }
        }

        if(!res)
            Log.e(TAG,"ERROR: fpCloseReceipt, fpError = " + fpError + " status: " + getStatusText());

        return res;
    }

    public boolean fpCancelReceipt()
    {
        // 130 (82h) АНУЛИРАНЕ НА БОН
        // Област за данни: 	Няма данни
        // Отговор: 		Allreceipt, FiscReceipt

        boolean res = false;

        if(noCommSetError())
            return false;


        Log.i(TAG,"fpCancelReceipt");

        if(comm.request((byte)0x82, ""))
        {
            res = analyzeAnswer();
            if(res){
                if(ansItemNum >= 2) {
                    setBonAll(CommonFn.ParseInt(ansItems[0]));
                    setBonFisc((int)CommonFn.ParseDouble(ansItems[1]));
                }
            }
        }

        if(!res)
            Log.e(TAG,"ERROR: fpCancelReceipt");

        return res;
    }

    public boolean isReceiptOpen() {
        if(fpGetReceiptVatGroupSales())
            return receiptOpen;

        return false;
    }

    public boolean fpGetReceiptVatGroupSales() {
        // Област за данни:	Няма данни
        // Отговор:	CanVoid,Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7,Tax8, InvoiceFlag,InvoiceNo

        boolean res = false;

        if(noCommSetError())
            return false;


        Log.i(TAG,"fpGetReceiptVatGroupSales");

        if(comm.request((byte)0x67, ""))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 11)
                    for(int i=0; i<8; i++)
                        vatSums.sum[i] = CommonFn.ParseDouble(ansItems[i+1]) / 100.0;
            }
        }

        if(res && comm.request((byte)0x4C, ""))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 3) {
                    //receiptOpen = (ansItems[0].charAt(0) == '1');
                    receiptOpen = (CommonFn.ParseInt(ansItems[0]) > 0); // Eltrade, Daisy, Datecs 150: 0/1; Datecs 25x: 0,1,2,3,4,5
                    vatSums.total = CommonFn.ParseDouble(ansItems[2]) / 100.0;

                    //if(ansItemNum >= 5)
                      //  restToPay = CommonFn.ParseDouble(ansItems[4]) / 100.0;
                }
            }
        }

        if(!res)
            Log.e(TAG,"ERROR: fpGetReceiptVatGroupSales");

        return res;
    }

    public boolean fpGetReceiptPayment()
    {
        paidFP = 0;
        // Област за данни: [Option]
        // Отговор: Open,Items,Amount[,Tender]
        // Option = ‘T’. Ако този параметър е указан, командата ще върне информацията относно текущото състояние на
        // дължимата до момента сметка от клиента.
        // Open Един байт, който е ‘1’ ако е отворен фискален или служебен бон (какъв точно може да се разбере по статус
        //                                                                              битовете), и ‘0’ ако няма отворен бон.
        // Items Броят на продажбите регистрирани на текущия или на последния фискален бон. 4 байта.
        // Amount Сумата от последния фискален бон – 9 байта със знак.
        // Tender Сумата платена на поредния или последен бон. 9 байта със знак.

        boolean res = false;
        String data;

        if(!checkComm(true))
            return false;

        res = comm.request((byte) 0x4C, "T");
        if (res)
        {
            res = analyzeAnswer();
            if(res && ansItemNum >= 4)
                paidFP = CommonFn.ParseDouble(ansItems[3]) / 100.0;
        }

        return res;
    }

    public boolean fpSubtotal(boolean print, double dTotal, double percent, double discountSum) {
        // 51
        // Област за данни:	{Print}{Display}[,Percent]
        // Отговор:		SubTotal,Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7,Tax8,

        boolean res = false;

        if(noCommSetError())
            return false;

        String data = print ? "10"   : "00";
        if(Math.abs(percent) > 0.001)
            data += String.format(",%s", String.format("%.2f",percent).replace(',', '.'));
        else
            if(Math.abs(discountSum) > 0.001 && Math.abs(dTotal - discountSum) > 0.001)
                data += String.format(",%s", String.format("%.2f",discountSum * 100.0 / (dTotal - discountSum)).replace(',', '.'));

        Log.i(TAG,"fpSubtotal");

        if(comm.request((byte)0x33, data))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 9) {
                    for (int i = 0; i < 8; i++)
                        vatSums.sum[i] = CommonFn.ParseDouble(ansItems[i + 1]) / 100.0;

                    res = Math.abs(vatSums.Total() - CommonFn.ParseDouble(ansItems[0])/100.0) < 0.001;
                }

                if(res)
                {
                    if(Math.abs(CommonFn.ParseDouble(ansItems[0])/100.0 - dTotal) > 0.001)
                    {
                        // тоталите от ФП и програмата се различават
                        res = false;
                    }
                }

            }
        }

        if(!res)
            Log.e(TAG,"ERROR: fpSubtotal");

        return res;
    }

    public boolean fpMakeReceiptCopy(int count) {
        // 109 (6Dh) ПЕЧАТ ДУБЛИКАТ НА БОН
        // Област за данни:	{Count}
        // Отговор:		Няма данни

        if(noCommSetError())
            return false;


        setLastError("");

        boolean res = false;
        String data = "1";

        Log.i(TAG,"fpMakeReceiptCopy");

        if(!checkComm(true))
            return false;

        if(comm.request((byte)0x6D, data))
            res = analyzeAnswer();

        if(!res)
            fpError = readLastError();


        return res;
    }

    public boolean fpGetInfo() {
        // 110 (6Eh) ИНФОРМАЦИЯ ЗА ДЕНЯ
        // Област за данни:	[All]
        // Отговор:	 	Cash, Pay1, ....PayX, ZRepNo, DocNo, InvoiceNo

        boolean res = false;

        if(noCommSetError())
            return false;

        Log.i(TAG,"fpGetInfo");


        if(!checkComm())
            return false;

        if(comm.request((byte)0x6E, "A"))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 5) {

                    // ZRepNo
                    setClosure(CommonFn.ParseInt(ansItems[ansItemNum - 3])+1);
                    // DocNo
                    setBonAll((int) CommonFn.ParseDouble(ansItems[ansItemNum - 2]));
                    // InvoiceNo
                    setInvNum(ansItems[ansItemNum - 1]);

                    // плащания за деня
                    PrinterFunctions.cashData.paymentsCash = CommonFn.ParseLong(ansItems[0]) / 100.0;
                    PrinterFunctions.cashData.paymentsCheque = CommonFn.ParseLong(ansItems[1]) / 100.0;
                    PrinterFunctions.cashData.paymentsCredit = CommonFn.ParseLong(ansItems[2]) / 100.0;
                    PrinterFunctions.cashData.paymentsAccount = CommonFn.ParseLong(ansItems[3]) / 100.0;
                }
            }
        }

        // todo check
        /*
        if(comm.request((byte)0x46, "0.00")) // fpCashInOut
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 4 && ansItems[0].equals("P"))
                {
                    PrinterFunctions.cashData.cashAvail = CommonFn.ParseDouble(ansItems[1]) / 100.0;
                    PrinterFunctions.cashData.paymentsCashIn = CommonFn.ParseDouble(ansItems[2]) / 100.0;
                    PrinterFunctions.cashData.paymentsCashOut = CommonFn.ParseDouble(ansItems[3]) / 100.0;
                }
            }
        }
        */

        if(!res)
            fpError = readLastError();

        return res;
    }

    @Override
    public boolean fpGetDiagInfo(boolean shooting) {
        // 90 (5Ah) ДИАГНОСТИЧНА ИНФОРМАЦИЯ
        // Област за данни:	{Calculate}
        // Отговор:		{FirmwareRev}{Space}{FirmwareDate}{Space}{FirmwareTime},{ChekSum},{Sw},{Country},{SerNum},{FM}
        //              {FirmwareRev}{Space}{FirmwareDate}{Space}
        //              {FirmwareTime},{ChekSum},{Sw},{Country},{SerNum},{FM}

        boolean res = false;

        if(!checkComm())
            return false;

        Log.i(TAG,"fpGetDiagInfo");

        if(comm.request((byte)0x5A, "0"))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 6) {
                    setVersion(ansItems[0]);
                    setSerial(ansItems[ansItemNum-2]);
                    setFm_num(ansItems[ansItemNum-1]);
                }
            }
        }

        if(!res)
            fpError = readLastError();

        return res;
    }

    public boolean fpFiscalize() {
        return true;
    }
    public boolean fpGetFDFMNum() {
        return true;
    }

    public boolean fpEJ_initialize() {
        return true;
    }

    public boolean fpXReport()
    {
        setLastError("");
        // 69 (45h) ДНЕВЕН ФИНАНСОВ ОТЧЕТ С ИЛИ БЕЗ НУЛИРАНЕ
        // Област за данни:	[[Item]Option]
        // Отговор: 	[Closure,FM_Total,Total1,Total2,Total3,Total4,Total5,Total6,Total7, Total8]

        boolean res = false;

        if(!checkComm(true))
            return false;

        res = comm.request((byte)0x45, "2");
        if(res) res = analyzeAnswer();

        if(!res)
            fpError = readLastError();

        return res;
    }

    public boolean fpZReport()
    {
        setLastError("");
        // 69 (45h) ДНЕВЕН ФИНАНСОВ ОТЧЕТ С ИЛИ БЕЗ НУЛИРАНЕ
        // Област за данни:	[[Item]Option]
        // Отговор: 	[Closure,FM_Total,Total1,Total2,Total3,Total4,Total5,Total6,Total7, Total8]

        boolean res = false;

        if(!checkComm(true))
            return false;

        res = comm.request((byte)0x45, "0");
        if(res) res = analyzeAnswer();

        if(res && ansItemNum >= 10)
        {
            setClosure(CommonFn.ParseInt(ansItems[0]) + 1); // netx closure
            setBonFisc(1);

            for(int i = 0; i < VAT_SUMS.numVats && (i+2 < ansItemNum); i++)
                vatSums.sum[i] = CommonFn.ParseDouble(ansItems[i + 2]) / 100.0;

            fpSetPayments();
        }

        if(!res)
            fpError = readLastError();

        return res;
    }

    public boolean fpSetPayments() {
        // 151 (97h) ПРОГРАМИРАНЕ НА ПЛАЩАНИЯ (ВАЛУТИ)
        // Област за данни:	{Item}{Data}
        // Отговор:		{Data}

        // Item	Определя вида на исканата операция. Възможните стойности са: “P” и “R”.
        //  “P” – програмиране наименование и валутен курс на плащания.
        //                 Област за данни:	{P}{Number},{Name}[{TAB}{Rate},TagNo]

        // “R” – прочитане стойност на параметър.
        //         Област за данни:	{R}{Number}
        //         Отговор		{Number},{Name}{TAB}{Rate},{TagNo}

        //         Number	Номер на плащане	(от 1 до #PAY_MAX_CNT#)
        //         Name	Име на плащане. "Отрязва" се отдясно, ако е по-дълго от #NAME_LEN# символа.
        // TAB	Разделител (ASCII 09)
        //         Rate	Курс на плащането спрямо лева.
        //         TagNo	Номер на Tag за изпращане на данни по X и Z задачи към сървъра на НАП.
        //         0 = <SCash>, 1 = < SChecks>, ......., 10 = <SR2>

        boolean res = false;

        // {P}{Number},{Name}[{TAB}{Rate},TagNo]

        // P N D C
        String data = String.format("P2,%s\t1.00,1",App.context.getString(R.string.payment_check));
        if(comm.request((byte)151, data))
            res = analyzeAnswer();

        if (res) {
            data = String.format("P1,%s\t1.00,8", App.context.getString(R.string.payment_credit));
            if (comm.request((byte) 151, data))
                res = analyzeAnswer();

            if (res) {
                data = String.format("P3,%s\t1.00,7", App.context.getString(R.string.payment_card));
                if (comm.request((byte) 151, data))
                {
                    res = analyzeAnswer();
                    if(!res)
                        Log.e(TAG,"ERROR: fpSetPayments string");

                }
            }
            else
                 Log.e(TAG,"ERROR: fpSetPayments CREDIT");
        }
        else
            Log.e(TAG,"ERROR: fpSetPayments CHEQUE");


        if(!res)
            fpError = readLastError();

        return res;
    }

    public boolean fpGetCurrentZData() {
        // 65 (41h) ТЕКУЩИ НЕТНИ/ОБЩИ СУМИ
        // Област за данни:	[Type]
        // Отговор:	Zlate,Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7, Tax8
        // версия 2020г. отговор: Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7,Tax8,StTax1,StTax2, StTax3,StTax4,StTax5,StTax6,StTax7,StTax8

        boolean res = false;
        int i, index;

        if(!checkComm())
            return false;

        if(comm.request((byte)0x41, "T")) // общо, вкл. сторна
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= VAT_SUMS.numVats + 1)
                {
                    //PrinterFunctions.cashData.closure = getClosure();
                    for(i = 0; i < VAT_SUMS.numVats; i++)
                    {
                        PrinterFunctions.cashData.vatSumsBrutto.sum[i] = CommonFn.ParseDouble(ansItems[i/*+1*/]) / 100.0;
                    }
                }

                if(ansItemNum >= 2 * VAT_SUMS.numVats)
                {
                    for(i = 8; i < 2 * VAT_SUMS.numVats; i++)
                    {
                        PrinterFunctions.cashData.vatSumsStorno.sum[i-8] = CommonFn.ParseDouble(ansItems[i]) / 100.0;
                    }
                }
            }
        }

        if(comm.request((byte)0x41, "N")) // netto
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= VAT_SUMS.numVats + 1)
                {
                    PrinterFunctions.cashData.closure = getClosure();
                    for(i = 0; i < VAT_SUMS.numVats; i++)
                    {
                        PrinterFunctions.cashData.vatSumsNetto.sum[i] = CommonFn.ParseDouble(ansItems[i+1]) / 100.0;
                    }
                }
            }
        }

        PrinterFunctions.cashData.numStorno = 0;
        for(int op = 20/*1*/; op < 21 /*31*/; op++) // използвали сме само 20!!!
            if(comm.request((byte)0x70, String.valueOf(op))) // Информация за оператор
            {
                // Отговор: Receipts,Total,Discount,Surcharge,Void,Name <Tab>RefClaim,RefError,RefOther,RepClaim,RepError, RepOther
                res = analyzeAnswer();
                if(res)
                {
                    //        0     1        2         3         4             5        6        7        8        9        10
                    // Receipts,Total,Discount,Surcharge,Void,Name <Tab>RefClaim,RefError,RefOther,RepClaim,RepError, RepOther
                    if(ansItemNum >= 8 && !ansItems[0].equals("F"))
                    {
                        for(i = 8; i < ansItemNum; i++) {
                            index = ansItems[i].indexOf(';'); // ansItems[i] = "13;335.78"
                            if(index > 0) {
                                PrinterFunctions.cashData.numStorno += CommonFn.ParseInt(ansItems[i].substring(0, index));
                            }
                        }
                    }
                }
                else
                    break;
            }
            else
                break;

        Log.i(TAG, String.format("Daisy/Base numStorno %d, sumStorno %f", PrinterFunctions.cashData.numStorno, PrinterFunctions.cashData.vatSumsStorno.Total()));

        if(!res)
            fpError = readLastError();

        return res;
    }

    public boolean fpGetCurrentVATRates(VAT_SUMS vatSum)
    {
        // 97 (61h) ТЕКУЩИ ДАНЪЧНИ СТАВКИ
        // Област за данни:	Няма данни
        // Отговор:		Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7,Tax8

        boolean res = false;
        int i;

        if(!checkComm())
            return false;

        if(comm.request((byte)0x61, ""))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 8 && ansItemNum >= VAT_SUMS.numVats)
                {
                    for(i = 0; i < VAT_SUMS.numVats; i++)
                    {
                        if(vatSum == null) {
                            PrinterFunctions.cashData.vatSumsBrutto.rate[i] = CommonFn.ParseDouble(ansItems[i]) / 100.0;
                            PrinterFunctions.cashData.vatSumsNetto.rate[i] = CommonFn.ParseDouble(ansItems[i]) / 100.0;
                            vatSums.rate[i] = CommonFn.ParseDouble(ansItems[i]) / 100.0;
                        }
                        else
                            vatSum.rate[i] = CommonFn.ParseDouble(ansItems[i]) / 100.0;
                    }
                }
            }
        }

        if(!res)
            fpError = readLastError();

        return res;
    }

    public double fpCashInOut(double amount)
    {
        // 70 (46h) СЛУЖЕБНО ВЪВЕДЕНИ И ИЗВЕДЕНИ СУМИ
        // Област за данни:	{Amount}[,{Text1}[{CR}{Text2}]]
        // Отговор:		Code,CashSum,ServInput,ServOutput

        setLastError("");

        boolean res = false;
        int i;
        String data = String.format("%.2f", amount).replace(',', '.');

        Log.i(TAG,"fpCashInOut:" + data);

        if(!checkComm(true))
            return -1;

        if(comm.request((byte)0x46, data))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 2 && ansItems[0].equals("P"))
                    return CommonFn.ParseDouble(ansItems[1]);
            }
        }

        if(!res)
            Log.e(TAG,"ERROR: fpCashInOut");

        return -1;
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
                        statusFP.statusBytes[i] = answer[i+4];
                    }
                else
                    res = false;
            }
        }

        if(!res)
            Log.e(TAG,"ERROR: fpGetStatus");

        return true;
    }

    @Override
    public boolean fpPrintBarcode(String barcode)
    {
        // 84 (54h) ПЕЧАТ НА БАРКОД
        // Област за данни:	Type,Data[<TAB>Pos[,Scale[,High[,PrnText]]]]
        // Отговор:		Няма данни

        boolean res = false;
        String data = String.format("2,%s", barcode);

        Log.i(TAG,"fpPrintBarcode");

        res = comm.request((byte)84, data);
        if(res)
            res = analyzeAnswer();

        if(!res)
            Log.e(TAG,"ERROR: fpPrintBarcode");

        return res;
    }

    public boolean checkDateTime(LocalDateTime date)
    {
        /*
        // прочети от ФП
        String fpDateTime = fpGetDateTime();
        PrinterFunctions.cashData.dateTime = fpDateTime;

        if(fpDateTime != null && fpDateTime.length() < 10)
            return true;
*/
        /*
        if(CommonFn.DateNowPattern("dd-MM-yyyy HH:mm").substring(0,10).equals(fpDateTime.substring(0,10)))
            return true;
        */

        return fpSetDateTime(date);
    }

    public String fpGetDateTime()
    {
        // 62 (3Eh) ИНФОРМАЦИЯ ЗА ДАТА И ЧАС
        // Област за данни:	Няма данни.
        // Отговор:		{DD–MM–YY}{Space}{HH:MM:SS}

        if(!checkComm(true))
            return "";

        boolean res = false;
        if(comm.request((byte)62, ""))
        {
            res = analyzeAnswer();
            if(res)
            {
                if(ansItemNum >= 3) {
                    Log.i(TAG,"fpGetDateTime->" + ansItems[0] + "/" + ansItems[2]);
                    return  ansItems[0] + "/" + ansItems[2];
                }
                else
                    if(ansItemNum == 1) {

                        int indexSpace = ansItems[0].indexOf(' ');
                        if(indexSpace > 0 && indexSpace < ansItems[0].length())
                        {
                            Log.i(TAG,"fpGetDateTime->" + ansItems[0].substring(0, indexSpace) + "/" + ansItems[0].substring(indexSpace + 1));
                            return ansItems[0].substring(0, indexSpace) + "/" + ansItems[0].substring(indexSpace + 1);
                        }
                    }
            }
        }

        if(!res)
            Log.e(TAG,"ERROR: fpGetDateTime");

        return "";
    }

    public boolean fpSetDateTime(LocalDateTime date)
    {
        // 61 (3Dh) ВЪВЕЖДАНЕ НА ДАТА И ЧАС
        // Област за данни:	{DD–MM–YY}{space}{HH:MM[:SS]}
        // Отговор:		Няма данни

        boolean res = false;
        String data = (date == null ? DateUtil.Companion.getCurrentDateTimeWithPatternAndDate(LocalDateTime.now(),new SimpleDateFormat("dd-MM-yy HH:mm")) : (new SimpleDateFormat("dd-MM-yy HH:mm").format(date)));

        Log.i(TAG,"fpSetDateTime");

        res = comm.request((byte)0x3D, data);
        if(res)
            res = analyzeAnswer();

        if(!res)
            Log.e(TAG,"ERROR: fpSetDateTime");

        return res;
    }

    public String fpGetPassword() {
        return "0000";
    }
        /************** reports **************/
    public boolean fpReportByNumber(int start, int end)
    {
        setLastError("");
        // 73 (49h) ОТЧЕТ ОТ ФП ПО НОМЕР
        // Област за данни:	{StartNum},{EndNum}
        // Отговор:		Няма данни

        boolean res = false;
        String data = String.format("%d,%d", start, end);

        if(!checkComm(true))
            return false;

        res = comm.request((byte)73, data);
        if(res) res = analyzeAnswer();

        return res;

    }

    public boolean fpReportByNumberShort(int start, int end)
    {
        setLastError("");
        // 95 (5Fh) СЪКРАТЕН ОТЧЕТ ОТ ФП ПО НОМЕР
        // Област за данни:	{StartNum},{EndNum}
        // Отговор:		Няма данни

        boolean res = false;
        String data = String.format("%d,%d", start, end);

        if(!checkComm(true))
            return false;

        res = comm.request((byte)0x5F, data);
        if(res) res = analyzeAnswer();

        return res;

    }

    public boolean fpReportByDateShort(String dtFrom, String dtTo)
    {
        setLastError("");
        // 79 (4Fh) СЪКРАТЕН ОТЧЕТ ОТ ФП ПО ДАТА
        // Област за данни:	 {StartDate},{EndDate}
        // Отговор:		Няма данни

        // StartDate 	 	Начална дата с дължина 6 символа (DDMMYY)
        // EndDate		Крайна дата с дължина 6 символа (DDMMYY)

        boolean res = false;
        String data = String.format("%s,%s", dtFrom, dtTo);

        if(!checkComm(true))
            return false;

        res = comm.request((byte)79, data);
        if(res) res = analyzeAnswer();

        return res;
    }
    public boolean fpReportByDate(String dtFrom, String dtTo)
    {
        setLastError("");setLastError("");
        // 94 (5Eh) ОТЧЕТ ОТ ФП ПО ДАТА
        // Област за данни:	{StartDate},{EndDate}
        // Отговор:		Няма данни

        // StartDate 	 	Начална дата с дължина 6 символа (DDMMYY)
        // EndDate		Крайна дата с дължина 6 символа (DDMMYY)

        boolean res = false;
        String data = String.format("%s,%s", dtFrom, dtTo);

        if(!checkComm(true))
            return false;

        res = comm.request((byte)94, data);
        if(res) res = analyzeAnswer();

        return res;
    }

    public boolean fpReportByDept()
    {
        // 165 (A5h) ОТЧЕТ ПО ДЕПАРТАМЕНТИ
        // Област за данни:	[RepType]
        // Отговор:		Code

        return true;
    }

    public boolean fpSetPassword(int opNo, String oldPassword, String newPassword)
    {
        // 101 (65h) ПАРОЛА НА ОПЕРАТОР
        // Област за данни:	{ClerkNumber},{OldPsw},{NewPsw}
        // Отговор:		Няма данни

        return true;
    }
    public boolean fpSetOperatorName(int opNo, String password, String Name)
    {
        // 102 (66h) ИМЕ НА ОПЕРАТОР
        // Област за данни:	{ClerkNum},{Password},{Name}
        // Отговор:		Няма данни


        return true;
    }
    public boolean fpGetVatInfoByNumber()
    {
        // 114 (72h) ИНФОРМАЦИЯ ОТ ФП ПО НОМЕР
        // Област за данни:	{FiskNum}[,{Options}[,FiskNum1]]
        // Отговор:		Code, Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7,Tax8


        return true;
    }
    public boolean fpSellDet()
    {
        // 138 (8Ah) ПРОДАЖБА ПО ДЕПАРТАМЕНТ
        // Област за данни:	{[Sign]Dept}{@Price} [*{QTY}][,Percent] [$Netto]
        // Отговор:		Няма данни

        return true;
    }
    public boolean fpSetHeaderFooter()
    {
        // 149 (95h) ПРОГРАМИРАНЕ НА ТЕКСТОВО ПОЛЕ
        // Област за данни:	{Item}{Data}
        // Отговор:		{Data}


        return true;
    }

    public boolean fpPrintBonFromKLEN(int start, int end)
    {
        // 195(C3h) СМЯНА НА КЛЕН И ОТЧЕТИ ОТ КЛЕН
        // Област за данни:	{Item}[,Data]
        // Отговор:		Code

        // Item = “I” 	Смяна на КЛЕН (...)
        // Item = “S” 	Печат на сумарни данни за КЛЕН (...)

        // Item = “R” 	Печат/четене на данни от КЛЕН
        // Област за данни:	{R}{PrnType},{Bgn},{End}[,Send]

        boolean res = false;
        String data = String.format("R21,%d,%d", start, end);

        if(!checkComm(true))
            return false;

        res = comm.request((byte)195, data);
        if(res) res = analyzeAnswer();

        return res;
    }

    public void Init(String serialNumber) {}

    public boolean isFDFMNumSet() { return true; }



    public boolean paperOK() {
        // unsuccessful print
        // 1. check paper
        // if paper OK -> return true
        // if paper not ok -> return false;

        pingFPError();
        if(isPaperError())
        {
            Log.e(TAG, "Error: no paper");
            return false;
        }
        return true;
    }

    // returns 0 - OK
    // 1 - no paper
    // 2 - busy
    // 3 - overheat
    // -1 - communication error
    public int pingFPError()
    {
        //if(fpGetStatus()) // not necessary - status is in the answer of every message for daisy and eltrade
            if(getStatus().NO_PAPER())
                return 1;
            else
                if(!getStatus().getError())
                    return 0;
                else
                    return -1;

        //return -1;
    }

    public boolean isPaperError() { return getStatus().NO_PAPER(); }
    public boolean isFDBusy() { return false; }

    public boolean fpGetGlobalBon()
    {
        boolean res = false;


        if(noCommSetError())
            return false;

        res = comm.request((byte)0x71, "");
        if(res)
        {
            res = analyzeAnswer();
            if(res)
                if(ansItemNum >= 1)
                    setBonGlobal(CommonFn.ParseLong(ansItems[0]) + 1); // връща последно отпечатания бон: Дейзи, Елтрейд, Датекс 150, Тремол
        }

        if(!res)
            Log.e(TAG, "ERROR: fpGetGlobalBon");

        return res;
    }

    public boolean fpGetBonFisc()
    {
        return fpGetInfo();
    }

    public String getClosureParam() {return "T";}
    public int getClosureIndexParam() { return 0; }

    public boolean fpGetClosure() {
        // 64 (40h) ПОСЛЕДЕН ФИСКАЛЕН ЗАПИС
        // Област за данни:	[Type]
        // Отговор:		Number,SpaceGr,Tax1,Tax2,Tax3,Tax4,Tax5,Tax6,Tax7,Tax8,Date


        boolean res = false;

        Log.i(TAG, "fpGetClosure");

        if(!checkComm())
            return false;

        if(comm.request((byte)0x40, getClosureParam())) // общо
        {
            res = analyzeAnswer();
            if (res) {
                if (ansItemNum >= 10 && ansItemNum >= VAT_SUMS.numVats + 2)
                {
                    // ZRepNo
                    setClosure(CommonFn.ParseInt(ansItems[getClosureIndexParam()]) + 1);
                    PrinterFunctions.cashData.closure = CommonFn.ParseInt(ansItems[getClosureIndexParam()]) + 1;
                }
                else
                    if (ansItemNum == 1)
                    {
                        if(ansItems[0].equals("F"))
                        {
                            setClosure(1);
                            Log.i(TAG, "fpGetClosure returns 'F' - assuming closure = 1");
                            res = true;
                        }
                    }
            }
        }

        if(!res)
            Log.e(TAG, "ERROR: fpGetClosure");

        return res;
    }

    public int getValidAddressLength() { return 36; }
    public String getValidAddressDelim() { return "\n"; }
    public String getValidAddressDelim2() { return ""; }

    public String getValidAddress()
    {
        String address;
        int maxLen = getValidAddressLength();

        if(client == null || client.getAddress() == null)
            address = "";
        else
            if(client.getAddress().length() > maxLen)
            {
                address = client.getAddress().substring(0,maxLen) + getValidAddressDelim() + client.getAddress().substring(maxLen);
                if (address.length() > (maxLen * 2 + 1))
                    address = address.substring(0, maxLen * 2 + 1);
            }
            else
            {
                address = client.getAddress();
                address += getValidAddressDelim2(); // 25x : "address line \t address line2" или address line \t
            }

        return address;
    }

    public boolean isPaid(Bon bon) {
        return Math.abs(bon.getTotal()) > 0.00001 && fpGetReceiptPayment() && Math.abs(Math.abs(paidFP) - Math
                .abs(bon.getTotal())) < 0.00001;
    }
}

