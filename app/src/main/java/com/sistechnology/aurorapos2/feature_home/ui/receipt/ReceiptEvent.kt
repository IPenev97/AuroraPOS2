package com.sistechnology.aurorapos2.feature_home.ui.receipt

import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem

sealed class ReceiptEvent {

    //Edit Receipt Item events
    object SaveChangesToReceiptItem : ReceiptEvent()
    object DeleteReceiptItem : ReceiptEvent()
    data class SelectReceiptItem(val receiptIndex: Int, val receiptItem: ReceiptItem) : ReceiptEvent()
    data class ToggleEditReceiptItemBox(val show: Boolean) : ReceiptEvent()
    data class ReceiptItemQuantityEntered(val quantity: String) : ReceiptEvent()
    object ReceiptItemPlusPressed : ReceiptEvent()
    object ReceiptItemMinusPressed : ReceiptEvent()
    data class ReceiptItemDiscountEntered(val discount: String) : ReceiptEvent()
    object ReceiptItemDiscountTypeChanged : ReceiptEvent()


    //Receipt events
    object PayReceipt : ReceiptEvent()
    data class SelectBasket(val index: Int) : ReceiptEvent()
    object ParkReceipts : ReceiptEvent()
    data class ToggleClearReceiptItemListDialog(val show: Boolean) : ReceiptEvent()


    //Edit Receipt events
    object ClearReceipt : ReceiptEvent()
    data class ToggleDiscountBox(val show: Boolean) : ReceiptEvent()
    object SaveChangesToReceipt : ReceiptEvent()
    data class ReceiptDiscountEntered(val discount: String) : ReceiptEvent()
    object ReceiptDiscountTypeChanged : ReceiptEvent()

}