package com.sistechnology.aurorapos2.feature_payment.ui

import com.sistechnology.aurorapos2.core.fp_comm.Fp_Error
import com.sistechnology.aurorapos2.feature_payment.domain.models.PaymentType
import com.sistechnology.aurorapos2.feature_payment.domain.models.enums.PaymentValidation

data class PaymentState(
    val paymentTypeList: List<PaymentType> = emptyList(),
    val total: Double = 0.00,
    val payment: String = "0.00",
    val totalPayed: Double = 0.00,
    val paymentError: PaymentValidation? = null,
    val showAlreadyPayedDialog: Boolean = false,
    val showPrintingProgressDialog: Boolean = false,
    val showReceiptClosedDialog: Boolean = false,
    val showPrintErrorDialog: Boolean = false,
    val errorPrintMessage: Fp_Error? = null,
) {
}