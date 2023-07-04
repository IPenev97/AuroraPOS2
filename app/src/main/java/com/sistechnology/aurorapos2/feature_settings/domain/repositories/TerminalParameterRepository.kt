package com.sistechnology.aurorapos2.feature_settings.domain.repositories

import com.sistechnology.aurorapos2.feature_settings.data.local.entities.TerminalParameterEntity
import com.sistechnology.aurorapos2.feature_settings.domain.models.PrintingDeviceInfo

interface TerminalParameterRepository {
    suspend fun saveTerminalParameter(terminalParameterEntity: TerminalParameterEntity)
    suspend fun getPrintingDeviceInfo() : PrintingDeviceInfo
    suspend fun savePrintingDeviceInfo(printingDeviceInfo: PrintingDeviceInfo)


}