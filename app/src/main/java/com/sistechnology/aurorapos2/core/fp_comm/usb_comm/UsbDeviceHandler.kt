package com.sistechnology.aurorapos2.core.fp_comm.usb_comm

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log
import com.sistechnology.aurorapos2.App
import com.sistechnology.aurorapos2.core.fp_comm.legacy.CommandSetFP
import com.sistechnology.aurorapos2.core.fp_comm.legacy.PrinterFunctions
import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.FiscalDevice
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.PrintingDevice

class UsbDeviceHandler {

    var context = App.context
    val TAG: String = "UsbPrinterCommunication"





    var sharedPrefs = SharedPreferencesHelper.getInstance(App.context)
    var connected: Boolean = false




    init {
        registerReceivers()
        init()
    }




      fun clear() {
        usbFiscalDevice = null
        connected = false
    }

     fun init() {
        val manager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        val deviceList: HashMap<String, UsbDevice> = manager.deviceList
        deviceList.values.forEach { device ->
            val fiscalDevice = checkDeviceFiscalPrinter(device)
            if (fiscalDevice!=FiscalDevice.NoDevice) {
                val permissionIntent =
                    PendingIntent.getBroadcast(
                        context,
                        0,
                        Intent(App.ACTION_USB_PRINTER_PERMISSION),
                        0
                    )
                manager.requestPermission(device, permissionIntent)
            }
        }
    }



    private fun registerReceivers() {
        val usbPermissionReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                if (App.ACTION_USB_PRINTER_PERMISSION == intent.action) {
                    synchronized(this) {
                        val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)

                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            device?.apply {
                                val fiscalDevice = checkDeviceFiscalPrinter(device)
                                if (fiscalDevice != FiscalDevice.NoDevice) {
                                    usbFiscalDevice = device
                                    when(fiscalDevice){
                                        FiscalDevice.Daisy -> PrinterFunctions.setCommandSetFP(
                                            CommandSetFP()
                                        )
                                        FiscalDevice.Datecs -> PrinterFunctions.setCommandSetFP(
                                            CommandSetFP()
                                        )
                                        else -> {PrinterFunctions.setCommandSetFP(null)}
                                    }
                                    PrinterFunctions.getCommandSetFP().initComm()
                                }
                            }
                        } else {
                            Log.d(TAG, "permission denied for device $device")
                        }
                    }
                }
            }
        }
        val filter = IntentFilter(App.ACTION_USB_PRINTER_PERMISSION)
        context?.registerReceiver(usbPermissionReceiver, filter)

    }

     fun checkDetachedPrinter(device: UsbDevice?) : Boolean {
        if(usbFiscalDevice==device) {
            usbFiscalDevice = null
            PrinterFunctions.setCommandSetFP(null)
            return true
        } else if(usbPrintingDevice==device){
            usbPrintingDevice = null
            PrinterFunctions.setCommandSetNonFP(null)
            return true
        }
         return false
    }

    companion object {

        val DAISY_PID = 22336
        val DAISY_VID = 1155
        val ELTRADE_PID = 24857
        val ELTRADE_VID = 1003
        val DATECS_PID = 8963
        val DATECS_FTDI_PID = 24577
        val DATECS_VID = 1659
        val DATECS_USB_VID = 65520
        val FTDI_USB_VID = 1027
        var usbFiscalDevice: UsbDevice? = null
        var usbPrintingDevice: UsbDevice? = null


        fun checkDeviceFiscalPrinter(device: UsbDevice?): FiscalDevice {
            if (device?.vendorId == DAISY_VID || device?.productId == DAISY_PID) {
                return FiscalDevice.Daisy
            } else if(device?.vendorId == FTDI_USB_VID && device?.productId == DATECS_FTDI_PID){
                return FiscalDevice.Datecs
            }

            return FiscalDevice.NoDevice
        }
        fun checkDevicePrinter(device: UsbDevice?) : PrintingDevice {
            if(device?.vendorId == 4660)
                return PrintingDevice.PAX_D210
            return PrintingDevice.NoDevice
        }
    }


}