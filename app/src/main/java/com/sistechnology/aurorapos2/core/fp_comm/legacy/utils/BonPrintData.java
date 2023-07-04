package com.sistechnology.aurorapos2.core.fp_comm.legacy.utils;



import com.sistechnology.aurorapos2.core.fp_comm.legacy.Bon;
import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by MARIELA on 31.8.2017 г..
 */

public class BonPrintData
{
    public Bon bonParent;
    public List<String> listToPrintHeader = new ArrayList();
    public List<String> listToPrint = new ArrayList();
    public String textToPrint = "";
    public String textToPrintTitle = "";
    public String textToPrintSum = "";
    public String textToPrintHeader = "";
    public String textToPrintHeaderLeft = "";
    public String textToPrintFooter = "";
    public String textToPrintTotal = "";
    public String textToPrintTotalSum = "";
    public String textToPrintPayments = "";
    public String textToPrintPaymentsSum = "";
    public String textToPrintVAT = "";
    public String textToPrintCustomer = "";
    public String textToPrintPAX = "";
    public String textToPrintVoucher = "";
    public String voucherBarcode = "";
    public boolean printable;
    public boolean printBonNo;
    private SharedPreferencesHelper sharedPreferencesHelper;

 
    public Bon getBonParent() { return bonParent; }
    public void setBonParent(Bon bonParent) { this.bonParent = bonParent; }

    
    public boolean isPrintable() { return printable; }
    public void setPrintable(boolean printable) {this.printable = printable; }

    public BonPrintData(Bon bonParent)
    {
        this.bonParent = bonParent;
    }

    
    public List<String> getListToPrintHeader() {
        return listToPrintHeader;
    }
    
    public List<String> getListToPrint() {
        return listToPrint;
    }

    
    public String getTextToPrint() { return textToPrint; }
    public void setTextToPrint(String textToPrint) { this.textToPrint = textToPrint; }

    
    public String getTextToPrintSum() { return textToPrintSum; }
    public void setTextToPrintSum(String textToPrintSum) { this.textToPrintSum = textToPrintSum; }

    
    public String getTextToPrintHeader() { return textToPrintHeader; }
    public void setTextToPrintHeader(String textToPrintHeader) { this.textToPrintHeader = textToPrintHeader; }

    
    public String getTextToPrintHeaderLeft() { return textToPrintHeaderLeft; }
    public void setTextToPrintHeaderLeft(String textToPrintHeaderLeft) { this.textToPrintHeaderLeft = textToPrintHeaderLeft; }

    
    public String getTextToPrintTitle() { return textToPrintTitle; }
    public void setTextToPrintTitle(String textToPrintTitle) { this.textToPrintTitle = textToPrintTitle; }

    
    public String getTextToPrintFooter() { return textToPrintFooter; }
    public void setTextToPrintFooter(String textToPrintFooter) { this.textToPrintFooter = textToPrintFooter; }


    
    public String getTextToPrintTotal() { return textToPrintTotal; }
    public void setTextToPrintTotal(String textToPrintTotal) { this.textToPrintTotal = textToPrintTotal; }

    
    public String getTextToPrintTotalSum() { return textToPrintTotalSum; }
    public void setTextToPrintTotalSum(String textToPrintTotalSum) { this.textToPrintTotalSum = textToPrintTotalSum; }

    
    public String getTextToPrintPayments() { return textToPrintPayments; }
    public void setTextToPrintPayments(String textToPrintPayments) { this.textToPrintPayments = textToPrintPayments; }

    
    public String getTextToPrintPaymentsSum() { return textToPrintPaymentsSum; }
    public void setTextToPrintPaymentsSum(String textToPrintPaymentsSum) { this.textToPrintPaymentsSum = textToPrintPaymentsSum; }

    
    public String getTextToPrintVAT() { return textToPrintVAT; }
    public void setTextToPrintVAT(String textToPrintVAT) { this.textToPrintVAT = textToPrintVAT; }

    
    public String getTextToPrintCustomer() { return textToPrintCustomer; }
    public void setTextToPrintCustomer(String textToPrintCustomer) { this.textToPrintCustomer = textToPrintCustomer; }

    
    public String getTextToPrintPAX() { return textToPrintPAX; }
    public void setTextToPrintPAX(String textToPrintPAX) { this.textToPrintPAX = textToPrintPAX; }

    
    public String getTextToPrintVoucher() { return textToPrintVoucher; }
    public void setTextToPrintVoucher(String textToPrintVoucher) { this.textToPrintVoucher = textToPrintVoucher; }

