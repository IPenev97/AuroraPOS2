package com.sistechnology.aurorapos2.core.fp_comm.legacy;

import android.content.Context;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.util.Log;

import com.sistechnology.aurorapos2.App;
import com.sistechnology.aurorapos2.core.fp_comm.usb_comm.UsbDeviceHandler;

import java.io.IOException;


/**
 * Created by MARIELA on 2.5.2017 г..
 */

public class DaisyCommunicationUSB extends DaisyCommunicationBase
{
    public UsbDevice usbPrintingDevice;
    public static boolean usbDeviceFoundHasNoPermission;
    public static String usbDeviceFTDINotEP50 = "";
    UsbDeviceConnection mConnection;
    UsbEndpoint epIN = null;
    UsbEndpoint epOUT = null;
    private int FIRST_BYTE = 0; //FTDI packets of 64 bytes which contain 2 status bytes and 62 data bytes.
    public static String TAG = "DaisyCommunicationUSB";

    public static int DAISY_PID = 22336;
    public static int DAISY_VID = 1155;
    public static int ELTRADE_PID = 24857;
    public static int ELTRADE_VID = 1003;

    public static int DATECS_PID = 8963;
    public static int DATECS_FTDI_PID = 24577;
    public static int DATECS_VID = 1659;
    public static int DATECS_USB_VID = 65520;
    public static int FTDI_USB_VID = 1027;








    public static int getPID()
    {
        if(PrinterFunctions.isFiscalDeviceDaisy() || PrinterFunctions.isFiscalDeviceTremol())
            return DAISY_PID;
        else
            if(PrinterFunctions.isFiscalDeviceEltrade())
                return ELTRADE_PID;
            else
                if(PrinterFunctions.isFiscalDeviceDatecsDP25x())
                    return DATECS_PID;

        return 0;
    }

    public static int getVID()
    {
        if(PrinterFunctions.isFiscalDeviceDaisy() || PrinterFunctions.isFiscalDeviceTremol())
            return DAISY_VID;
        else
            if(PrinterFunctions.isFiscalDeviceEltrade())
                return ELTRADE_VID;
            else
                if(PrinterFunctions.isFiscalDeviceDatecsDP25x())
                    return DATECS_VID;

        return 0;
    }

    public static boolean checkUSBDeviceVID_PID(UsbDevice device, boolean fiscal)
    {
        // if Datecs fiscal and non-fiscal are expected to be attached
        // we assume FTDI is the non-fiscal device!

        boolean bFiscalNoFTDI = PrinterFunctions.isNonFiscalDeviceDatecsEP50();

        int vid = device.getVendorId();
        int pid = device.getProductId();

        String name = "";
        String man = "";
        String serial = "";

        try
        {
            name = device.getProductName();
            man = device.getManufacturerName();
            serial = device.getSerialNumber(); // не винаги работи без permission
        }
        catch (Exception ex)
        {
            Log.e(TAG,"checkUSBDeviceVID_PID : " + ex.getMessage().toString());
        }

        Log.i(TAG,"USB device parameters:"
                           + " name=" + (name == null ? "null" : name)
                           + " man=" + (man == null ? "null" : man)
                           + " serial=" + (serial == null ? "null" : serial)
                           + " vid=" + vid
                           + " pid=" + pid
                           + " expected PID=" + getPID()
                           + " expected VID=" + getVID()
        );

        boolean res;

        if(fiscal) {
            res = (vid == getVID()
                   ||
                   // tremol
                   (PrinterFunctions.isFiscalDeviceTremol() &&
                    (pid == getPID()
                     ||
                     name != null && name.toUpperCase().contains("TREMOL")
                            ||
                     man != null && man.toUpperCase().contains("TREMOL")
                     ||
                     serial != null && serial.toUpperCase().startsWith("ZK")))
                   ||
                   // datecs
                   PrinterFunctions.isFiscalDeviceDatecs() &&
                   (!bFiscalNoFTDI && vid == FTDI_USB_VID
                    ||
                    vid == DATECS_USB_VID))
                  ||
                  // datecs
                  (PrinterFunctions.isFiscalDeviceDatecs() &&
                   device.getProductId() == getPID());
            Log.i(TAG,"checkUSBDeviceVID_PID: fiscal res = " + (res ? "true" : "false"));
        }
        else
        {
            res = PrinterFunctions.isNonFiscalDeviceDatecsEP50() && (vid == FTDI_USB_VID || /*vid == DATECS_USB_VID ||*/ pid == DATECS_FTDI_PID) &&
                  (usbDeviceFTDINotEP50.equals("") || !usbDeviceFTDINotEP50.equals(name));
            Log.i(TAG,"checkUSBDeviceVID_PID: non fiscal res = " + (res ? "true" : "false"));
        }

        Log.i(TAG,"USB device is " + (res ? "accepted" : "denied"));


        return res;
    }

