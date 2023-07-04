package com.sistechnology.aurorapos2

import android.app.Application
import android.content.Context
import com.sistechnology.aurorapos2.core.fp_comm.Printer
import com.sistechnology.aurorapos2.core.fp_comm.legacy.LegacyPrinter
import com.sistechnology.aurorapos2.core.fp_comm.usb_comm.UsbDeviceHandler
import com.sistechnology.aurorapos2.core.utils.PresetDataHelper
import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var presetDataHelper: PresetDataHelper

    @Inject
    lateinit var prefs: SharedPreferencesHelper

    companion object {

        lateinit var context: Context
        const val ACTION_USB_PRINTER_PERMISSION = "com.sistechnology.aurorapos2.USB_PERMISSION"
        var printer: Printer? = null
        var usbDeviceHandler: UsbDeviceHandler? = null


    }


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        usbDeviceHandler = UsbDeviceHandler()
        if(prefs.getIsFirstRun() == true) {
            MainScope().launch {
                presetDataHelper.populateDatabase()
            }
            prefs.setIsFirstRun(false)
        }
            printer = LegacyPrinter()



    }
}