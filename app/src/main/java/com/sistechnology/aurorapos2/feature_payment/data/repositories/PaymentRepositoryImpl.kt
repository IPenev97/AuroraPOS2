package com.sistechnology.aurorapos2.feature_payment.data.repositories

import com.sistechnology.aurorapos2.feature_payment.data.local.dao.PaymentTypeDao
import com.sistechnology.aurorapos2.feature_payment.data.local.entities.PaymentTypeEntity
import com.sistechnology.aurorapos2.feature_payment.domain.models.PaymentType
import com.sistechnology.aurorapos2.feature_payment.domain.repositories.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PaymentRepositoryImpl(
    private val paymentDao: PaymentTypeDao
) : PaymentRepository {

    private fun PaymentType.toEntity() : PaymentTypeEntity {
        return PaymentTypeEntity(id = this.id,
        type = this.type)
    }

    private fun PaymentTypeEntity.toModel() : PaymentType {
        return PaymentType(id = this.id,
            type = this.type)
    }


    override fun getPayments(): Flow<List<PaymentType>> {
        return paymentDao.getAll().map{ list -> list.map { it.toModel() }}
    }

    override suspend fun getPaymentById(id: Int): PaymentType? {
        return paymentDao.getById(id)?.toModel()
    }

    override suspend fun addPayment(vararg payment: PaymentType) {
        paymentDao.insertPayment(*payment.map{it.toEntity()}.toTypedArray())
    }
}