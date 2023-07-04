package com.sistechnology.aurorapos2.feature_settings.domain.use_case

import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_settings.domain.models.PrintingDeviceInfo
import com.sistechnology.aurorapos2.feature_settings.domain.repositories.TerminalParameterRepository

class SavePrintingDeviceInfoUseCase(
    val terminalParameterRepository: TerminalParameterRepository,
    val sharedPreferencesHelper: SharedPreferencesHelper
) {
    suspend operator fun invoke(printingDeviceInfo: PrintingDeviceInfo){
        terminalParameterRepository.savePrintingDeviceInfo(printingDeviceInfo)
        sharedPreferencesHelper.setPrintingDeviceInfo(printingDeviceInfo)
    }
}