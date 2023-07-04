package com.sistechnology.aurorapos2.feature_home.domain.models.receipt

import com.sistechnology.aurorapos2.feature_home.domain.models.enums.DiscountType

data class ReceiptItem(
    val id: Int = 0,
    var name: String = "",
    var price: Double = 0.0,
    var quantity: Double = 0.0,
    var articleId: Int = 0,
    var vatGroupId: Int = 1,
    var discountType: DiscountType = DiscountType.Percent,
    var discountValue: Double = 0.0
) {

    fun getTotal() : Double {
        var total = price*quantity
        if(discountValue>0) {
            total -= if (discountType == DiscountType.Percent)
                total * (discountValue / 100)
            else
                discountValue
        }
        return String.format("%.2f", total).toDouble()
    }
    fun getItemDiscount() : Double {
        if(discountValue>0){
            if(discountType == DiscountType.Percent)
                return String.format("%.2f", price*quantity*discountValue/100).toDouble()
            else
                return discountValue
        }
        return 0.0
    }

}