package com.sistechnology.aurorapos2.feature_payment.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sistechnology.aurorapos2.core.domain.values.receipt.CurrentReceiptList
import com.sistechnology.aurorapos2.feature_payment.domain.models.Payment
import com.sistechnology.aurorapos2.feature_payment.domain.use_case.PaymentUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class PaymentScreenViewModel @Inject constructor(
    private val paymentUseCases: PaymentUseCases
) : ViewModel() {



    private val _paymentState = mutableStateOf(PaymentState())
    val paymentState: State<PaymentState> = _paymentState

    private val _enteredPaymentsList = mutableStateListOf<Payment>()
    val enteredPaymentsList: List<Payment> = _enteredPaymentsList

    private var getPaymentTypeJob: Job? = null

    init {
        getPaymentTypes()
        _paymentState.value = paymentState.value.copy(total = roundDouble(CurrentReceiptList.currentReceipt.getTotal()))
    }

    fun onPaymentEvent(event: PaymentEvent){
        when(event){
            is PaymentEvent.EnterPayment -> {
                if(_paymentState.value.totalPayed>=_paymentState.value.total){
                    _paymentState.value = paymentState.value.copy(showAlreadyPayedDialog = true)
                    return
                }
                if (event.amount.isEmpty()) return
                val amount = roundDouble(event.amount.toDouble())

                _enteredPaymentsList.add(Payment(event.paymentType, amount))
                _paymentState.value = paymentState.value.copy(totalPayed = roundDouble(paymentState.value.totalPayed+amount))
            }
            is PaymentEvent.DeletePayment -> {
                _enteredPaymentsList.remove(event.payment)
                _paymentState.value = paymentState.value.copy(totalPayed = roundDouble(paymentState.value.totalPayed-event.payment.amount))
            }
            is PaymentEvent.ToggleAlreadyPayedDialog -> {
                _paymentState.value = paymentState.value.copy(showAlreadyPayedDialog = event.show)
            }
        }
    }

    private fun getPaymentTypes() {
        getPaymentTypeJob = paymentUseCases.getPaymentTypes().onEach { paymentTypes ->
            _paymentState.value = paymentState.value.copy(paymentTypeList = paymentTypes)
        }.launchIn(viewModelScope)
    }

    private fun roundDouble(input: Double): Double{
        val roundingMode = RoundingMode.HALF_UP
        return BigDecimal(input).setScale(2, roundingMode).toDouble()
    }



}