    public List<String> toStringListReceipt()
    {
//        if(bonParent == null) TODO
//            return null;
//
//
//
//
//
//
//        int i;
//
//        textToPrint = "";
//        textToPrintSum = "";
//        textToPrintHeader = "";
//        textToPrintHeaderLeft = "";
//        textToPrintTitle = "";
//        textToPrintFooter = "";
//        textToPrintTotal = "";
//        textToPrintTotalSum = "";
//        textToPrintPayments = "";
//        textToPrintPaymentsSum = "";
//        textToPrintVAT = "";
//        textToPrintCustomer = "";
//        textToPrintPAX = "";
//        textToPrintVoucher = "";
//        voucherBarcode = "";
//
//        listToPrint.clear();
//        listToPrintHeader.clear();
//
//        // HEADER TO PRINT CENTER
//
//        // company name
//        listToPrintHeader.add(centerStr(sharedPreferencesHelper.getCompanyInfo().getCompanyName(), 28));
//        // address
//        listToPrintHeader.add(centerStr(sharedPreferencesHelper.getCompanyInfo().getAddress(), 28));
//        // ИД номер
//        listToPrintHeader.add(centerStr(App.context.getString(R.string.idnomer) + ": " + sharedPreferencesHelper.getCompanyInfo()
//                .getRegNumber(), 28));
//        // ЕИК
//        listToPrintHeader.add(centerStr(App.context.getString(R.string.zddsno) + ": " + sharedPreferencesHelper.getCompanyInfo()
//                .getVatNumber(), 28));
//
//        for(i=0; i<listToPrintHeader.size(); i++)
//            textToPrintHeader += listToPrintHeader.get(i) + "\n";
//
//        if(Printer.Companion.getCurrentBon().isDuplicateLevel())
//        {
//            listToPrint.add("\n");
//            listToPrint.add(centerStr(App.context.getString(R.string.duplicate), 28));
//            listToPrint.add("\n");
//        }
//
//        for(; i<listToPrint.size(); i++)
//            textToPrintHeader += listToPrint.get(i) + "\n";
//
//        // TITLE
//        String title = "";
//        if(Printer.Companion.getCurrentBon().isReversal())
//            title = App.context.getString(R.string.reversal);
//        listToPrint.add(title);
//        textToPrintTitle = title;
//
//
//
//        // HEADER TO PRINT LEFT
//        if(bonParent.getUsn() != null && !bonParent.getUsn().equals("")) {
//            listToPrint.add(String.format("%s %s", App.GetString(R.string.usn), bonParent.getUsn()));
//            textToPrintHeaderLeft += listToPrint.get(listToPrint.size() - 1) + "\n";
//        }
//
//        // вътрешен номер
//        listToPrint.add(String.format("%s %d", App.GetString(R.string.bon_fisc_number), bonParent.receiptNumber));
//        textToPrintHeaderLeft += listToPrint.get(listToPrint.size()-1) + "\n";
//
//        if(bonParent.bonToBePrintedAs != 1) /// 0 - неотпечатан, 1 - нефискален (разписка), 2 - фискален, 3 - фактура, която не се печата
//        {
//            // фискален бон
//            listToPrint.add(String.format("%s %d", App.GetString(R.string.fisc_number), bonParent.bonFisc));
//            textToPrintHeaderLeft += listToPrint.get(listToPrint.size() - 1) + "\n";
//
//            // нулиране
//            listToPrint.add(String.format("%s %d", App.GetString(R.string.closure), bonParent.bonClosure));
//            textToPrintHeaderLeft += listToPrint.get(listToPrint.size() - 1) + "\n";
//        }
//
//        // ARTICLES
//        for (i = 0; i < bonParent.list.size(); i++)
//        {
//            if(Math.abs(bonParent.list.get(i).getBonItem_quan() - 1) > 0.0001) {
//                listToPrint.add(bonParent.list.get(i).toStringQuan());
//
//                textToPrint += listToPrint.get(listToPrint.size()-1) + "\n";
//                textToPrintSum += "\n";
//
//                // ако това е отстъпка сума и е използван ваучер:
//                if(bonParent.list.get(i) instanceof BonDataProvider_Discount && bonParent.list.get(i).isbSubtDisc() &&
//                   bonParent.getVoucherDataPaid() != null)
//                {
//                    // използван ваучер
//                    listToPrint.add(bonParent.getVoucherDataPaid().getBarcode());
//
//                    textToPrint += bonParent.getVoucherDataPaid().getBarcode() + "\n";
//                    textToPrintSum += "\n";
//                }
//            }
//            listToPrint.add(bonParent.list.get(i).toStringReg());
//
//            textToPrint += bonParent.list.get(i).getBonItem_title() + "\n";
//            textToPrintSum += String.format("%8.2f%s",
//                                            BigDecimal.valueOf(bonParent.list.get(i).getBonItem_quan()).multiply(BigDecimal.valueOf(bonParent.list.get(i).getBonItem_price())).doubleValue() - bonParent.list.get(i).getBonItem_discount(), bonParent.list.get(i).getVat().getSym() + "\n");
//
//            if(bonParent.list.get(i).getSerialNumber() != null && !bonParent.list.get(i).getSerialNumber().equals(""))
//            {
//                listToPrint.add(App.GetString(R.string.serialNumber) + " " + bonParent.list.get(i).serialNumber);
//
//                textToPrint += App.GetString(R.string.serialNumber) + " " + bonParent.list.get(i).serialNumber + "\n";
//                textToPrintSum += "\n";
//            }
//        }
//
//        bonParent.PrintVATData();
//
//        // TOTAL
//        listToPrint.add("----------------------------");
//
//        listToPrint.add(String.format("%-18s %8.2f", App.GetString(R.string.total_rcpt), bonParent.isCanceled() ? 0.0 :bonParent.getTotal()));
//
//        textToPrintTotal = App.GetString(R.string.total_rcpt) + "\n";
//        textToPrintTotalSum = String.format("%8.2f ", bonParent.isCanceled() ? 0.0 : bonParent.getTotal()) + "\n";
//
//        if(bonParent.isCanceled())
//        {
//            listToPrint.add(App.GetString(R.string.canceled_receipt));
//
//            textToPrintPayments += App.GetString(R.string.canceled_receipt) + "\n";
//        }
//        else
//            // PAYMENTS
//            if(bonParent.listPayments.size() > 0) {
//                for (i = 0; i < bonParent.listPayments.size(); i++) {
//                    if (Math.abs(bonParent.listPayments.get(i).getBonItem_sum()) > 0.00001) {
//                        listToPrint.add(bonParent.listPayments.get(i).toStringReg());
//
//                        textToPrintPayments += bonParent.listPayments.get(i).getBonItem_title() + "\n";
//                        textToPrintPaymentsSum += String.format("%8.2f ", bonParent.listPayments.get(i)
//                                                                                                .getBonItem_sum()) + "\n";
//                    }
//                }
//
//                if(Math.abs(bonParent.getRest()) > 0.0001)
//                {
//                    listToPrint.add(String.format("%-18s %8.2f", App.GetString(R.string.resto), bonParent.getRest()));
//
//                    textToPrintPayments += App.GetString(R.string.resto) + "\n";
//                    textToPrintPaymentsSum += String.format("%8.2f ", bonParent.getRest()) + "\n";
//                }
//            }
//            else
//            {
//                if(bonParent.cashAmountPaid > 0.0d)
//                {
//                    listToPrint.add(String.format("%-18s %8.2f", App.GetString(R.string.cash), bonParent.cashAmountPaid));
//
//                    textToPrintPayments += App.GetString(R.string.cash) + "\n";
//                    textToPrintPaymentsSum += String.format("%8.2f ", bonParent.cashAmountPaid) + "\n";
//
//                    if(Math.abs(bonParent.getRest()) > 0.0001)
//                    {
//                        listToPrint.add(String.format("%-18s %8.2f", App.GetString(R.string.resto), bonParent.getRest()));
//
//                        textToPrintPayments += App.GetString(R.string.resto) + "\n";
//                        textToPrintPaymentsSum += String.format("%8.2f ", bonParent.getRest()) + "\n";
//                    }
//                }
//
//                if(bonParent.cardAmountPaid > 0.0d)
//                {
//                    listToPrint.add(String.format("%-18s %8.2f", App.GetString(R.string.card), bonParent.cardAmountPaid));
//
//                    textToPrintPayments += App.GetString(R.string.card) + "\n";
//                    textToPrintPaymentsSum += String.format("%8.2f ", bonParent.cardAmountPaid) + "\n";
//                }
//                if(bonParent.checkAmountPaid > 0.0d)
//                {
//                    listToPrint.add(String.format("%-18s %8.2f", App.GetString(R.string.check), bonParent.checkAmountPaid));
//
//                    textToPrintPayments += App.GetString(R.string.check) + "\n";
//                    textToPrintPaymentsSum += String.format("%8.2f ", bonParent.checkAmountPaid) + "\n";
//                }
//                if(bonParent.creditAmountPaid > 0.0d)
//                {
//                    listToPrint.add(String.format("%-18s %8.2f", App.GetString(R.string.credit), bonParent.creditAmountPaid));
//
//                    textToPrintPayments += App.GetString(R.string.credit) + "\n";
//                    textToPrintPaymentsSum += String.format("%8.2f ", bonParent.creditAmountPaid) + "\n";
//                }
//            }
//
//        bonParent.PrintCustomerData();
//
//        bonParent.PrintCardPaymentSlip();
//
//        bonParent.PrintVoucherSlip();
//
//        // FOOTER
//        // за сторно: дата,  час и номер на оригиналния бон
//        if(bonParent.isfReversal())
//            bonParent.PrintOriginalBonData();
//        else
//            textToPrintFooter += App.GetString(R.string.footer) + "\n";
//
//        // listToPrint.add(App.GetString(R.string.footer));
//
//        textToPrintFooter += CommonFn.DateNowReceiptEnd() + "\n";
//        /*
//        if (PrinterFunctions.isNonFiscalDeviceAvail())
//            listToPrint.add(CommonFn.DateNowReceiptEnd());*/
//
//        notifyPropertyChanged(BR.textToPrintHeader);
//        notifyPropertyChanged(BR.textToPrintHeaderLeft);
//        notifyPropertyChanged(BR.textToPrintTitle);
//        notifyPropertyChanged(BR.textToPrint);
//        notifyPropertyChanged(BR.textToPrintSum);
//        notifyPropertyChanged(BR.textToPrintFooter);
//        notifyPropertyChanged(BR.textToPrintTotal);
//        notifyPropertyChanged(BR.textToPrintTotalSum);
//        notifyPropertyChanged(BR.textToPrintPayments);
//        notifyPropertyChanged(BR.textToPrintPaymentsSum);
//        notifyPropertyChanged(BR.textToPrintVAT);
//        notifyPropertyChanged(BR.textToPrintCustomer);
//        notifyPropertyChanged(BR.textToPrintPAX);
//        notifyPropertyChanged(BR.textToPrintVoucher);

        return listToPrint;
    }

