package com.sistechnology.aurorapos2.feature_home.domain.use_case.receipt

import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptInfo

class GetReceiptInfoUseCase {
     operator fun invoke(receipt: Receipt): ReceiptInfo {
        return ReceiptInfo(
            id = receipt.id,
            basketNumber = receipt.basketNumber,
            discountType = receipt.discountType,
            discountValue = receipt.discountValue.toString(),
            sumWithoutDiscount = receipt.getTotalWithoutDiscount()
        )
    }
}