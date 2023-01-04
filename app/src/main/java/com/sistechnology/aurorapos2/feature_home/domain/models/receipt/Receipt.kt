package com.sistechnology.aurorapos2.feature_home.domain.models.receipt

import com.sistechnology.aurorapos2.core.domain.values.receipt.CurrentReceiptList

data class Receipt(
    val id: Int = 0,
    val basketNumber: Int = 0,
    var receiptItemList: List<ReceiptItem> = mutableListOf()
) {
     fun getTotal() : Double{
        var total = 0.0
        for(item: ReceiptItem in receiptItemList){
            total+= item.price*item.quantity
        }
        return total
    }
}