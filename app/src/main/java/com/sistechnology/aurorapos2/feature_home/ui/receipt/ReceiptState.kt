package com.sistechnology.aurorapos2.feature_home.ui.receipt

import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptInfo
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItemInfo

data class ReceiptState(
    val selectedReceiptItemInfo: ReceiptItemInfo = ReceiptItemInfo(),
    val selectedReceiptInfo: ReceiptInfo = ReceiptInfo(),
    val selectedReceiptItemIndex: Int = 0,
    val selectedBasketIndex: Int = 0,
    val showClearReceiptItemListDialog: Boolean = false,
    val showEditReceiptItemBox: Boolean = false,
    val showDiscountBox: Boolean = false,
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
        Receipt(basketNumber = 10, receiptItemList =  mutableListOf())),




    ) {
}