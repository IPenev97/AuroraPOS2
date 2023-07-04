package com.sistechnology.aurorapos2.core.fp_comm.legacy;

import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;



import com.sistechnology.aurorapos2.R;
import com.sistechnology.aurorapos2.App;
import com.sistechnology.aurorapos2.core.fp_comm.Fp_Error;
import com.sistechnology.aurorapos2.core.fp_comm.Printer;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.CashData;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.PrinterFilter;
import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MARIELA on 11.5.2017 г..
 */

public class PrinterFunctions
{
    public static String DEVICE_TYPE_NONE = App.context.getString(R.string.no_device);
    public static String FP_TYPE_DAISY = App.context.getString(R.string.daisy_compact_s);
    public static String FP_TYPE_DAISY_M = App.context.getString(R.string.daisy_compact_m);
    public static String FP_TYPE_ELTRADE = App.context.getString(R.string.eltrade);
    public static String FP_TYPE_TREMOL = App.context.getString(R.string.tremol);
    public static String FP_TYPE_DATECS = App.context.getString(R.string.datecs);
    public static String FP_TYPE_DATECS_DP25x = App.context.getString(R.string.datecs_dp25x);
    public static String FP_TYPE_N5 = App.context.getString(R.string.fpn5);
    public static String NONFP_TYPE_D210 = App.context.getString(R.string.d210_printer);
    public static String NONFP__TYPE_E500 = App.context.getString(R.string.e500_printer);
    public static String NONFP_TYPE_DATECS_EP50 = App.context.getString(R.string.datecs_ep50);
    private static String TAG = "PrinterFunctions";
    private static PrinterFilter filter = new PrinterFilter();





    public static int getFiscalDevice() { return fiscalDevice; }
    public static void setFiscalDevice(int fiscalDevice)

    {


        if(PrinterFunctions.fiscalDevice != fiscalDevice) {

            PrinterFunctions.fiscalDevice = fiscalDevice;

            setCommandSetFP(null);
            if (isFiscalDeviceDaisy())
                setCommandSetFP(new CommandSetFP());
//            else
//                if (isFiscalDeviceEltrade())
//                    setCommandSetFP(new EltradeFP());
//                else
//                    if (isFiscalDeviceTremol())
//                        setCommandSetFP(new TremolFP());
//                    else
//                        if (isFiscalDeviceDatecsDP150())
//                            setCommandSetFP(new DatecsFP());
//                        else
//                            if (isFiscalDeviceDatecsDP25x())
//                                setCommandSetFP(new DatecsDP25x());
//                            else
//                                if(isFiscalDeviceErpNet())
//                                    setCommandSetFP(new ErpNetFP());
//                                else
//                            {
//                                if(commandSetFP != null &&  commandSetFP instanceof N5FP)
//                                    return;
//                                //setCommandSetFP(new N5FP());
//                            }

/*
            if(getCommandSetFP() != null)
                if(getCommandSetFP().fpGetDiagInfo())
                    getCommandSetFP().fpGetClosure();*/
        }
    }
    public static int getNonFiscalDevice() { return nonFiscalDevice; }
    public static void setNonFiscalDevice(int nonFiscalDevice)
    {
        PrinterFunctions.nonFiscalDevice = nonFiscalDevice;
        if (isNonFiscalDeviceDatecsEP50())
            setCommandSetNonFP(new CommandSet());
        else
            setCommandSetNonFP(null);

    }
    public static boolean isNonFiscalDeviceAvail() {

        return nonFiscalDevice != 1; }
    public static boolean isFiscalDeviceAvail() { return  fiscalDevice != 1; }
    public static boolean isFiscalDeviceDaisy() { return fiscalDevice == 2 || fiscalDevice == 3; }
    public static boolean isFiscalDeviceEltrade() { return fiscalDevice == 4; }
    public static boolean isFiscalDeviceTremol() { return fiscalDevice == 5; }
    public static boolean isFiscalDeviceDatecs() { return fiscalDevice == 6 || fiscalDevice == 7; }
    public static boolean isFiscalDeviceDatecsDP150() { return fiscalDevice == 6; }
    public static boolean isFiscalDeviceDatecsDP25x() { return fiscalDevice == 7; }
    public static boolean isFiscalDeviceN5() { return fiscalDevice == 8 || Build.MODEL.equals("N5"); }
    public static boolean isFiscalDeviceErpNet() { return fiscalDevice == 9; }
    public static boolean isNonFiscalDevicePAX() { return nonFiscalDevice == 2; }
    public static boolean isNonFiscalDeviceE500Printer() { return nonFiscalDevice == 3 && (Build.MODEL.equals("E500") || Build.MODEL.equals("E800")); }
    public static boolean isNonFiscalDeviceDatecsEP50() { return nonFiscalDevice == 4; }

