package com.sistechnology.aurorapos2.feature_home.data.local.entities.receipt

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.DiscountType

@Entity(tableName = "ParkedReceipt")
data class ParkedReceiptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val basketNumber: Int,
    val userId: Int,
    val discountType: DiscountType,
    val discountValue: Double
)
