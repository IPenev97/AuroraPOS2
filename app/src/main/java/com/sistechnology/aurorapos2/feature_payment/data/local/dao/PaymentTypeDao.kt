package com.sistechnology.aurorapos2.feature_payment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sistechnology.aurorapos2.feature_payment.data.local.entities.PaymentTypeEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PaymentTypeDao {
    @Query("SELECT * FROM Payment")
    fun getAll() : Flow<List<PaymentTypeEntity>>

    @Query("SELECT * FROM Payment WHERE id = :id")
    suspend fun getById(id: Int) : PaymentTypeEntity?

    @Insert(onConflict = REPLACE)
    suspend fun insertPayment(vararg payment: PaymentTypeEntity)
}