    // за БГ, без ЕКАФП, провери хартията, статуса за грешки
    // ако е празен, прочети серийния номер
    // ако е празно, прочети нулирането
    // ако readBonNumbers == true, пусни thread за:
    //    прочитане на глобалния номер на бон
    //    прочитане на фискалния номер на бон

    public static boolean CheckFP_WithMessage() { return CheckFP_WithMessage(false); }
    public static boolean CheckFP_WithMessage(boolean readBonNumbers)
    {

        if(PrinterFunctions.isFiscalDeviceErpNet()){

            if(LegacyPrinter.Companion.getCurrentBon()!=null && LegacyPrinter.Companion.getCurrentBon().isFiscal()) {
                getCommandSetFP().fpGetGlobalBon();
            }
            return true;
        }

        int res = CheckFP(readBonNumbers);
        if(res == 1)
            return true;

        if(res == -1)
            return false;
        else {


//            if (!(App.currentActivity).isFinishing())
//                if (PrinterFunctions.getCommandSetFP() != null)
//                    if (PrinterFunctions.getCommandSetFP().isPaperError()) {
//                        //CommonFn.Toast(App.GetString(R.string.err_no_paper), Toast.LENGTH_LONG);
//                        CommonFn.MessageBox(App.GetString(R.string.err_no_paper));
//                    }
//                    else
//                        //CommonFn.Toast(App.GetString(R.string.error_fp) + " " + PrinterFunctions.getCommandSetFP().getLastErrorText(), Toast.LENGTH_LONG);
//                        CommonFn.MessageBox(PrinterFunctions.getCommandSetFP()
//                                                            .getPrinterType() + " " + App.GetString(R.string.error_fp) + " " + PrinterFunctions
//                                                    .getCommandSetFP()
//                                                    .getStatus()
//                                                    .getStatusText());
//                else
//                    //CommonFn.Toast(App.GetString(R.string.error_fp_no_printer_selected), Toast.LENGTH_LONG);
//                    CommonFn.MessageBox(App.GetString(R.string.error_fp_no_printer_selected));
        }

        return false;
    }

    static int timesCheckFP_Wrong = 0;
    static int timesCheckPermission = 0;

    public static boolean CheckFPPermissionWithMessage(boolean message)
    {
        if(isFiscalDeviceAvail() && getCommandSetFP() != null) // && getCommandSetFP().comm instanceof DaisyCommunicationUSB)
        {
            if(!getCommandSetFP().checkComm(false) && getCommandSetFP().usbDeviceFoundHasNoPermission)
            {
                Log.e(TAG,"CheckFPPermissionWithMessage: fiscal printer usb device null");

                if(message) {
                    timesCheckPermission++;

                    if (timesCheckPermission > 1)
                        Printer.Companion.setError(Fp_Error.FP_NOT_READY);
                    else {
                        // wait until the permission is granted
                        // remember the action and do it after
                    }
                }
                else
                    timesCheckPermission = 0;
                return false;
            }
            else
                Log.i(TAG,"CheckFPPermissionWithMessage: USB fiscal printer found");
        }
        else
            Log.e(TAG,"CheckFPPermissionWithMessage: no USB fiscal printer");

        if(isNonFiscalDeviceDatecsEP50() && getCommandSetNonFP() != null)
        {
            if(!getCommandSetNonFP().checkComm(false))
            {
                Log.e(TAG,"CheckFPPermissionWithMessage: non fiscal printer usb device null");
                //CommonFn.MessageBox(App.GetString(R.string.try_again));
                //return false;
            }
            else
                Log.i(TAG,"CheckFPPermissionWithMessage: non USB fiscal printer found");
        }
        else
            Log.e(TAG,"CheckFPPermissionWithMessage: no USB non fiscal printer");
        Printer.Companion.setError(null);
        return true;
    }