    public DaisyCommunicationUSB()
    {
        Log.i("FPComm", "DaisyCommunicationUSB");

        readBuffer = new byte[1024];
    }


    @Override
    public void init(boolean fiscal) throws IOException
    {
        super.init(fiscal);
        strTag = strPrinterType + ":CommunicationUSB";
        strPrinterType += ":USB";


        Log.i(TAG,"DaisyCommunicationUSB::init");

        clear();

        notConnected = true;
        boolean domore = false;

        usbDeviceFoundHasNoPermission = false;

        usbPrintingDevice = UsbDeviceHandler.Companion.getUsbFiscalDevice();

        if(usbPrintingDevice==null)
            return;


        class RunnableArg implements Runnable
        {
            boolean fiscal;

            RunnableArg(boolean f) {
                fiscal = f;
            }

            public void run() {
                connectToPrinterUSB(fiscal);
            }
        }

        Runnable mConnect = new RunnableArg(fiscal);
        Log.i("FPComm", "connectToPrinterUSB mConnect.run()");
        mConnect.run();
    }

    public void connectToPrinterUSB(boolean fiscal)
    {
        Log.i("FPComm", "connectToPrinterUSB start");
        UsbManager manager = (UsbManager) App.context.getSystemService(Context.USB_SERVICE);

        epIN = null;
        epOUT = null;

        if(manager != null && usbPrintingDevice != null) {

            Log.i("FPComm", "connectToPrinterUSB openDevice:");
            mConnection = manager.openDevice(usbPrintingDevice);

            if (mConnection == null) {
                Log.i("FPComm", "connectToPrinterUSB mConnection == null");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }

                Log.i("FPComm", "connectToPrinterUSB openDevice II");
                mConnection = manager.openDevice(usbPrintingDevice);
                if (mConnection == null) {
                    Log.i("FPComm", "connectToPrinterUSB mConnection == null II");
                    return;
                }
            }
        }

        Log.i("FPComm", "connectToPrinterUSB getInterfaceCount:");
        for(int i = 0; i < usbPrintingDevice.getInterfaceCount(); i++ )
        {
            Log.i("USB", "connectToPrinterUSB getInterfaceCount: start");
            if(usbPrintingDevice.getInterface(i).getEndpointCount() >= 2)
            {
                if (usbPrintingDevice.getInterface(i).getEndpoint(i).getType() == UsbConstants.USB_ENDPOINT_XFER_BULK)
                {
                    for (int j = 0; j < usbPrintingDevice.getInterface(i).getEndpointCount(); j++)
                        if (usbPrintingDevice.getInterface(i)
                                             .getEndpoint(j)
                                             .getDirection() == UsbConstants.USB_DIR_IN)
                            epIN = usbPrintingDevice.getInterface(i).getEndpoint(j);
                        else
                            epOUT = usbPrintingDevice.getInterface(i).getEndpoint(j);

                    UsbInterface iface = usbPrintingDevice.getInterface(i);

                    if(iface.getInterfaceClass() == UsbConstants.USB_CLASS_CDC_DATA)
                    {
                        int x= 0;
                    }

                    if (!mConnection.claimInterface(iface, true)) {
                        //updateResult("connect error");
                        return;
                    }
                    else
                        break;

                }
            }
        }

