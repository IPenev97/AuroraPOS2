package com.sistechnology.aurorapos2.feature_home.domain.use_case.articles

import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleGroup
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleInfo
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetArticleInfo(
    val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(article: Article) : ArticleInfo{
        val articleInfo = ArticleInfo(
            groupName = articleRepository.getArticleGroupById(article.articleGroupId).name,
            name = article.name,
            price = article.price.toString(),
            groupId = article.articleGroupId,
            id = article.id
        )
        return articleInfo
    }
}