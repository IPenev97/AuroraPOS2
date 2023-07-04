package com.sistechnology.aurorapos2.feature_payment.ui

import com.sistechnology.aurorapos2.feature_payment.domain.models.Payment
import com.sistechnology.aurorapos2.feature_payment.domain.models.PaymentType

sealed class PaymentEvent {

    data class TogglePaymentProgressDialog(val show: Boolean) : PaymentEvent()
    data class EnterPayment(val paymentType: PaymentType) : PaymentEvent()
    data class DeletePayment(val payment: Payment) : PaymentEvent()
    data class PaymentChanged(val payment: String) : PaymentEvent()
    data class ToggleAlreadyPayedDialog(val show: Boolean) : PaymentEvent()
    data class TogglePrintCompleteDialog(val show: Boolean) : PaymentEvent()
    data class ToggleErrorPrintDialog(val show: Boolean) : PaymentEvent()
    object FinishReceipt: PaymentEvent()
}