    public static int CheckFP(boolean readBonNumbers)
    {
        Log.i(TAG,"CheckFP start");



        timesCheckFP_Wrong++;

        boolean resGetStatus = false;
        if(isFiscalDeviceAvail() && getCommandSetFP() != null)
        {
            if(timesCheckFP_Wrong > 1)
            {
                getCommandSetFP().fpStopConnection();
                timesCheckFP_Wrong = 0;
                Log.e(TAG,"timesCheckFP_Wrong > 1");
                return 0;
            }

            resGetStatus = getCommandSetFP().fpGetStatus();
            if (resGetStatus)
            {
                // Батерията се тества за СУПТО
                    if (getCommandSetFP().getStatus().BATTERY_LOW()) {
                        Printer.Companion.setError(Fp_Error.NO_BATTERY);
                        return 0;
                    }
            }
            else
            {
                Log.e(TAG,"resGetStatus == false");
                return 0;
            }
        }

        if(isFiscalDeviceAvail() && getCommandSetFP() != null)
        {
                // хартията се проверява само за СУПТО
                if(!getCommandSetFP().paperOK())
                {
                    Log.e(TAG,"no paper");
                    return 0;
                }

            if(resGetStatus) {
                if (getCommandSetFP().getStatus().getError()) {
                    // статус: грешка - само за СУПТО
                    Log.e(TAG,getCommandSetFP().getStatus().getStatusText());
                        return 0;
                }
                else
                    if(getCommandSetFP().getSerial().isEmpty())
                        // няма serial
                        if(!getCommandSetFP().fpGetDiagInfo()) {
                            Log.e(TAG,"error get diag info");
                            return 0;
                        }
                        else
                            if(getCommandSetFP().getSerial().isEmpty()) {
                                // няма serial
                                Log.e(TAG,"error no serial");
                                return 0;
                            }

                // текущо нулиране
                if(getClosure() == 0)
                    if(!getCommandSetFP().fpGetClosure())
                    {
                        Log.e(TAG,"error no closure");
                        return 0;
                    }
                    else
                        if(getClosure() == 0) {
                            Log.e(TAG,"error no closure II");
                            return 0;
                        }


                if(readBonNumbers)
                {
                    Thread thrd = new Thread(new Runnable()
                    {
                        @Override
                        public void run() {
                            // глобален номер на бон
                            if (!getCommandSetFP().fpGetGlobalBon())
                                return;

                            // номер на фискален бон (или текущ, ако е отворен)
                            if (!getCommandSetFP().fpGetBonFisc())
                                return;
                        }
                    });
                    new Handler().postDelayed(thrd, 1000);
                }
            }
            else
            {
                Log.e(TAG,"error res get status");
                return 0;
            }
        }
        else
            /*if(BuildConfig.DEBUG)
                return true;
            else*/
        {
                Log.e(TAG,"error no FP set");
                return 0; // няма настроено ФУ
        }




        timesCheckFP_Wrong = 0; // restart unsusccessful trials counter
        Printer.Companion.setError(null);
        return 1;
    }



    private static int fiscalDevice;
    private static int nonFiscalDevice;
    private static String fiscalDeviceName = "";

