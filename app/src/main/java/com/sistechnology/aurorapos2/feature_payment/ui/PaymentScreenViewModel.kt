package com.sistechnology.aurorapos2.feature_payment.ui

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sistechnology.aurorapos2.App
import com.sistechnology.aurorapos2.core.fp_comm.Printer
import com.sistechnology.aurorapos2.core.ui.Screen
import com.sistechnology.aurorapos2.core.utils.DateUtil
import com.sistechnology.aurorapos2.core.utils.InputValidator
import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_home.ui.HomeScreenViewModel
import com.sistechnology.aurorapos2.feature_payment.domain.models.Payment
import com.sistechnology.aurorapos2.feature_payment.domain.use_case.PaymentUseCases
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.FiscalDevice
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.PrintingDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentScreenViewModel @Inject constructor(
    private val paymentUseCases: PaymentUseCases
) : ViewModel() {



    private val _paymentState = mutableStateOf(PaymentState())
    val paymentState: State<PaymentState> = _paymentState


    private val _enteredPaymentsList = mutableStateListOf<Payment>()
    val enteredPaymentsList: List<Payment> = _enteredPaymentsList

    private val _eventFlow = MutableSharedFlow<HomeScreenViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getPaymentTypeJob: Job? = null
    private var sharedPrefs = SharedPreferencesHelper.getInstance(App.context)



    init {
        getPaymentTypes()
        _paymentState.value = paymentState.value.copy(total = Printer.currentBon.getTotal(), payment = Printer.currentBon.getTotal().toString())
    }

    fun onPaymentEvent(event: PaymentEvent){
        when(event){
            is PaymentEvent.EnterPayment -> {
                if(_paymentState.value.totalPayed>=_paymentState.value.total){
                    _paymentState.value = paymentState.value.copy(showAlreadyPayedDialog = true)
                    return
                }
                if(_paymentState.value.paymentError!=null){
                    return
                }
                val amount = _paymentState.value.payment.toDouble()
                _enteredPaymentsList.add(Payment(event.paymentType, amount))
                _paymentState.value = paymentState.value.copy(totalPayed = paymentState.value.totalPayed+amount)
                calculateTotalLeft()

            }
            is PaymentEvent.DeletePayment -> {
                _enteredPaymentsList.remove(event.payment)
                calculateTotalLeft()

            }
            is PaymentEvent.ToggleAlreadyPayedDialog -> {
                _paymentState.value = paymentState.value.copy(showAlreadyPayedDialog = event.show)
            }
            is PaymentEvent.PaymentChanged -> {
                _paymentState.value = paymentState.value.copy(payment = event.payment, paymentError = InputValidator.validatePaymentAmount(event.payment))
            }
            is PaymentEvent.TogglePaymentProgressDialog -> {

                _paymentState.value = paymentState.value.copy(showPrintingProgressDialog = event.show)
            }
            is PaymentEvent.ToggleErrorPrintDialog -> {
                _paymentState.value = paymentState.value.copy(showPrintErrorDialog = event.show)
            }
            is PaymentEvent.TogglePrintCompleteDialog -> {
                _paymentState.value = paymentState.value.copy(showReceiptClosedDialog = event.show)
            }
            PaymentEvent.FinishReceipt -> {
                viewModelScope.launch{
                    sharedPrefs.setClearBasket(true)
                    _eventFlow.emit(HomeScreenViewModel.UiEvent.Navigate(Screen.HomeScreen.route))
                }
            }
        }
    }

    private fun calculateTotalLeft() {
        if(_paymentState.value.paymentError==null){
            var totalPayed = 0.0
            _enteredPaymentsList.forEach { p-> totalPayed+=p.amount }
            val totalLeft = String.format("%.2f", _paymentState.value.total - totalPayed).toDouble()
            _paymentState.value = paymentState.value.copy(payment = if(totalLeft<0)"0.00" else totalLeft.toString(), totalPayed = totalPayed)
            if(_paymentState.value.totalPayed >= _paymentState.value.total){
                onPaymentEvent(PaymentEvent.TogglePaymentProgressDialog(true))
                makePayment()
            }
        }
    }


    private fun makePayment() {
        val test = sharedPrefs.getPrintingDeviceInfo()




        if(sharedPrefs.getPrintingDeviceInfo().fiscalDevice!=FiscalDevice.NoDevice) {
            Printer.currentBon.listPayments = enteredPaymentsList
            Printer.currentBon.date = DateUtil.getCurrentDateAsString()
            Printer.currentBon.time = DateUtil.getCurrentTimeAsString()

            viewModelScope.launch(Dispatchers.IO) {
                if (App.printer?.printFiscal() == true) {
                    _paymentState.value = paymentState.value.copy(
                        showPrintingProgressDialog = false,
                        showReceiptClosedDialog = true
                    )
                } else {
                    _paymentState.value = paymentState.value.copy(
                        showPrintingProgressDialog = false,
                        showPrintErrorDialog = true,
                        errorPrintMessage = Printer.error
                    )
                }
            }
        } else {
            _paymentState.value = paymentState.value.copy(
                showPrintingProgressDialog = false,
                showReceiptClosedDialog = true
            )
        }

    }

    private fun getPaymentTypes() {
        getPaymentTypeJob?.cancel()
        getPaymentTypeJob = paymentUseCases.getPaymentTypes().onEach { paymentTypes ->
            _paymentState.value = paymentState.value.copy(paymentTypeList = paymentTypes)
        }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class Navigate(val route: String) : UiEvent()
    }






}