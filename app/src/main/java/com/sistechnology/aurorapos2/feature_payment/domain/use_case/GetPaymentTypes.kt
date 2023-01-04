package com.sistechnology.aurorapos2.feature_payment.domain.use_case

import com.sistechnology.aurorapos2.feature_payment.domain.models.PaymentType
import com.sistechnology.aurorapos2.feature_payment.domain.repositories.PaymentRepository
import kotlinx.coroutines.flow.Flow

class GetPaymentTypes (
    private val paymentRepository: PaymentRepository
        ){
    operator fun invoke() : Flow<List<PaymentType>>{
        return paymentRepository.getPayments()
    }
}