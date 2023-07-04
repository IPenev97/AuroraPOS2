package com.sistechnology.aurorapos2.feature_settings.ui.printing_device

import com.sistechnology.aurorapos2.feature_settings.domain.models.PrintingDeviceInfo

data class PrintingDeviceState(
    val printingDeviceInfo: PrintingDeviceInfo = PrintingDeviceInfo(),
    val showPrintingDeviceBox: Boolean = false
) {
}