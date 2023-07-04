package com.sistechnology.aurorapos2.feature_settings.domain.use_case

import com.sistechnology.aurorapos2.feature_settings.domain.models.PrintingDeviceInfo
import com.sistechnology.aurorapos2.feature_settings.domain.repositories.TerminalParameterRepository

class GetPrintingDeviceInfoUseCase(
    val terminalParameterRepository: TerminalParameterRepository
) {
    suspend operator fun invoke() : PrintingDeviceInfo{
        return terminalParameterRepository.getPrintingDeviceInfo()
    }
}