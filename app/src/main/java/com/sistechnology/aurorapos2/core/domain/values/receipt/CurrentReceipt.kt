package com.sistechnology.aurorapos2.core.domain.values.receipt

import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt

object CurrentReceipt {


     var currentReceipt: Receipt = Receipt(receiptItemList = mutableListOf())

}