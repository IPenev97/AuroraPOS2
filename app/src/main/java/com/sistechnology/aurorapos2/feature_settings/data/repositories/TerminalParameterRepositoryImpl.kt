package com.sistechnology.aurorapos2.feature_settings.data.repositories

import com.sistechnology.aurorapos2.feature_settings.data.local.dao.TerminalParameterDao
import com.sistechnology.aurorapos2.feature_settings.data.local.entities.TerminalParameterEntity
import com.sistechnology.aurorapos2.feature_settings.domain.models.PrintingDeviceInfo
import com.sistechnology.aurorapos2.feature_settings.domain.repositories.TerminalParameterRepository


class TerminalParameterRepositoryImpl(
    val terminalParameterDao: TerminalParameterDao
) : TerminalParameterRepository {
    override suspend fun saveTerminalParameter(terminalParameterEntity: TerminalParameterEntity) {
        terminalParameterDao.editTerminalParameter(terminalParameterEntity)
    }

    override suspend fun getPrintingDeviceInfo(): PrintingDeviceInfo {
        val terminalParameter = terminalParameterDao.getTerminalParameter()
        return if(terminalParameter!=null)
            PrintingDeviceInfo(
                printingDevice = terminalParameter.printingDevice,
                fiscalDevice = terminalParameter.fiscalDevice,
                printerName = terminalParameter.printerName,
                autoPrintDuplicate = terminalParameter.autoPrintDuplicate,
                autoPrintReceipt = terminalParameter.autoPrintReceipt
            )
        else PrintingDeviceInfo()
    }

    override suspend fun savePrintingDeviceInfo(printingDeviceInfo: PrintingDeviceInfo) {
        var terminalParameter = terminalParameterDao.getTerminalParameter()
        terminalParameter = terminalParameter.copy(
            printingDevice = printingDeviceInfo.printingDevice,
            fiscalDevice = printingDeviceInfo.fiscalDevice,
            printerName = printingDeviceInfo.printerName,
            autoPrintReceipt = printingDeviceInfo.autoPrintReceipt,
            autoPrintDuplicate = printingDeviceInfo.autoPrintDuplicate
        )
        terminalParameterDao.editTerminalParameter(terminalParameter)


    }


}