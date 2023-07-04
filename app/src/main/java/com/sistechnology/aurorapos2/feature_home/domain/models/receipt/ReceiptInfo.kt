package com.sistechnology.aurorapos2.feature_home.domain.models.receipt

import com.sistechnology.aurorapos2.feature_home.domain.models.enums.DiscountType
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.validation.ReceiptValidation

data class ReceiptInfo(
    val id: Int = 0,
    val basketNumber: Int = 0,
    val discountType: DiscountType = DiscountType.Percent,
    val discountValue: String = "0",
    var sumWithoutDiscount: Double = 0.0,
    var discountValueError: ReceiptValidation? = null

)
{
    fun getTotal() : Double{
        val discountValue: Double
        var output = sumWithoutDiscount
        try{
            discountValue = this.discountValue.toDouble()
        } catch (e: Exception){
            //TODO
            return output
        }
            if(discountValue>0) {
                output -= if (discountType == DiscountType.Percent)
                    output * (discountValue / 100)
                else
                    discountValue
            }
        return String.format("%.2f", output).toDouble()
    }

    fun hasErrors(): Boolean {
        return discountValueError!=null
    }
}
