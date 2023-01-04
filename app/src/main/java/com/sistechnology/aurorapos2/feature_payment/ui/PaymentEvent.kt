package com.sistechnology.aurorapos2.feature_payment.ui

import com.sistechnology.aurorapos2.feature_payment.domain.models.Payment
import com.sistechnology.aurorapos2.feature_payment.domain.models.PaymentType

sealed class PaymentEvent {
    data class EnterPayment(val paymentType: PaymentType, val amount: String) : PaymentEvent()
    data class DeletePayment(val payment: Payment) : PaymentEvent()
    data class ToggleAlreadyPayedDialog(val show: Boolean) : PaymentEvent()
}