package com.sistechnology.aurorapos2.feature_home.data.local.entities.receipt

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ParkedReceipt")
data class ParkedReceiptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val basketNumber: Int,
    val userId: Int
)
