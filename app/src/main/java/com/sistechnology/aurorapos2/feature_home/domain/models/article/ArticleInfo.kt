package com.sistechnology.aurorapos2.feature_home.domain.models.article

import com.sistechnology.aurorapos2.feature_home.domain.models.enums.validation.ArticleValidation

data class ArticleInfo(
    val id: Int? = null,
    val name: String? = null,
    val price: String? = null,
    var groupId: Int? = null,
    var groupName: String? = null,
    var vatGroupId: Int? = null,
    var vatGroupName: String? = null,
    val favourite: Boolean = false,


    //Validation
    var nameError: ArticleValidation? = null,
    var priceError: ArticleValidation? = null
    ) {
    fun hasErrors() : Boolean{
        return nameError!=null || priceError!=null
    }
}