package com.sistechnology.aurorapos2.feature_settings.ui.printing_device

import com.sistechnology.aurorapos2.feature_settings.domain.models.PrintingDeviceInfo

sealed class PrintingDeviceEvent {
    data class TogglePrintingDeviceBox(val show: Boolean) : PrintingDeviceEvent()
    data class PrinterNameEntered(val name: String) : PrintingDeviceEvent()
    data class SavePrintingDeviceInfo(val printingDeviceInfo: PrintingDeviceInfo) : PrintingDeviceEvent()
    object GetPrintingDeviceInfo : PrintingDeviceEvent()
}