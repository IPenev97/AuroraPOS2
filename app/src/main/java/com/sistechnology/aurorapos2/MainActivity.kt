package com.sistechnology.aurorapos2

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sistechnology.aurorapos2.core.fp_comm.usb_comm.UsbDeviceHandler
import com.sistechnology.aurorapos2.core.ui.Screen
import com.sistechnology.aurorapos2.core.ui.components.CustomDialog
import com.sistechnology.aurorapos2.core.ui.components.SystemBroadcastReceiver
import com.sistechnology.aurorapos2.core.ui.theme.AuroraPOS2Theme
import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_authentication.ui.users.UsersScreen
import com.sistechnology.aurorapos2.feature_home.ui.HomeScreen
import com.sistechnology.aurorapos2.feature_payment.ui.PaymentScreen
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.DeviceCommunicationType
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.FiscalDevice
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.PrintingDevice
import com.sistechnology.aurorapos2.feature_settings.domain.use_case.SettingsUseCases
import com.sistechnology.aurorapos2.feature_settings.ui.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    @Inject
    lateinit var settingsUseCases: SettingsUseCases

    private var TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AuroraPOS2Theme {
                var showUsbStateChangedDialog by remember { mutableStateOf(false) }
                var usbStateChangeDialogMessage by remember { mutableStateOf("") }
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val startDestination = if (sharedPreferencesHelper.getCurrentUserName()
                            ?.isEmpty() == true
                    ) Screen.UsersScreen.route else Screen.HomeScreen.route

                    SystemBroadcastReceiver(action = UsbManager.ACTION_USB_DEVICE_ATTACHED) { receiveIntent ->
                        val action = receiveIntent?.action ?: return@SystemBroadcastReceiver
                        if (action == UsbManager.ACTION_USB_DEVICE_ATTACHED) {
                            val device: UsbDevice? =
                                receiveIntent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                            val fiscalDevice =
                                UsbDeviceHandler.checkDeviceFiscalPrinter(device)
                            if (fiscalDevice != FiscalDevice.NoDevice) {
                                App.usbDeviceHandler?.init()
                                when (fiscalDevice) {
                                    FiscalDevice.Daisy -> usbStateChangeDialogMessage =
                                        applicationContext.getString(R.string.fiscal_printer_attached_daisy)
                                    else -> {}
                                }
                                showUsbStateChangedDialog = true

                                MainScope().launch {
                                    val printingDeviceInfo =
                                        settingsUseCases.getPrintingDeviceInfoUseCase()
                                    printingDeviceInfo.fiscalDevice = fiscalDevice
                                    printingDeviceInfo.fiscalDeviceComMType =
                                        DeviceCommunicationType.USB
                                    settingsUseCases.savePrintingDeviceInfoUseCase(
                                        printingDeviceInfo
                                    )
                                }
                            }
                            val printingDevice = UsbDeviceHandler.checkDevicePrinter(device)
                            if (printingDevice != PrintingDevice.NoDevice) {
                                App.usbDeviceHandler?.init()
                                when (printingDevice) {
                                    PrintingDevice.PAX_D210 -> usbStateChangeDialogMessage =
                                        applicationContext.getString(
                                            R.string.printer_attached_pax_d210
                                        )
                                    PrintingDevice.PAX_E500 -> usbStateChangeDialogMessage =
                                        applicationContext.getString(
                                            R.string.printer_attached_pax_e500
                                        )
                                    PrintingDevice.Datecs_EP_50 -> TODO()
                                    else -> {}
                                }
                                showUsbStateChangedDialog = true


                                MainScope().launch {
                                    val printingDeviceInfo =
                                        settingsUseCases.getPrintingDeviceInfoUseCase()
                                    printingDeviceInfo.printingDevice = printingDevice
                                    printingDeviceInfo.printingDeviceCommType =
                                        DeviceCommunicationType.USB
                                    settingsUseCases.savePrintingDeviceInfoUseCase(
                                        printingDeviceInfo
                                    )
                                }
                            }
                        }
                    }
                    SystemBroadcastReceiver(action = UsbManager.ACTION_USB_DEVICE_DETACHED) { receiveIntent ->
                        val action = receiveIntent?.action ?: return@SystemBroadcastReceiver
                        if (action == UsbManager.ACTION_USB_DEVICE_DETACHED) {
                            synchronized(this) {
                                val device: UsbDevice? =
                                    receiveIntent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                                if(App.usbDeviceHandler?.checkDetachedPrinter(device)!=false){
                                    usbStateChangeDialogMessage =
                                        applicationContext.getString(R.string.fiscal_printer_detached_message)
                                    showUsbStateChangedDialog = true
                                }

                            }
                        }
                    }




                    NavHost(navController = navController, startDestination = startDestination) {
                        composable( route = Screen.HomeScreen.route,

                        ) {
                            HomeScreen(navController = navController)
                        }
                        composable(route = Screen.UsersScreen.route) {
                            UsersScreen(navController = navController)
                        }
                        composable(route = Screen.PaymentScreen.route) {
                            PaymentScreen(navController = navController)
                        }
                        composable(route = Screen.SettingsScreen.route) {
                            SettingsScreen(navController = navController)
                        }
                    }
                }

                //Dialogs
                if (showUsbStateChangedDialog) {
                    CustomDialog(
                        confirmButtonText = stringResource(id = R.string.ok),
                        messageText = usbStateChangeDialogMessage,
                        titleText = stringResource(id = R.string.system),
                        onConfirm = { showUsbStateChangedDialog = false }) {

                    }
                }

            }
        }
    }



}

@Preview()
@Composable
fun DefaultPreview() {

}
