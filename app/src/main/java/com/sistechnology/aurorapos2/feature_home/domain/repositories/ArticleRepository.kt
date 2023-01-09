package com.sistechnology.aurorapos2.feature_home.domain.repositories

import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleGroup
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleInfo
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getAllArticles() : Flow<List<Article>>
    suspend fun getArticleById(id: Int): Article
    suspend fun addArticle(vararg article: Article)
    fun getArticlesByGroupId(id: Int) : Flow<List<Article>>

    fun getAllArticleGroups() : Flow<List<ArticleGroup>>
    suspend fun getArticleGroupById(id: Int): ArticleGroup
    suspend fun addArticleGroup(vararg articleGroup: ArticleGroup)
    suspend fun editArticle(article: Article)
}