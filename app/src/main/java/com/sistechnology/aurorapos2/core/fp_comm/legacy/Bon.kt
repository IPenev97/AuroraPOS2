package com.sistechnology.aurorapos2.core.fp_comm.legacy

import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.BonPrintData
import com.sistechnology.aurorapos2.core.fp_comm.legacy.utils.ClientDataProvider
import com.sistechnology.aurorapos2.feature_clients.domain.models.Client
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem
import com.sistechnology.aurorapos2.feature_payment.domain.models.Payment

data class Bon(
    var id: Int = 0,
    var usn: String = "",
    var bonType: Int = 1,
    var client: ClientDataProvider? = null,
    var isDuplicateLevel: Boolean = false,
    var isReversal: Boolean = false,
    var printData: BonPrintData? = null,
    var reversedBon: Bon? = null,
    var bonNum: Int = 1,
    var closure: Int = 1,
    var receiptNumber: Int = 1,
    var date: String = "",
    var time: String = "",
    var listArticles: List<ReceiptItem> = emptyList(),
    var listPayments: List<Payment> = emptyList(),
    var flagPrinted: Boolean = false,



) {
     fun isFiscal() : Boolean {
        return bonType!=10 && bonType!=11
    }
    fun isInvoice() : Boolean {
        return bonType==4 || bonType==5
    }

    fun getTotal() : Double {
        //TODO SubtDisc
        var total = 0.0
        for(receiptItem in listArticles){
            total+=receiptItem.getTotal()
        }
        return total
    }

}