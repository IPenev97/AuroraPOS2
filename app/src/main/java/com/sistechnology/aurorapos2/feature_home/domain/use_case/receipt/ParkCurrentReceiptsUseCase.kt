package com.sistechnology.aurorapos2.feature_home.domain.use_case.receipt

import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ReceiptRepository

class ParkCurrentReceiptsUseCase(
    val receiptRepository: ReceiptRepository,
    val sharedPreferencesHelper: SharedPreferencesHelper
) {
    suspend operator fun invoke(vararg receipts: Receipt){
        receiptRepository.deleteParkedReceiptItems(sharedPreferencesHelper.getCurrentUserId()?:0)
        receiptRepository.addReceipt(*receipts)
    }
}