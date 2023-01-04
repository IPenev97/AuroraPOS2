package com.sistechnology.aurorapos2.feature_payment.ui

import com.sistechnology.aurorapos2.feature_payment.domain.models.PaymentType

data class PaymentState(
    val paymentTypeList: List<PaymentType> = emptyList(),
    val total: Double = 0.00,
    val totalPayed: Double = 0.00,
    val showAlreadyPayedDialog: Boolean = false
) {
}