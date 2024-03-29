package com.sistechnology.aurorapos2.feature_home.domain.repositories

import com.sistechnology.aurorapos2.feature_home.data.local.entities.article.VatGroupEntity
import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleGroup
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleInfo
import com.sistechnology.aurorapos2.feature_home.domain.models.article.VatGroup
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    //Articles
    fun getAllArticles() : Flow<List<Article>>
    suspend fun getArticleById(id: Int): Article
    suspend fun addArticle(vararg article: Article)
    fun getArticlesByGroupId(id: Int) : Flow<List<Article>>
    suspend fun editArticle(article: Article)
    fun getFavouriteArticles() : Flow<List<Article>>


    //Article Groups
    fun getAllArticleGroups() : Flow<List<ArticleGroup>>
    suspend fun getArticleGroupById(id: Int): ArticleGroup
    suspend fun addArticleGroup(vararg articleGroup: ArticleGroup)


    //Vat Groups
    fun getAllVatGroups() : Flow<List<VatGroup>>
    suspend fun getVatGroupById(id: Int) : VatGroup
    suspend fun insertVatGroup(vararg vatGroup: VatGroup)

}