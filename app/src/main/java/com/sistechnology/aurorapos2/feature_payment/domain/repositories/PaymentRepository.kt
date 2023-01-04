package com.sistechnology.aurorapos2.feature_payment.domain.repositories

import com.sistechnology.aurorapos2.feature_payment.domain.models.PaymentType
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getPayments() : Flow<List<PaymentType>>
    suspend fun getPaymentById(id: Int) : PaymentType?
    suspend fun addPayment(vararg payment: PaymentType)
}