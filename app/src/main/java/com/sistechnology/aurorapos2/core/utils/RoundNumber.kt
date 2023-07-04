package com.sistechnology.aurorapos2.core.utils

class RoundNumber {
companion object{
    fun to2DecimalPlaces(number: Double) : Double{
        //TODO try catch validation
        return String.format("%.2f", number).toDouble()
    }
}
}