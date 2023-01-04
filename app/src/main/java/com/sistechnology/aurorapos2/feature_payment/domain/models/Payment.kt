package com.sistechnology.aurorapos2.feature_payment.domain.models

data class Payment(
    val type: PaymentType,
    val amount: Double
) {


}