        Log.i("FPComm", "connectToPrinterUSB getInterfaceCount: controlTransfer");

        if(fiscal) {
            if (PrinterFunctions.isFiscalDeviceTremol())
                usbCommandsToTremol();
            else
                if (PrinterFunctions.isFiscalDeviceDatecsDP150() || (Build.VERSION.SDK_INT > 21 && usbPrintingDevice
                        .getProductName()
                        .equals("FT232R USB UART")))
                    usbCommandsToDatecs();
                else
                    usbCommandsToEltrade();
        }
        else
            if(PrinterFunctions.isNonFiscalDeviceDatecsEP50())
                usbCommandsToDatecsNonFiscal();

        notConnected = false;
        Log.i("FPComm", "connectToPrinterUSB getInterfaceCount: connected!");

        if(PrinterFunctions.isNonFiscalDeviceDatecsEP50())
        {
            String usbText = usbPrintingDevice.toString().toUpperCase();
            if(!fiscal && (usbText.contains("FTDI") || usbText.contains("DATECS")))
            {
                /*
                byte[] buf = new byte[]{27, 74, (byte)1};
                synchronized(this) {
                    if(!requestPlain(buf, buf.length,100))
                    {
                        try
                        {
                            usbDeviceFTDINotEP50 = usbPrintingDevice.getProductName();
                            LogToMail.AddToLog("usbDeviceFTDINotEP50 : " + usbPrintingDevice.getProductName());
                            notConnected = true;
                        }
                        catch (Exception ex)
                        {
                            LogToMail.AddToLog("check nonfiscal USBDevice : " + ex.getMessage().toString());
                        }
                    }
                }
                */
            }

        }
    }

    private void usbCommandsToEltrade() {
        mConnection.controlTransfer(0x40, 0, 0, 0, null, 0, 0);// reset
        // mConnection.controlTransfer(0×40, 0, 1, 0, null, 0, 0);// clear Rx
        mConnection.controlTransfer(0x40, 0, 2, 0, null, 0, 0);// clear Tx
        mConnection.controlTransfer(0x40, 0x02, 0x0000, 0, null, 0, 0);// flow control none
        mConnection.controlTransfer(0x40, 0x03, 0x001A, 0, null, 0, 0);// baudrate 115200
        mConnection.controlTransfer(0x40, 0x04, 0x0008, 0, null, 0, 0);// data bit 8, parity none, stop bit 1, tx off
    }

    public void usbCommandsToDatecs()
    {


        FIRST_BYTE = 2; //FTDI USB packets 2 status bytes and 62 data bytes, so first byte in packet is 2 - from Datecs SDK!

        mConnection.controlTransfer(0x40, 0, 0, 0, null, 0, 0); // Reset
        mConnection.controlTransfer(0x40, 0, 1, 0, null, 0, 0); // Clear Rx
        mConnection.controlTransfer(0x40, 0, 2, 0, null, 0, 0); // Clear Tx
        // usbConn.controlTransfer(0x40, 0x03, 0x4138, 0, null, 0, 0); // Set baud rate to 9600
        mConnection.controlTransfer(0x40, 0x03, 0x001A, 0, null, 0, 0); // Set baud rate to 115200

    }

    // typedef enum {
    // ftdi_8U232AM_48MHz_b300 = 0x2710,
    //         ftdi_8U232AM_48MHz_b600 = 0x1388,
    //         ftdi_8U232AM_48MHz_b1200 = 0x09c4,
    //         ftdi_8U232AM_48MHz_b2400 = 0x04e2,
    //         ftdi_8U232AM_48MHz_b4800 = 0x0271,
    //         ftdi_8U232AM_48MHz_b9600 = 0x4138,
    //         ftdi_8U232AM_48MHz_b19200 = 0x809c,
    //         ftdi_8U232AM_48MHz_b38400 = 0xc04e,
    //         ftdi_8U232AM_48MHz_b57600 = 0x0034,
    //         ftdi_8U232AM_48MHz_b115200 = 0x001a,
    //         ftdi_8U232AM_48MHz_b230400 = 0x000d,
    //         ftdi_8U232AM_48MHz_b460800 = 0x4006,
    //         ftdi_8U232AM_48MHz_b921600 = 0x8003,
    // } FTDI_8U232AM_48MHz_baudrate_t;

    public void usbCommandsToDatecsNonFiscal()
    {


        mConnection.controlTransfer(0x40, 0, 0, 0, null, 0, 0); // Reset
        mConnection.controlTransfer(0x40, 0, 1, 0, null, 0, 0); // Clear Rx
        mConnection.controlTransfer(0x40, 0, 2, 0, null, 0, 0); // Clear Tx
        // usbConn.controlTransfer(0x40, 0x03, 0x4138, 0, null, 0, 0); // Set baud rate to 9600
        mConnection.controlTransfer(0x40, 0x03, 0x0034, 0, null, 0, 0); // Set baud rate to 57600!!!

    }

    @Override
    public boolean send()
    {
        String output = "";
        for (int ii=0; ii < lenToSend; ii++) {
            output += String.format("%02x ", bytesToSend[ii]);

        }

       Log.i(TAG, "FPComm send:" + output);


        if(mConnection == null || epIN == null || epOUT == null)
            return false;

        bufferReady = false;

        int res = mConnection.bulkTransfer(epOUT, bytesToSend, lenToSend, 500);

        return (res > 0);
    }


    @Override
    public void clear()
    {
        Log.i("FPComm", "DaisyCommunicationUSB::clear");

        try {

            if(mConnection != null)
            {
                Log.i("FPComm", "DaisyCommunicationUSB::clear: mConnection.close()");
                mConnection.close();
            }


        } catch (Exception ex) {
        } catch (Throwable e) {
        }

        notConnected = true;

    }

    public waitforanswerBase createReadAnswer()
    {
        return new waitforanswer();
    }

    public class waitforanswer extends waitforanswerBase implements Runnable
    {
        waitforanswer()
        {
        }
        @Override
        public void run()
        {
            byte[] buffer = new byte[4096];
            readBufferPosition = 0;
            int trialsAfterReceived = 7;
            int i;

            if(mConnection == null)
                return;

            if (Thread.interrupted()) {
                return;
            }

            try {

                int bytesAvailable = 0;
                boolean received = false;

                do {
                    if (Thread.interrupted()) {
                        return;
                    }
                    // read
                    bytesAvailable = mConnection.bulkTransfer(epIN, buffer, 4096, 500);

                    if (bytesAvailable > 0) {

                        received = true;
                        trialsAfterReceived = 5;

                        byte[] packetBytes; // = new byte[bytesAvailable];
                        packetBytes = buffer;

                        if (bytesAvailable == 1)
                            if (packetBytes[FIRST_BYTE] == CommandSetFP.NAK) {
                                Log.i("DAISY USB", "NAK");
                            }
                            else
                                if (packetBytes[FIRST_BYTE] == CommandSetFP.SYN) {
                                    {
                                        Log.i("DAISY USB", "SYN");
                                        continue;
                                    }
                                }

                        for (i = 0; i < bytesAvailable; i++)
                        {
                            if(i % 64 == 0)
                            {
                                i += FIRST_BYTE;
                                if(i >= bytesAvailable)
                                    break;
                            }

                            if (packetBytes[i] != CommandSetFP.SYN)
                                break;
                            else
                                Log.i("DAISY USB", "SYN");
                        }

                        for (; i < bytesAvailable; i++)
                        {
                            if(i % 64 == 0)
                            {
                                i += FIRST_BYTE;
                                if(i >= bytesAvailable)
                                    break;
                            }

                            byte b = packetBytes[i];
                            readBuffer[readBufferPosition++] = b;

                            if (b == CommandSetFP.ETX || isPing() && bytesAvailable == 1)
                            {
                                String output = "";
                                for (int ii=0; ii < bytesAvailable; ii++) {
                                    output += String.format("%02x ", readBuffer[ii]);

                                }
                                Log.i("DAISY USB", output);
                                Log.i(TAG,"answer:" + output);

                                PrinterFunctions.getCommandSetFP().copyAnswerNoSYN(readBuffer);
                                readBufferPosition = 0;

                                if (PrinterFunctions.getCommandSetFP().checkFormat()) {
                                    bufferReady = true;
                                    break;
                                }
                            }
                        }
                    }
                    else
                    {
                        if(received)
                        {
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.i("DAISY USB", "waitforanswer: bytesAvailable 0");
                            trialsAfterReceived--;
                        }
                    }
                } while (!bufferReady && (bytesAvailable > 0 || !received || trialsAfterReceived > 0));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Log.i("DAISY USB", "waitforanswer: end");
        }

    }

    // TREMOL
    private static final byte[] byteArrayToControlTransfer = {0, -62, 1, 0, 0, 0, 8};

    private int controlTransferArray(int i, int i2, byte[] bArr) {
        return mConnection.controlTransfer(33, i, i2, 0, bArr, bArr != null ? bArr.length : 0, 5000);
    }

    /* renamed from: e */
    private void usbCommandsToTremol() {

        controlTransferArray(32, 0, byteArrayToControlTransfer);
        controlTransferArray(34, 3, null);
        controlTransferBaudrate(115200);
        controlTransferDataBits(8);
        controlTransferStopBits(1);
        controlTransferParity(0);
    }

    /* renamed from: f */
    private byte[] controlTransferRead7Bytes() {
        byte[] bArr = new byte[7];
        mConnection.controlTransfer(161, 33, 0, 0, bArr, bArr.length, 5000);
        return bArr;
    }

    public void controlTransferBaudrate(int i) {
        byte[] f = controlTransferRead7Bytes();
        f[0] = (byte) (i & 255);
        f[1] = (byte) ((i >> 8) & 255);
        f[2] = (byte) ((i >> 16) & 255);
        f[3] = (byte) ((i >> 24) & 255);
        controlTransferArray(32, 0, f);
    }
    public void controlTransferDataBits(int i) {
        byte[] f = controlTransferRead7Bytes();
        switch (i) {
            case 5:
                f[6] = 5;
                break;
            case 6:
                f[6] = 6;
                break;
            case 7:
                f[6] = 7;
                break;
            case 8:
                f[6] = 8;
                break;
            default:
                return;
        }
        controlTransferArray(32, 0, f);
    }

    public void controlTransferStopBits(int i) {
        byte[] f = controlTransferRead7Bytes();
        switch (i) {
            case 1:
                f[4] = 0;
                break;
            case 2:
                f[4] = 2;
                break;
            case 3:
                f[4] = 1;
                break;
            default:
                return;
        }
        controlTransferArray(32, 0, f);
    }

    public void controlTransferParity(int i) {
        byte[] f = controlTransferRead7Bytes();
        switch (i) {
            case 0:
                f[5] = 0;
                break;
            case 1:
                f[5] = 1;
                break;
            case 2:
                f[5] = 2;
                break;
            case 3:
                f[5] = 3;
                break;
            case 4:
                f[5] = 4;
                break;
            default:
                return;
        }
        controlTransferArray(32, 0, f);
    }

}






