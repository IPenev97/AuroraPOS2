package com.sistechnology.aurorapos2.core.fp_comm

import com.sistechnology.aurorapos2.core.fp_comm.legacy.Bon
import com.sistechnology.aurorapos2.feature_payment.domain.models.enums.PrintResult
import kotlinx.coroutines.flow.Flow

interface Printer {
    fun checkFP()
    suspend fun printFiscal() : Boolean
    fun printInvoice()

    companion object {
        var error: Fp_Error? = null
        var currentBon: Bon = Bon()
    }




}