    public static String getFiscalDeviceName() { return fiscalDeviceName; }
    public static void setFiscalDeviceName(String fiscalDeviceName) { PrinterFunctions.fiscalDeviceName = fiscalDeviceName; }


    public static boolean isCashDrawerAvail() {return Build.MODEL.equals("E500");}
    //public static void setCashDrawerAvail(boolean cashDrawerAvail) { PrinterFunctions.cashDrawerAvail = cashDrawerAvail; }

    private static boolean cashDrawerAvail;

    private static CommandSet commandSetNonFP;
    private static CommandSetFP commandSetFP;
    public static String cashAvail;
    public static CashData cashData = new CashData();
    public boolean showZReports;

    public boolean isShowZReports(){return showZReports;}
    public void setShowZReports(boolean show){ showZReports = show; }


    public static CommandSetFP getCommandSetFP()
    {
        return commandSetFP;
    }
    public static void setCommandSetFP(CommandSetFP commandSetFP) {PrinterFunctions.commandSetFP = commandSetFP; }

    public static CommandSet getCommandSetNonFP() { return commandSetNonFP; }
    public static void setCommandSetNonFP(CommandSet commandSetNonFP) { PrinterFunctions.commandSetNonFP = commandSetNonFP; }


    public String getCashAvail() { return cashAvail; }
    public static void setCashAvail(String cashAvail) {
        PrinterFunctions.cashAvail = cashAvail;
    }



    public static int getClosure() { return getCommandSetFP() != null ? getCommandSetFP().getClosure() : 0; }



    public static int getBonFisc() { return getCommandSetFP() != null ? getCommandSetFP().getBonFisc() : 0; }
    public static int getBonAll() { return getCommandSetFP() != null ? getCommandSetFP().getBonAll() : 0; }
    public static String getSerial() { return getCommandSetFP() != null ? getCommandSetFP().getSerial() :"TF000001"; }
    public static String getInvNum() { return getCommandSetFP() != null ? getCommandSetFP().getInvNum() : "0000000001"; }
    public static String getVersion() { return getCommandSetFP() != null ? getCommandSetFP().getVersion() : ""; }



    // съдържанието на бона от Last Receipt, например
    // или всички бонове от Справки





    public static boolean fpPrintBon(Bon bon)
    {
        if(bon.isFiscal() )
            return getCommandSetFP().fpPrintFiscal(bon);
        else
            return getCommandSetFP().fpPrintService(bon.getPrintData());
    }

    public void fpMakeReceiptCopy()
    {
            if (getCommandSetFP() != null) {
                getCommandSetFP().fpMakeReceiptCopy(1);
            }
    }





    public  static void fpZReport()
    {
        if(!PrinterFunctions.CheckFP_WithMessage())
            return;

        if (getCommandSetFP() != null)
        {
            if(isFiscalDeviceErpNet()){
                getCommandSetFP().fpZReport();
                return;
            }
            cashData.Clear();
            getCommandSetFP().fpGetCurrentVATRates(null);
            getCommandSetFP().fpGetCurrentZData();
            getCommandSetFP().fpGetClosure();

            cashData.serial = getCommandSetFP().serial;

            getCommandSetFP().fpGetInfo(); // за плащанията

            String dateTime = getCommandSetFP().fpGetDateTime();
            String dateTimeISO = parseDateTimeDotsToDateDashes(dateTime);

            Log.i(TAG,"date ISO:" + dateTimeISO);

            cashData.time = dateTimeISO;
            cashData.fiscalMemory = getCommandSetFP().fm_num;

            //App.getDb().tCash.addToCashData(cashData);

            getCommandSetFP().fpZReport();

            getCommandSetFP().fpGetClosure();
            SharedPreferencesHelper.Companion.getInstance(App.context).setCurrentClosure(getCommandSetFP().getClosure());

        }
    }

    public  static void fpXReport(View v)
    {
        PrinterFunctions.CheckFP_WithMessage();

        if (getCommandSetFP() != null)
        {
            // for test
            /*
            getCommandSetFP().fpGetCurrentZData();
            getCommandSetFP().fpGetCurrentVATRates(null);
            getCommandSetFP().fpGetClosure();
            */

            getCommandSetFP().fpXReport();
        }
    }

