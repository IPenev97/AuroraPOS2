package com.sistechnology.aurorapos2.feature_home.domain.use_case.articles

import com.sistechnology.aurorapos2.core.utils.RoundNumber
import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleInfo
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ArticleRepository

class EditArticleUseCase(
    val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(articleInfo: ArticleInfo) {
        articleRepository.editArticle(
            Article(
                id = articleInfo.id ?: 0,
                name = articleInfo.name ?: "",
                price = RoundNumber.to2DecimalPlaces(articleInfo.price?.toDouble() ?: 0.0),
                articleGroupId = articleInfo.groupId ?: 0,
                vatGroupId = articleInfo.vatGroupId ?: 0,
                favourite = articleInfo.favourite
            )
        )
    }




}