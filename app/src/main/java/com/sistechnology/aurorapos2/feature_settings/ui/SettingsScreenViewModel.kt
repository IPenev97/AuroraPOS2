package com.sistechnology.aurorapos2.feature_settings.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sistechnology.aurorapos2.core.utils.InputValidator
import com.sistechnology.aurorapos2.feature_settings.domain.use_case.SettingsUseCases
import com.sistechnology.aurorapos2.feature_settings.ui.printing_device.PrintingDeviceState
import com.sistechnology.aurorapos2.feature_settings.ui.printing_device.PrintingDeviceEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    val settingsUseCases: SettingsUseCases
) : ViewModel() {


    private val _printingDeviceState = mutableStateOf(PrintingDeviceState())
    val printingDeviceState: State<PrintingDeviceState> = _printingDeviceState

    var getPrintingDeviceInfoJob: Job? = null


    fun onPrintingDeviceEvent(event: PrintingDeviceEvent) {
        when (event) {
            is PrintingDeviceEvent.TogglePrintingDeviceBox -> {
                _printingDeviceState.value =
                    printingDeviceState.value.copy(showPrintingDeviceBox = event.show)
            }
            is PrintingDeviceEvent.PrinterNameEntered -> {
                val printingDeviceInfo = _printingDeviceState.value.printingDeviceInfo.copy(
                    printerName = event.name,
                    printerNameError = InputValidator.validatePrinterName(event.name)
                )
                _printingDeviceState.value =
                    printingDeviceState.value.copy(printingDeviceInfo = printingDeviceInfo)
            }
            is PrintingDeviceEvent.SavePrintingDeviceInfo -> {
                viewModelScope.launch { settingsUseCases.savePrintingDeviceInfoUseCase(event.printingDeviceInfo) }
                _printingDeviceState.value =
                    printingDeviceState.value.copy(showPrintingDeviceBox = false)
            }
            PrintingDeviceEvent.GetPrintingDeviceInfo -> {
                _printingDeviceState.value =
                    printingDeviceState.value.copy(showPrintingDeviceBox = true)
                getPrintingDeviceInfo()

            }
        }
    }

    fun getPrintingDeviceInfo() {
        getPrintingDeviceInfoJob?.cancel()
        getPrintingDeviceInfoJob = viewModelScope.launch {
            _printingDeviceState.value =
                printingDeviceState.value.copy(printingDeviceInfo = settingsUseCases.getPrintingDeviceInfoUseCase())
        }
    }

}