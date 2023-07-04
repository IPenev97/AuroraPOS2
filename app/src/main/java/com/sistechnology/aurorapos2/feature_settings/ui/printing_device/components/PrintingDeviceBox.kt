package com.sistechnology.aurorapos2.feature_settings.ui.printing_device.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.ui.components.CustomSwitch
import com.sistechnology.aurorapos2.core.ui.components.IconTextField
import com.sistechnology.aurorapos2.core.ui.components.OkayCancelButtonsRow
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.DiscountType
import com.sistechnology.aurorapos2.feature_settings.domain.models.PrintingDeviceInfo
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.FiscalDevice
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.PrintingDevice
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.PrintingDeviceValidation

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PrintingDeviceBox(
    printingDeviceInfo: PrintingDeviceInfo,
    onPrinterNameEntered: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (PrintingDeviceInfo) -> Unit

) {

    var printingDeviceExpanded by remember { mutableStateOf(false) }
    var fiscalDeviceExpanded by remember { mutableStateOf(false) }

    var printerDeviceErrorMessage by remember { mutableStateOf("") }


    val orangeColor = colorResource(id = R.color.logo_orange)
    val orangeLightColor = orangeColor.copy(TextFieldDefaults.BackgroundOpacity)
    val context = LocalContext.current

    LaunchedEffect(key1 = printingDeviceInfo) {
        printerDeviceErrorMessage = when (printingDeviceInfo.printerNameError) {
            PrintingDeviceValidation.InvalidPrinterNameRange -> context.getString(R.string.edit_printing_device_invalid_name)
            else -> ""
        }
    }




    Surface(
        modifier = Modifier
            .fillMaxWidth(0.3f)
            .fillMaxHeight(0.7f),
        shape = RoundedCornerShape(16.dp),
        elevation = 10.dp
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center
        ) {
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp)),
                expanded = printingDeviceExpanded,
                onExpandedChange = {
                    printingDeviceExpanded = !printingDeviceExpanded
                }
            ) {
                TextField(
                    value = printingDeviceInfo.printingDevice.name,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text(
                            text = stringResource(id = R.string.printing_device),
                            color = orangeColor,
                            fontSize = 16.sp
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = printingDeviceExpanded,
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = orangeColor,
                        backgroundColor = orangeLightColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(fontSize = 25.sp, color = orangeColor)
                )
                ExposedDropdownMenu(
                    expanded = printingDeviceExpanded,
                    onDismissRequest = { printingDeviceExpanded = false }
                ) {
                    PrintingDevice.values().forEach { selectedOption ->
                        DropdownMenuItem(onClick = {
                            printingDeviceInfo.printingDevice = selectedOption
                            printingDeviceExpanded = false
                        }) {
                            Text(
                                text = selectedOption.name,
                                color = orangeColor,
                                fontSize = 25.sp
                            )
                        }
                    }
                }
            }
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(12.dp)),
                expanded = fiscalDeviceExpanded,
                onExpandedChange = {
                    fiscalDeviceExpanded = !fiscalDeviceExpanded
                }
            ) {
                TextField(
                    value = printingDeviceInfo.fiscalDevice.name,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text(
                            text = stringResource(id = R.string.fiscal_device),
                            color = orangeColor,
                            fontSize = 16.sp
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = fiscalDeviceExpanded,
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = orangeColor,
                        backgroundColor = orangeLightColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(fontSize = 25.sp, color = orangeColor)
                )
                ExposedDropdownMenu(
                    expanded = fiscalDeviceExpanded,
                    onDismissRequest = { fiscalDeviceExpanded = false }
                ) {
                    FiscalDevice.values().forEach { selectedOption ->
                        DropdownMenuItem(onClick = {
                            printingDeviceInfo.fiscalDevice = selectedOption
                            fiscalDeviceExpanded = false
                        }) {
                            Text(
                                text = selectedOption.name,
                                color = orangeColor,
                                fontSize = 25.sp
                            )
                        }
                    }
                }
            }

            IconTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = printingDeviceInfo.printerName,
                label = stringResource(id = R.string.printer_name),
                onValueChange = onPrinterNameEntered,
                errorMessage = printerDeviceErrorMessage
            )
            Column(modifier = Modifier.weight(0.8f).fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.print_receipt),
                    color = orangeColor,
                    fontSize = 16.sp
                )
                CustomSwitch(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxSize()
                        .height(50.dp),
                    onClick = { printingDeviceInfo.autoPrintReceipt = !printingDeviceInfo.autoPrintReceipt},
                    color = orangeColor,
                    rightText = stringResource(id = R.string.on_demand),
                    leftText = stringResource(id = R.string.automatically),
                    switchedOn = !printingDeviceInfo.autoPrintReceipt
                )
            }
            Column(modifier = Modifier.weight(0.8f).fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.print_duplicate),
                    color = orangeColor,
                    fontSize = 16.sp
                )
                CustomSwitch(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxSize()
                        .height(50.dp),
                    onClick = { printingDeviceInfo.autoPrintDuplicate = !printingDeviceInfo.autoPrintDuplicate},
                    color = orangeColor,
                    rightText = stringResource(id = R.string.on_demand),
                    leftText = stringResource(id = R.string.automatically),
                    switchedOn = !printingDeviceInfo.autoPrintDuplicate
                )
            }

            OkayCancelButtonsRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f),
                onOk = { onSave(printingDeviceInfo)},
                onCancel = { onDismiss() })
        }


    }
}