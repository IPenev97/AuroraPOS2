package com.sistechnology.aurorapos2.feature_home.data.local.entities.receipt

import androidx.room.Embedded
import androidx.room.Relation
import com.sistechnology.aurorapos2.feature_authentication.data.local.entities.UserEntity

data class UserAndReceipt(
    @Embedded
    val user: UserEntity,
    @Relation(parentColumn = "id", entityColumn = "userId")
    val receipt: ParkedReceiptEntity
) {
}