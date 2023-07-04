package com.sistechnology.aurorapos2.feature_settings.domain.models

import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.DeviceCommunicationType
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.FiscalDevice
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.PrintingDevice
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.PrintingDeviceValidation

data class PrintingDeviceInfo(
    val id: Int = 1,
    var printingDevice: PrintingDevice = PrintingDevice.NoDevice,
    var fiscalDevice: FiscalDevice = FiscalDevice.NoDevice,
    var printingDeviceCommType: DeviceCommunicationType = DeviceCommunicationType.USB,
    var fiscalDeviceComMType: DeviceCommunicationType = DeviceCommunicationType.USB,
    var printerName: String = "",
    var fiscalMemory: String = "",
    var autoPrintReceipt: Boolean = false,
    var autoPrintDuplicate: Boolean = false,
    var printerNameError: PrintingDeviceValidation? = null
)
