package com.sistechnology.aurorapos2.feature_home.ui.receipt

import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem

sealed class ReceiptEvent {
    data class EditReceipt(val receiptItem: ReceiptItem) : ReceiptEvent()
    object DeleteReceiptItem : ReceiptEvent()
    data class SelectReceiptItem(val receiptIndex: Int, val receiptItem: ReceiptItem) : ReceiptEvent()
    object PayReceipt : ReceiptEvent()
    object ClearReceipt : ReceiptEvent()
    data class SelectBasket(val index: Int) : ReceiptEvent()
    object ParkReceipts : ReceiptEvent()
}