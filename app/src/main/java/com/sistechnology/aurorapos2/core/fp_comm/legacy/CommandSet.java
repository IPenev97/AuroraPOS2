package com.sistechnology.aurorapos2.core.fp_comm.legacy;

import android.util.Log;
import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.BonPrintData;


import java.io.IOException;
import java.util.List;

public class CommandSet
{
    public boolean usbDeviceFoundHasNoPermission;
    protected boolean fiscal = false;
    public String TAG = "CommandSet";

    public static byte ESC = 0x1B;
    public static byte GS = 0x1D;

    public static final int FP_ERROR_NO_COMM = 1000;

    public final static byte FP_FISCAL_RECEIPT = '1';
    public final static byte FP_SERVICE_RECEIPT = '2';
    public final static byte FP_CASH_IN_RECEIPT = '3';
    public final static byte FP_CASH_OUT_RECEIPT = '4';
    public final static byte FP_INVOICE_RECEIPT = '5';
    public final static byte FP_RETURN_RECEIPT = '6';
    public final static byte FP_KI_RECEIPT = '7';

    // BT errors
    public static final int FP_ERROR_BT_NAME_NOT_SETUP = 1001;
    public static final int FP_ERROR_BT_NO_PAIRED_DEV = 1002; // No appropriate paired devices
    public static final int FP_ERROR_BT_DISABLED = 1003; // Bluetooth is disabled.
    public static final int FP_ERROR_BT_NOT_SUPPORTED = 1004; // Bluetooth not supported!!

    public int COMM_CREATED = 2;
    public int COMM_EXISTS = 1;
    public int COMM_ERROR = 0;

    public static int fpError = 0;
    public String lastError = "";
    public String serial = "";

    public String getSerial() { return serial; }
    public void setSerial(String serial) { this.serial = serial; }

    public static String errorNoPrn = "No connected printer.";
    public DaisyCommunicationBase comm;


    public boolean noCommSetError()
    {
        if(comm == null)
        {
            fpError = FP_ERROR_NO_COMM;
            return true;
        }
        else
            return false;
    }
    public static int getFpError() { return fpError; }
    public boolean checkLastErrorPrintAgain() {return true;}



    public String getLastError() { return lastError; }
    public void setLastError(String lastError)
    {
        this.lastError = lastError;
        if(lastError != null && !lastError.equals(""))
            Log.e(TAG, "lastError = " + lastError);
    }
    public void addLastError(String lastError)
    {
        if(lastError == null || lastError.equals(""))
            return;

        if(!this.lastError.equals(""))
            this.lastError += "\n";
        this.lastError += lastError;
        Log.e(TAG, "lastError += " + lastError);
    }
    public String getLastErrorText()
    {
        return getLastErrorText(fpError);
    }
    public String getLastErrorText(int err)
    {
        return "";
    }

    public StatusFP statusFP;
    public StatusFP getStatus() { return statusFP; }

    public void initStatusFP(){ statusFP = new StatusFP(); }

    public CommandSet() { initStatusFP(); }

    public void fpStopConnection()
    {
        if(comm != null)
            comm.clear();
    }
    public int initComm()
    {
        usbDeviceFoundHasNoPermission = false;
        if(comm == null || comm.notConnected) {
            try {
                if(comm != null) {
                    comm.clear();
                    comm = null;
                }

                //comm = DaisyCommunicationUSB.getInstance();
                comm = new DaisyCommunicationUSB();
                comm.init(fiscal);

                if(comm.notConnected) {
                    //comm = DaisyCommunication.getInstance();
//                    comm = new DaisyCommunication();
//                    comm.init(fiscal);
                }
                if(comm.notConnected) {
                    Log.e(TAG,"ERROR: initComm");
                    return COMM_ERROR;
                }

                return COMM_CREATED; // created
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.i("PRN_INIT_COMM:", "comm.init failed");

                if (comm != null) {
                    comm.clear();
                }

                //comm.pairDevice();

                Log.e(TAG,"ERROR: initComm");
                return COMM_ERROR;
            }
        }

        return COMM_EXISTS;
    }

    public boolean checkComm()
    {
        return checkComm(false);
    }