    public void CopyFrom(BonPrintData printData)
    {
        if(printData == null)
            return;

        bonParent = printData.bonParent;

        listToPrintHeader.addAll(printData.listToPrintHeader);
        /*
        for(int h = 0; h<printData.listToPrintHeader.size(); h++)
        {
            String str = printData.listToPrintHeader.get(h);
            listToPrintHeader.add(str);
        }*/

        listToPrint.addAll(printData.listToPrint);
        /*
        for(int i = 0; i<printData.listToPrint.size(); i++)
        {
            String str = printData.listToPrint.get(i);
            listToPrint.add(str);
        }
        */

        textToPrint = printData.textToPrint;
        textToPrintSum = printData.textToPrintSum;
        textToPrintHeader = printData.textToPrintHeader;
        textToPrintHeaderLeft = printData.textToPrintHeaderLeft;
        textToPrintTitle = printData.textToPrintTitle;
        textToPrintFooter = printData.textToPrintFooter;
        textToPrintTotal = printData.textToPrintTotal;
        textToPrintTotalSum = printData.textToPrintTotalSum;
        textToPrintPayments = printData.textToPrintPayments;
        textToPrintPaymentsSum = printData.textToPrintPaymentsSum;
        textToPrintVAT = printData.textToPrintVAT;
        textToPrintCustomer = printData.textToPrintCustomer;
        textToPrintPAX = printData.textToPrintPAX;
        textToPrintVoucher = printData.textToPrintVoucher;
        voucherBarcode = printData.voucherBarcode;
        printable = printData.printable;
    }

    public static String centerStr(String s, int allLen) {
        if(s == null)
            return "";
        int len = s.length();
        len = (allLen - len) / 2;
        String s1 = padLeft(s, ' ', len + s.length());
        s1 = padRight(s1, ' ', allLen);
        return s1;
    }
    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }
    public static String padLeft(String s, char sym, int n) {
        if(s == null || n < s.length())
            return s;

        String s1 = padLeft(s, n);
        String s2 = s1.replace(' ', sym);

        return s2; //String.format("%" + n + "s", s).replace(' ', sym);
    }
    public static String padRight(String s, char sym, int n) {
        if(s == null || n <= s.length())
            return s;

        return s + String.format("%" + (n - s.length()) + "s", "").replace(" ", String.valueOf(sym));
    }

}