package com.sistechnology.aurorapos2.feature_home.data.local.dao.receipt

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.sistechnology.aurorapos2.feature_home.data.local.entities.receipt.ParkedReceiptEntity
import com.sistechnology.aurorapos2.feature_home.data.local.entities.receipt.ReceiptWithReceiptItems
import kotlinx.coroutines.flow.Flow


@Dao
interface ParkedReceiptDao {
    @Query("SELECT * FROM ParkedReceipt")
    fun getALl() : Flow<List<ParkedReceiptEntity>>



    @Transaction
    @Query("SELECT * FROM ParkedReceipt WHERE userId = :userId AND basketNumber = :basketNumber")
    suspend fun getReceiptWithReceiptItemsByUserIdAndBasketNumber(userId: Int, basketNumber: Int) : ReceiptWithReceiptItems?

    @Transaction
    @Query("SELECT * FROM ParkedReceipt WHERE userId = :userId ORDER BY basketNumber ASC")
    suspend fun getAllReceiptsWithReceiptItemsByUserId(userId: Int) : List<ReceiptWithReceiptItems>


    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg receiptEntity: ParkedReceiptEntity) : LongArray




}