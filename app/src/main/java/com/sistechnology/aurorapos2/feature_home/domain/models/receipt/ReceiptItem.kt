package com.sistechnology.aurorapos2.feature_home.domain.models.receipt

data class ReceiptItem(
    val id: Int = 0,
    var name: String = "",
    var price: Double = 0.0,
    var quantity: Int = 0,
    var articleId: Int = 0
) {
}