    public  static void fpReportByDateShort(View v)
    {
        PrinterFunctions.CheckFP_WithMessage();

        if (getCommandSetFP() != null)
        {
            getCommandSetFP().fpReportByDateShort(filter.getDateFrom().ToStringFp(), filter.getDateTo().ToStringFp());
        }
    }

    public  static void fpReportByDate(View v)
    {
        PrinterFunctions.CheckFP_WithMessage();

        if (getCommandSetFP() != null)
        {
            getCommandSetFP().fpReportByDate(filter.getDateFrom().ToStringFp(), filter.getDateTo().ToStringFp());
        }
    }

    public  static void fpReportByNumber(View v)
    {
        PrinterFunctions.CheckFP_WithMessage();

        if (getCommandSetFP() != null)
        {
            getCommandSetFP().fpReportByNumber(Integer.parseInt(filter.getNumberStart()), Integer.parseInt(filter.getNumberEnd()));
        }
    }

    public  static void fpReportByNumberShort(View v)
    {
        PrinterFunctions.CheckFP_WithMessage();

        if (getCommandSetFP() != null)
        {
            getCommandSetFP().fpReportByNumberShort(Integer.parseInt(filter.getNumberStart()), Integer.parseInt(filter.getNumberEnd()));
        }
    }

    public  static void fpPrintBonFromKLEN(View v)
    {
        PrinterFunctions.CheckFP_WithMessage();

        if (getCommandSetFP() != null)
        {
            getCommandSetFP().fpPrintBonFromKLEN(Integer.parseInt(filter.getNumberStart()), Integer.parseInt(filter.getNumberEnd()));
        }
    }

    public  void fpCashIn()
    {
        double dCashAvail = Double.parseDouble(getCashAvail());
        double dValue = Double.parseDouble(filter.getCashInOut());
        if(dValue==0){
            return;
        }

        if(!PrinterFunctions.CheckFP_WithMessage())
            return;

        if (getCommandSetFP() != null) {
            dCashAvail = getCommandSetFP().fpCashInOut(dValue);
        }
        else
            dCashAvail += dValue;

        if(Math.abs(dCashAvail + 1) > 0.001) {
            //Table_Cash.addNewBonCashInOut(dValue); TODO
            setCashAvail(String.format("%.2f", dCashAvail));

        }
    }

    public  void fpCashOut(View v)
    {
        double dCashAvail = Double.parseDouble(getCashAvail());
        double dValue = -Double.parseDouble(filter.getCashInOut());

        if(dValue==0){
            return;
        }
        if(!PrinterFunctions.CheckFP_WithMessage())
            return;


        if (getCommandSetFP() != null)
            dCashAvail = getCommandSetFP().fpCashInOut(dValue);
        else
            dCashAvail += dValue;

        if(Math.abs(dCashAvail + 1) > 0.001) {
            //Table_Cash.addNewBonCashInOut(dValue); TODO

            setCashAvail(String.format("%.2f", dCashAvail));
        }
    }

    public static double GetSafeCash()
    {
        //App.getDb().tCash.ReadCashAvail(); TODO
        return Double.parseDouble(cashAvail);
    }


    public static String parseDateTimeDotsToDateDashes(String dateTimeDots)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy/HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss");
        SimpleDateFormat format_new = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateFormatted;
        String output = "";


        try {
            dateFormatted = format.parse(dateTimeDots);
            output = format_new.format(dateFormatted);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                dateFormatted = format2.parse(dateTimeDots);
                output = format_new.format(dateFormatted);
            } catch (ParseException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }
        if(!output.equals("") && output.length() > 3) {
            if (output.substring(0, 2).equals("00"))
                output = "20" + output.substring(2);
            return output;
        }
        return String.format("%s", dateTimeDots);
    }
}