    public boolean checkComm(boolean toast)
    {
        int commRes = initComm();
        if(commRes == COMM_ERROR)
        {
            if(comm!=null) {
                comm.clear();
                comm = null;
            }

            Log.i("CHECK_COMM:", errorNoPrn);

            if(toast)

            return false;
        }

        return true;
    }


    public boolean analyzeAnswer()
    {
        return analyzeAnswer(null);
    }

    public boolean analyzeAnswer(String charsetName) {
        return true;
    }

    public void request(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    public boolean fpPrintService(BonPrintData printData)
    {
        // cash bon closure
        Log.i(TAG,"fpPrintService non fiscal");
        if(!checkComm())
        {
            Log.e(TAG, "fpPrintService: checkComm error");
            return false;
        }

        class RunnableArg implements Runnable
        {
            // BonPrintData printData;
            StringBuffer textBuffer;

            RunnableArg(StringBuffer textBuffer) {
                this.textBuffer = textBuffer;
            }

            public void run() {

//                List<String> strList = printData.toStringListReceipt();
//                StringBuffer textBuffer = new StringBuffer();
//
//
//
//                int i;
//                for(i = 0; i < printData.listToPrintHeader.size(); i++)
//                {
//                    textBuffer.append(printData.listToPrintHeader.get(i));
//                    textBuffer.append('\n');
//                }
//                for(i = 0; i < strList.size(); i++)
//                {
//                    textBuffer.append(strList.get(i));
//                    textBuffer.append('\n');
//                }
//
//
//                textBuffer.append(printData.textToPrintFooter);

                comm.requestPlain(this.textBuffer.toString(), 0);

                try {
                    feedPaper(110);
                }
                catch (IOException e){}

                /*
                textBuffer.append("{reset}{center}{w}{h}RECEIPT");
                textBuffer.append("{br}");
                textBuffer.append("{br}");
                textBuffer.append("{reset}1. {b}Ябълки{br}");
                textBuffer.append("{reset}{right}{h}$0.50 A{br}");
                textBuffer.append("{reset}2. {u}Second item{br}");
                textBuffer.append("{reset}{right}{h}$1.00 B{br}");
                textBuffer.append("{reset}3. {i}Third item{br}");
                textBuffer.append("{reset}{right}{h}$1.50 C{br}");
                textBuffer.append("{br}");
                textBuffer.append("{reset}{right}{w}{h}TOTAL: {/w}$3.00  {br}");
                textBuffer.append("{br}");
                textBuffer.append("{reset}{center}{s}Thank You!{br}");

                try {
                    reset();
                    printTaggedText(textBuffer.toString());
                    feedPaper(110);
//                flush();
                }
                catch (IOException e){}

                 */
            }
        }


        List<String> strList = printData.toStringListReceipt();
        StringBuffer textBuffer = new StringBuffer();



        int i;
        for(i = 0; i < printData.listToPrintHeader.size(); i++)
        {
            textBuffer.append(printData.listToPrintHeader.get(i));
            textBuffer.append('\n');
        }
        for(i = 0; i < strList.size(); i++)
        {
            textBuffer.append(strList.get(i));
            textBuffer.append('\n');
        }


        textBuffer.append(printData.textToPrintFooter);

        request(new RunnableArg(textBuffer));

        return true; // printing started
    }


    public boolean fpPrintBarcode(String barcode)
    {
        boolean res = false;
        int type = 67; // EAN13

        byte[] data = new byte[barcode.length()];
        for(int lenToSend = 0; lenToSend < barcode.length(); lenToSend++)
            data[lenToSend] = (byte)(barcode.charAt(lenToSend));


        if (data == null) {
            throw new NullPointerException("The data is null.");
        } else {
            byte[] buf = new byte[21 + data.length];
            int len = 0;
            //int offs = offs + 1;
            buf[len] = ESC;
            buf[len++] = 0x61; // 'a'
            buf[len++] = 0; // left aligned
            buf[len++] = GS;
            buf[len++] = 0x77; // 'w'
            buf[len++] = (byte)3; // scale
            buf[len++] = GS;
            buf[len++] = 0x68; // 'h'
            buf[len++] = (byte)162; // height
            buf[len++] = GS;
            buf[len++] = 0x48; // 'H'
            buf[len++] = (byte)2; // HRI pos - below
            buf[len++] = GS; // GS
            buf[len++] = 0x66; // 'f'
            buf[len++] = (byte)0; // HRI font
            switch(type) {
                case 65:
                    if (data.length != 11) {
                        throw new IllegalArgumentException("The length of UPCA barcode data must be 11 symbols");
                    }
                    break;
                case 66:
                    if (data.length != 11) {
                        throw new IllegalArgumentException("The length of UPCE barcode data must be 11 symbols");
                    }
                    break;
                case 67:
                    if (data.length != 12) {
                        throw new IllegalArgumentException("The length of EAN13 barcode data must be 12 symbols");
                    }
                    break;
                case 68:
                    if (data.length != 7) {
                        throw new IllegalArgumentException("The length of EAN8 barcode data must be 7 symbols");
                    }
                    break;
                case 69:
                case 70:
                case 71:
                case 72:
                case 73:
                case 75:
                case 76:
                    if (data.length < 1 || data.length > 255) {
                        throw new IllegalArgumentException("The length of barcode data must be between 1 and 255 symbols");
                    }
                    break;
                case 74:
                    if (data.length >= 1 && data.length <= 1000) {
                        break;
                    }

                    throw new IllegalArgumentException("The length of PDF417 barcode data must be between 1 and 1000 symbols");
                default:
                    throw new IllegalArgumentException("Invalid barcode type");
            }

            buf[len++] = 29;
            buf[len++] = 107;
            buf[len++] = (byte)type;
            if (type == 73 && data[0] != 123) {
                buf[len++] = (byte)(data.length + 2);
                buf[len++] = 123;
                buf[len++] = 66;
            } else if (type == 74) {
                buf[len++] = 0;
                buf[len++] = (byte)(data.length & 255);
                buf[len++] = (byte)(data.length >> 8 & 255);
            } else {
                buf[len++] = (byte)data.length;
            }

            for(int i = 0; i < data.length; ++i) {
                buf[len++] = data[i];
            }

            synchronized(this) {
                res = comm.requestPlain(buf, len,0);
            }
        }

        return res;
    }

    public boolean fpGetStatus()
    {
        return true;
    }

    public boolean fpGetDiagInfo() {
        return fpGetDiagInfo(false);
    }

    public boolean fpGetDiagInfo(boolean shooting) {
        return true;
    }

    public void reset() throws IOException {
        //                      1B  @   1b   2  1b   i  0  1b   !  0  GS   L  0  0
        byte[] buf = new byte[]{27, 64, 27, 50, 27, 73, 0, 27, 33, 0, 29, 76, 0, 0};
        synchronized(this) {
            comm.requestPlain(buf, buf.length,0);
        }
    }
    private void printTaggedText(byte[] b) throws IOException {
        int BREAK = "br".hashCode();
        int SMALL = "s".hashCode();
        int BOLD = "b".hashCode();
        int HIGH = "h".hashCode();
        int WIDE = "w".hashCode();
        int UNDERLINE = "u".hashCode();
        int ITALIC = "i".hashCode();
        int RESET = "reset".hashCode();
        int LEFT = "left".hashCode();
        int CENTER = "center".hashCode();
        int RIGHT = "right".hashCode();
        if (b == null) {
            throw new NullPointerException("The b is null");
        } else {
            int len = b.length;
            byte[] tbuf = new byte[9 + len];
            int toffs = 0;
            byte mode = 0;
            int pos = 0;


            tbuf[toffs++] = 27;
            tbuf[toffs++] = 0x74;
            tbuf[toffs++] = 5;

            tbuf[toffs++] = 27;
            tbuf[toffs++] = 33;
            tbuf[toffs++] = mode;
            tbuf[toffs++] = 27;
            tbuf[toffs++] = 73;
            tbuf[toffs++] = 0;

            for(int i = 0; i < len; ++i) {
                byte value = b[i];
                tbuf[toffs++] = value;
                if (value == 123) {
                    pos = toffs;
                } else if (value == 125 && pos >= 1 && toffs - 1 - 6 <= pos) {
                    int index;
                    boolean set;
                    if (tbuf[pos] == 47) {
                        set = false;
                        index = pos + 1;
                    } else {
                        set = true;
                        index = pos;
                    }

                    int hash = 0;
                    int hashlen = toffs - 1 - index;

                    for(int j = 0; j < hashlen; ++j) {
                        int c = tbuf[index + j] & 255;
                        if (c >= 65 && c <= 90) {
                            c += 32;
                        }

                        hash = 31 * hash + c;
                    }

                    if (hash == BREAK) {
                        toffs = pos - 1;
                        tbuf[toffs++] = 10;
                    } else if (hash == SMALL) {
                        if (set) {
                            mode = (byte)(mode | 1);
                        } else {
                            mode &= -2;
                        }

                        toffs = pos - 1;
                        tbuf[toffs++] = 27;
                        tbuf[toffs++] = 33;
                        tbuf[toffs++] = mode;
                    } else if (hash == BOLD) {
                        if (set) {
                            mode = (byte)(mode | 8);
                        } else {
                            mode &= -9;
                        }

                        toffs = pos - 1;
                        tbuf[toffs++] = 27;
                        tbuf[toffs++] = 33;
                        tbuf[toffs++] = mode;
                    } else if (hash == HIGH) {
                        if (set) {
                            mode = (byte)(mode | 16);
                        } else {
                            mode &= -17;
                        }

                        toffs = pos - 1;
                        tbuf[toffs++] = 27;
                        tbuf[toffs++] = 33;
                        tbuf[toffs++] = mode;
                    } else if (hash == WIDE) {
                        if (set) {
                            mode = (byte)(mode | 32);
                        } else {
                            mode &= -33;
                        }

                        toffs = pos - 1;
                        tbuf[toffs++] = 27;
                        tbuf[toffs++] = 33;
                        tbuf[toffs++] = mode;
                    } else if (hash == UNDERLINE) {
                        if (set) {
                            mode = (byte)(mode | 128);
                        } else {
                            mode = (byte)(mode & -129);
                        }

                        toffs = pos - 1;
                        tbuf[toffs++] = 27;
                        tbuf[toffs++] = 33;
                        tbuf[toffs++] = mode;
                    } else if (hash == ITALIC) {
                        toffs = pos - 1;
                        tbuf[toffs++] = 27;
                        tbuf[toffs++] = 73;
                        tbuf[toffs++] = (byte)(set ? 1 : 0);
                    } else if (hash == RESET) {
                        mode = 0;
                        toffs = pos - 1;
                        tbuf[toffs++] = 27;
                        tbuf[toffs++] = 33;
                        tbuf[toffs++] = mode;
                        tbuf[toffs++] = 27;
                        tbuf[toffs++] = 73;
                        tbuf[toffs++] = 0;
                    } else if (hash == LEFT) {
                        toffs = pos - 1;
                        tbuf[toffs++] = 27;
                        tbuf[toffs++] = 97;
                        tbuf[toffs++] = 0;
                    } else if (hash == CENTER) {
                        toffs = pos - 1;
                        tbuf[toffs++] = 27;
                        tbuf[toffs++] = 97;
                        tbuf[toffs++] = 1;
                    } else if (hash == RIGHT) {
                        toffs = pos - 1;
                        tbuf[toffs++] = 27;
                        tbuf[toffs++] = 97;
                        tbuf[toffs++] = 2;
                    }
                }
            }

            synchronized(this) {
                byte[] tmp = new byte[toffs];
                System.arraycopy(tbuf, 0, tmp, 0, tmp.length);
                comm.requestPlain(tmp, tmp.length,0);
            }
        }
    }
    public void printTaggedText(String s) throws IOException {
        if (s == null) {
            throw new NullPointerException("The s is null");
        } else {
            this.printTaggedText(s.getBytes());
        }
    }
    public void printTaggedText(String s, String encoding) throws IOException {
        if (s == null) {
            throw new NullPointerException("The s is null");
        } else {
            this.printTaggedText(s.getBytes(encoding));
        }
    }

    public void feedPaper(int lines) throws IOException {
        if (lines >= 0 && lines <= 255) {
            byte[] buf = new byte[]{27, 74, (byte)lines};
            synchronized(this) {
                comm.requestPlain(buf, buf.length,0);
            }
        } else {
            throw new IllegalArgumentException("The lines is out of range");
        }
    }

}
