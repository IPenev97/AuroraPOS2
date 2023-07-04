package com.sistechnology.aurorapos2.feature_home.domain.models.receipt

import com.sistechnology.aurorapos2.feature_home.domain.models.enums.DiscountType
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.validation.ReceiptValidation

data class ReceiptItemInfo(
    val id: Int = 0,
    var name: String = "",
    var price: String = "1",
    var quantity: String = "0",
    var articleId: Int = 0,
    var discountValue: String = "0",
    var discountType: DiscountType = DiscountType.Percent,

    //Validation
    var quantityError: ReceiptValidation? = null,
    var discountValueError: ReceiptValidation? = null

) {

    fun getTotalWithoutDiscount(): Double {
        var price: Double
        var quantity: Double
        try{
            price = this.price.toDouble()
            quantity = this.quantity.toDouble()
        } catch (e: Exception){
            //TODO
            return 0.0
        }
        return return String.format("%.2f", price*quantity).toDouble()
    }

    fun getTotal() : Double {
        val price: Double
        val quantity: Double
        var discountValue = 0.0

        try{
            price = this.price.toDouble()
            quantity = this.quantity.toDouble()
            if(this.discountValue.isNotEmpty()) {
                discountValue = this.discountValue.toDouble()
            }
        } catch (e: Exception){
            //TODO
            return 0.0
        }

        var total = price*quantity
        if(discountValue>0) {
            total -= if (discountType == DiscountType.Percent)
                total * (discountValue / 100)
            else
                discountValue
        }

        return String.format("%.2f", total).toDouble()
    }

    fun hasErrors() : Boolean{
        return quantityError!=null || discountValueError!=null
    }
}