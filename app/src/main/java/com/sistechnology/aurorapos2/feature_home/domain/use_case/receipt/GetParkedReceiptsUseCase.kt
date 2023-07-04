package com.sistechnology.aurorapos2.feature_home.domain.use_case.receipt

import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ReceiptRepository

class GetParkedReceiptsUseCase(
    val receiptRepository: ReceiptRepository,
    val sharedPreferencesHelper: SharedPreferencesHelper
) {

    suspend operator fun invoke() : MutableList<Receipt> {
        val receiptList = mutableListOf(
            Receipt(basketNumber = 1, receiptItemList =  mutableListOf()),
            Receipt(basketNumber = 2, receiptItemList =  mutableListOf()),
            Receipt(basketNumber = 3, receiptItemList =  mutableListOf()),
            Receipt(basketNumber = 4, receiptItemList =  mutableListOf()),
            Receipt(basketNumber = 5, receiptItemList =  mutableListOf()),
            Receipt(basketNumber = 6, receiptItemList =  mutableListOf()),
            Receipt(basketNumber = 7, receiptItemList =  mutableListOf()),
            Receipt(basketNumber = 8, receiptItemList =  mutableListOf()),
            Receipt(basketNumber = 9, receiptItemList =  mutableListOf()),
            Receipt(basketNumber = 10, receiptItemList =  mutableListOf()))


         receiptRepository.getAllReceiptsByUserId(sharedPreferencesHelper.getCurrentUserId() ?: 0).forEach {receipt ->
            val basket = receiptList.firstOrNull {it.basketNumber == receipt.basketNumber}
             basket?.receiptItemList = receipt.receiptItemList
             basket?.discountType = receipt.discountType
             basket?.discountValue = receipt.discountValue
         }

        return receiptList

    }
}