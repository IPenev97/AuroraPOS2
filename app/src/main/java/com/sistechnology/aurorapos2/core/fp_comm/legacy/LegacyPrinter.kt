package com.sistechnology.aurorapos2.core.fp_comm.legacy

import com.sistechnology.aurorapos2.core.fp_comm.Printer

class LegacyPrinter() : Printer {






    init {

    }



    override fun checkFP() {
        TODO("Not yet implemented")
    }

    override suspend fun printFiscal() : Boolean {
        return PrinterFunctions.fpPrintBon(Printer.currentBon)
    }

    override fun printInvoice() {
        TODO("Not yet implemented")
    }

}