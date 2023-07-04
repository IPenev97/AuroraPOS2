package com.sistechnology.aurorapos2.feature_home.domain.repositories

import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {
    suspend fun getAllReceiptsByUserId(userId: Int) : MutableList<Receipt>
    suspend fun getReceiptByUserIdAndBasketNumber(userId: Int, basketId: Int) : Receipt
    suspend fun addReceipt(vararg receipt: Receipt)

    suspend fun deleteReceiptItem(vararg receiptItem: ReceiptItem)
    suspend fun deleteParkedReceiptItems(userId: Int)


}