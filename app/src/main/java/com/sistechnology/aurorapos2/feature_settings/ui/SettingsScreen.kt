package com.sistechnology.aurorapos2.feature_settings.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.ui.components.AppBar
import com.sistechnology.aurorapos2.feature_settings.ui.components.ButtonsColumn
import com.sistechnology.aurorapos2.feature_settings.ui.printing_device.PrintingDeviceEvent
import com.sistechnology.aurorapos2.feature_settings.ui.printing_device.components.PrintingDeviceBox

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsScreenViewModel = hiltViewModel(),
) {
    val blueColor = colorResource(id = R.color.logo_blue)
    val orangeColor = colorResource(id = R.color.logo_orange)

    val printingDeviceState = viewModel.printingDeviceState

    Scaffold(topBar = {
        AppBar(
            onMenuDrawerClick = {},
            onLogoutClick = {},
            navController = navController,
            onSettingsClick = {}
        )
    },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),

                ) {
                ButtonsColumn(
                    onGeneralSettingsClick = {},
                    onPrintingDeviceClick = {
                        viewModel.onPrintingDeviceEvent(
                            PrintingDeviceEvent.GetPrintingDeviceInfo
                        )
                    },
                    onScaleDeviceClick = {}
                )
            }


        })

    //EditBoxes
    AnimatedVisibility(
        visible = printingDeviceState.value.showPrintingDeviceBox,
        enter = slideInVertically(initialOffsetY = { 600 }) + fadeIn(
            initialAlpha = 0.2f
        ),
        exit = slideOutVertically(targetOffsetY = { 600 }) + fadeOut(targetAlpha = 0.2f)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            PrintingDeviceBox(
                printingDeviceInfo = printingDeviceState.value.printingDeviceInfo,
                onPrinterNameEntered = {
                    viewModel.onPrintingDeviceEvent(
                        PrintingDeviceEvent.PrinterNameEntered(it)
                    )
                },
            onDismiss = {viewModel.onPrintingDeviceEvent(PrintingDeviceEvent.TogglePrintingDeviceBox(false))},
            onSave = {viewModel.onPrintingDeviceEvent(PrintingDeviceEvent.SavePrintingDeviceInfo(it))})
        }

    }
}