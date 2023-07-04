package com.sistechnology.aurorapos2.feature_home.domain.models.receipt

import com.sistechnology.aurorapos2.feature_home.domain.models.enums.DiscountType

data class Receipt(
    val id: Int = 0,
    val basketNumber: Int = 0,
    var discountType: DiscountType = DiscountType.Percent,
    var discountValue: Double = 0.00,
    var receiptItemList: List<ReceiptItem> = mutableListOf()
) {
     fun getTotal() : Double{
        var total = 0.0
        for(item: ReceiptItem in receiptItemList){
            total+= item.getTotal()
        }
         if(discountValue>0) {
             total -= if (discountType == DiscountType.Percent)
                 total * (discountValue / 100)
             else
                 discountValue
         }
        return String.format("%.2f", total).toDouble()
    }
    fun getTotalWithoutDiscount() : Double {
        var total = 0.0
        for(item: ReceiptItem in receiptItemList){
            total+= item.getTotal()
        }
        return String.format("%.2f", total).toDouble()
    }
}