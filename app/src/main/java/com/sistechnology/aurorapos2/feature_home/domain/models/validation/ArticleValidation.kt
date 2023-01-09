package com.sistechnology.aurorapos2.feature_home.domain.models.validation

enum class ArticleValidation {
    Success,
    MissingName,
    MissingPrice,
    InvalidName,
    InvalidPriceFormat,
    InvalidPriceRange,
}