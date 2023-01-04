package com.sistechnology.aurorapos2.feature_home.domain.models.article

data class Article(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val articleGroupId: Int
)
