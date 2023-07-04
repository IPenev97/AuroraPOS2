package com.sistechnology.aurorapos2.feature_settings.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.FiscalDevice
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.PrintingDevice

@Entity(tableName = "TerminalParameter")
data class TerminalParameterEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    @ColumnInfo(name = "printing_device")
    val printingDevice: PrintingDevice = PrintingDevice.NoDevice,
    @ColumnInfo(name = "fiscal_device")
    val fiscalDevice: FiscalDevice = FiscalDevice.NoDevice,
    @ColumnInfo(name = "printer_name")
    val printerName: String = "",
    @ColumnInfo(name = "auto_print_receipt")
    val autoPrintReceipt: Boolean = false,
    @ColumnInfo(name = "auto_print_duplicate")
    val autoPrintDuplicate: Boolean = false
) {

}