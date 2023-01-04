package com.sistechnology.aurorapos2.feature_home.data.local.entities.receipt

import androidx.room.Embedded
import androidx.room.Relation

data class ReceiptWithReceiptItems(
    @Embedded
    val receipt: ParkedReceiptEntity,
    @Relation(parentColumn = "id", entityColumn = "receiptId")
    val receiptItems: MutableList<ReceiptItemEntity>
) {

}