package com.sistechnology.aurorapos2.core.fp_comm.legacy;

import android.util.Log;



import java.io.IOException;


/**
 * Created by MARIELA on 2.5.2017 г..
 */

public class DaisyCommunicationBase
{
    public byte[] bytesToSend;
    public int lenToSend;

    public String strPrinterType = "Daisy";
    public String strTag = "FPCommunication";
    public String TAG = "PrinterCommunicationBase";

    public boolean notConnected;
    public byte[] readBuffer;
    public int readBufferPosition;
    public boolean bufferReady;

    public DaisyCommunicationBase(){
        Log.i("FPComm", "DaisyCommunicationBase");

        registerReceiver();
    }

    public boolean isPing() { return bytesToSend.length == 1; }

    public void setPrinterType(boolean fiscal)
    {
        if(fiscal)
        {
            if(PrinterFunctions.isFiscalDeviceDaisy())
                strPrinterType = "Daisy";
            else
                if(PrinterFunctions.isFiscalDeviceEltrade())
                    strPrinterType = "Eltrade";
                else
                    if(PrinterFunctions.isFiscalDeviceTremol())
                        strPrinterType = "Tremol";
                    else
                        if(PrinterFunctions.isFiscalDeviceDatecsDP25x())
                            strPrinterType = "Datecs DP 25x";
                        else
                            if(PrinterFunctions.isFiscalDeviceDatecsDP150())
                                strPrinterType = "Datecs DP 150";
        }
        else
            if(PrinterFunctions.isNonFiscalDeviceDatecsEP50())
                strPrinterType = "Datecs EP50";
    }

    public synchronized void init(boolean fiscal) throws IOException
    {
        setPrinterType(fiscal);
    }

    public synchronized void init(boolean fiscal, CommandSet commandSet) throws IOException
    {
        setPrinterType(fiscal);
    }

    public boolean send()
    {
        // override
        return true;
    }

    public class waitforanswerBase implements Runnable
    {
        @Override
        public void run() {

        }
    }

    public waitforanswerBase createReadAnswer()
    {
        // override
        return null;
    }

    public boolean request(byte cmd, String data)
    {
        if(PrinterFunctions.getCommandSetFP() != null)
            CommandSetFP.fpError = 0;

        return request(cmd, data, 5000);
    }
    public boolean request(byte cmd, String data, int timeout)
    {
        // 1. Set last error to ""
        // 2. PutDelimiter short log
        // 3. Name the short log
        // 4. Pack command and data
        // 5. Send bytes
        // 6. Start a thread to wait for answer for max 5 s
        // 7. Send the short log
        // 8. Return true if bufferReady or false (bufferReady is set in the waiting thread

        Log.i(TAG, String.format("*** request: cmd = 0x%x, data = %s", cmd, data == null ? "" : data));

        bytesToSend = PrinterFunctions.getCommandSetFP().packCommand(cmd, data);

        if(send()) // sending bytesToSend
        {
            waitforanswerBase readAnswer = createReadAnswer();

            Thread t = new Thread(readAnswer, "Thread" + strPrinterType + "WaitForAnswer");

            t.start(); // spawn thread

            try {
                t.join(timeout);
                t.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
            notConnected = true;



        if(bufferReady)
            Log.i(strTag, "bufferReady");
        else
            Log.i(strTag, "buffer not Ready");

        return bufferReady;
    }

    public boolean requestPlain(byte[] data, int len, int timeout)
    {
        bytesToSend = data;
        lenToSend = len;

        return requestPlain(timeout);
    }

    public boolean requestPlain(String data, int timeout) {
        // 1. Set last error to ""
        // 2. PutDelimiter short log
        // 3. Name the short log
        // 4. Pack command and data
        // 5. Send bytes
        // 6. Start a thread to wait for answer for max 5 s
        // 7. Send the short log
        // 8. Return true if bufferReady or false (bufferReady is set in the waiting thread

        if (data.length() <= 0)
            return true;

        Log.i(TAG, String.format("requestEsc: data = %s", data == null ? "" : data));


        bytesToSend = new byte[6 + data.length()];
        int len = 0;
        bytesToSend[len++] = 0x1b;
        bytesToSend[len++] = 0x74;
        bytesToSend[len++] = 0x5;

        for(int i = 0; i < data.length(); i++)
        {
            if(data.charAt(i) == '\n')
                bytesToSend[len++] = (byte)0x0A;
            else
                bytesToSend[len++] = getCyrByte856(data.charAt(i));
        }

        bytesToSend[len++] = 0x1b;
        bytesToSend[len++] = 'J';
        bytesToSend[len++] = 0x5;

        this.lenToSend = len;

        return requestPlain(timeout);
    }

    public boolean requestPlain(int timeout)
    {
        if(send()) // sending bytesToSend
        {
            if(timeout > 0) {
                waitforanswerBase readAnswer = createReadAnswer();

                Thread t = new Thread(readAnswer, "Thread" + strPrinterType + "WaitForAnswer");

                t.start(); // spawn thread

                try {
                    t.join(timeout);
                    t.interrupt();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else
                bufferReady = true;
        }
        else
            notConnected = true;

        return bufferReady;
    }
    public static byte getCyrByte856(char charTmp)
    {
        byte byteCyr;

        if(charTmp>='а' && charTmp<='ъ')
            byteCyr=(byte)(0xA0 + (int)charTmp - (int)'а');
        else if(charTmp=='ь')
            byteCyr=(byte)0xBC;
        else if(charTmp=='ю')
            byteCyr=(byte)0xBE;
        else if(charTmp=='я')
            byteCyr=(byte)0xBF;
        else if(charTmp>='А' && charTmp<='Ъ')
            byteCyr=(byte)(0x80 + (int)charTmp - (int)'А');
        else if(charTmp=='Ю')
            byteCyr=(byte)0x9E;
        else if(charTmp=='Я')
            byteCyr=(byte)0x9F;
        else if(charTmp=='№')
            byteCyr = (byte)0xD5;
        else
            byteCyr = (byte)charTmp;

        return byteCyr;
    }
    public static byte getCyrByte(char charTmp)
    {
        byte byteCyr;
        if(charTmp>='а' && charTmp<='я')
        {
            byteCyr=(byte)(0xE0 + (int)charTmp - (int)'а');
        }
        else
        if(charTmp>='А' && charTmp<='Я')
        {
            byteCyr=(byte)(0xC0 + (int)charTmp - (int)'А');
        }
        else
        if(charTmp=='№')
            byteCyr = (byte)0xB9;
        else
            byteCyr = (byte)charTmp;

        return byteCyr;
    }

    /*
 * after opening a connection to printer device,
 * we have to listen and check if a data were sent to be printed.
 */
    public void clear()
    {
    }

    public void unregisterReceiver() {
    }

    public void registerReceiver() {
    }

}






