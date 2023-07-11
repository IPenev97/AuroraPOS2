package com.sistechnology.aurorapos2

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sistechnology.aurorapos2.core.fp_comm.usb_comm.UsbDeviceHandler
import com.sistechnology.aurorapos2.core.ui.NavigationDrawer
import com.sistechnology.aurorapos2.core.ui.Screen
import com.sistechnology.aurorapos2.core.ui.components.*
import com.sistechnology.aurorapos2.core.ui.theme.AuroraPOS2Theme
import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_authentication.ui.users.UsersScreen
import com.sistechnology.aurorapos2.feature_home.ui.HomeScreen
import com.sistechnology.aurorapos2.feature_home.ui.bar_drawer.BarDrawerEvent
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
                var showLogoutDialog by remember { mutableStateOf(false) }
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                val scope = rememberCoroutineScope()

                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBar(
                            onMenuDrawerClick = { scope.launch { scaffoldState.drawerState.open() } },
                            onLogoutClick = {
                                navController.navigate(Screen.UsersScreen.route)
                            },
                            navController = navController,
                            onSettingsClick = { navController.navigate(Screen.SettingsScreen.route) }
                        )
                    },
                    drawerShape = NavShape(300.dp, 0f),
                    drawerContent = {
                        NavigationDrawer(
                            items = listOf(
                                MenuItem(
                                    id = "Home",
                                    title = stringResource(id = R.string.home),
                                    contentDescription = "HomeButton",
                                    icon = Icons.Default.Home,
                                    screen = Screen.HomeScreen
                                ),
                                MenuItem(
                                    id = "Clients",
                                    title = stringResource(id = R.string.clients),
                                    contentDescription = "ClientsButton",
                                    icon = Icons.Default.Person,
                                    screen = Screen.PaymentScreen
                                ),
                                MenuItem(
                                    id = "Settings",
                                    title = stringResource(id = R.string.settings),
                                    contentDescription = "SettingsButton",
                                    icon = Icons.Default.Settings,
                                    screen = Screen.SettingsScreen
                                ),
                                MenuItem(
                                    id = "Logout",
                                    title = stringResource(id = R.string.logout),
                                    contentDescription = "LogoutButton",
                                    icon = Icons.Default.Logout,
                                    screen = Screen.PaymentScreen
                                ),
                            ),
                            onItemClick = {
                                if (it.id == "Logout")
                                    showLogoutDialog = true
                                else
                                    navController.navigate(it.screen.route)
                            }
                        )
                    },
                ) { padding ->
                    Surface(
                        modifier = Modifier.padding(padding),
                        color = MaterialTheme.colors.background
                    ) {
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
                                    if (App.usbDeviceHandler?.checkDetachedPrinter(device) != false) {
                                        usbStateChangeDialogMessage =
                                            applicationContext.getString(R.string.fiscal_printer_detached_message)
                                        showUsbStateChangedDialog = true
                                    }

                                }
                            }
                        }




                        NavHost(
                            navController = navController,
                            startDestination = startDestination
                        ) {
                            composable(
                                route = Screen.HomeScreen.route,

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
                if (showLogoutDialog) {
                    CustomDialog(
                        confirmButtonText = stringResource(id = R.string.ok),
                        messageText = stringResource(id = R.string.confirm_logout_text),
                        titleText = stringResource(id = R.string.logout),
                        onConfirm = {
                            scope.launch { scaffoldState.drawerState.close() }; navController.navigate(
                            Screen.UsersScreen.route
                        )
                        },
                        onDismiss = { showLogoutDialog = false },
                        confirmButtonColor = colorResource(id = R.color.okay_button_green),
                        cancelButtonText = stringResource(id = R.string.cancel),
                        imageVector = Icons.Default.Logout

                    )
                }

            }
        }
    }


}

@Preview()
@Composable
fun DefaultPreview() {

}
