package com.sistechnology.aurorapos2.feature_home.data.local.dao.receipt

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.sistechnology.aurorapos2.feature_home.data.local.entities.receipt.ReceiptItemEntity
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem

@Dao
interface ReceiptItemDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg receiptItem: ReceiptItemEntity)
    @Delete
    suspend fun delete(vararg receiptItem: ReceiptItemEntity)
}