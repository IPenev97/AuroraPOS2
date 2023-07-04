package com.sistechnology.aurorapos2.core.utils

import com.sistechnology.aurorapos2.feature_home.domain.models.enums.validation.ArticleValidation
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.DiscountType
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.validation.ReceiptValidation
import com.sistechnology.aurorapos2.feature_payment.domain.models.enums.PaymentValidation
import com.sistechnology.aurorapos2.feature_settings.domain.models.enums.PrintingDeviceValidation

sealed class InputValidator {


    companion object{

        private val RECEIPT_ITEM_MAX_QUAN: Double = 99999.0
        private val ARTICLE_MAX_PRICE: Double = 99999.0

        //Article
        fun validateArticleName(name: String) : ArticleValidation?{
            if(name.isEmpty())
                return ArticleValidation.MissingName
            if(name.length<2 || name.length>20)
                return ArticleValidation.InvalidName
            return null
        }
        fun validateArticlePrice(price: String) : ArticleValidation?{
            val output: Double
            try{
                 output = price.toDouble()
            } catch (e: Exception){
                return ArticleValidation.InvalidPriceFormat
            }
            if (output<0 || output>ARTICLE_MAX_PRICE)
                return ArticleValidation.InvalidPriceRange
            return null
        }



        //Receipt
        fun validateReceiptItemQuantity(quantity: String) : ReceiptValidation?{
            val output: Double
            try {
                output = quantity.toDouble()
            } catch (e: Exception){
                return ReceiptValidation.InvalidQuantityFormat
            }
            if(output<=0 || output> RECEIPT_ITEM_MAX_QUAN)
                return ReceiptValidation.InvalidQuantityRange
            return null
        }
        fun validateReceiptDiscount(discountValue: String, discountType: DiscountType, total: Double) : ReceiptValidation?{

            if(discountValue.trim().isEmpty())
                return null
            val output: Double
            try {
                output = discountValue.toDouble()
            } catch (e: Exception){
                return ReceiptValidation.InvalidDiscountFormat
            }
            if(discountType == DiscountType.Percent){
                if(output<0 || output>=100){
                    return ReceiptValidation.InvalidDiscountRange
                }
            } else {
                if(output<0 || output>=total){
                    return ReceiptValidation.InvalidDiscountRange
                }
            }
            return null
        }

        //Payment
        fun validatePaymentAmount(amount: String) : PaymentValidation?{
            if(amount.isEmpty())
                return PaymentValidation.MissingPayment
            val output: Double
            try {
                output = amount.toDouble()
            } catch (e: Exception){
                return PaymentValidation.InvalidFormat
            }
            return null
        }

        //PrintingDevice
        fun validatePrinterName(name: String) : PrintingDeviceValidation?{
            if(name.length>20){
                return PrintingDeviceValidation.InvalidPrinterNameRange
            }
            return null
        }
    }


}