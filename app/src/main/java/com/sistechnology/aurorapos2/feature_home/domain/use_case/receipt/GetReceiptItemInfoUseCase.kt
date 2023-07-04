package com.sistechnology.aurorapos2.feature_home.domain.use_case.receipt

import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItemInfo
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ReceiptRepository

class GetReceiptItemInfoUseCase(
    val receiptRepository: ReceiptRepository
) {
     operator fun invoke(receiptItem: ReceiptItem): ReceiptItemInfo {
        return ReceiptItemInfo(
            id = receiptItem.id,
            name = receiptItem.name,
            price = receiptItem.price.toString(),
            quantity = receiptItem.quantity.toString(),
            articleId = receiptItem.articleId,
            discountValue = receiptItem.discountValue.toString(),
            discountType = receiptItem.discountType,
        )

    }
}