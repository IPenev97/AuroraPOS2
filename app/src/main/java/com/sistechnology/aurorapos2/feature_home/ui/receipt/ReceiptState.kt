package com.sistechnology.aurorapos2.feature_home.ui.receipt

import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem

data class ReceiptState(
    val selectedReceiptItem: ReceiptItem? = null,
    val total: Double = 0.0,
    val selectedReceiptIndex: Int = 0,
    val selectedBasketIndex: Int = 0,
    val receiptList: List<Receipt> = listOf(
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

) {
}