package com.sistechnology.aurorapos2.feature_home.domain.use_case.articles

import androidx.compose.runtime.snapshots.SnapshotApplyResult
import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleInfo
import com.sistechnology.aurorapos2.feature_home.domain.models.validation.ArticleValidation
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ArticleRepository

class EditArticle(
    val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(articleInfo: ArticleInfo): ArticleValidation {
        val validation = validateInfo(articleInfo)
        if (validation != ArticleValidation.Success) {
            return validation
        }
        articleRepository.editArticle(
            Article(
                id = articleInfo.id ?: 0,
                name = articleInfo.name ?: "",
                price = articleInfo.price?.toDouble() ?: 0.0,
                articleGroupId = articleInfo.groupId ?: 0
            )
        )
        return validation

    }

    private fun validateInfo(articleInfo: ArticleInfo): ArticleValidation {

        //ValidateName
        if (articleInfo.name != null) {
            if (articleInfo.name.length < 2 || articleInfo.name.length > 20)
                return ArticleValidation.InvalidName
        } else {
            return ArticleValidation.MissingName
        }


        //ValidatePrice
        val price: Double?
        try {
            price = articleInfo.price?.toDouble()
        } catch (exception: Exception) {
            return ArticleValidation.InvalidPriceFormat
        }

        if (price != null) {
            if (price <= 0 || price >= 100000)
                return ArticleValidation.InvalidPriceRange
        } else {
            return ArticleValidation.MissingPrice
        }

        return ArticleValidation.Success
    }
}