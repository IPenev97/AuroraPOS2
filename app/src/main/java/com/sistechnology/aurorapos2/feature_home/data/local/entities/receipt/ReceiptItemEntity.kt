package com.sistechnology.aurorapos2.feature_home.data.local.entities.receipt

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ReceiptItem")
data class ReceiptItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int,
    val articleId: Int,
    val receiptId: Int
)
