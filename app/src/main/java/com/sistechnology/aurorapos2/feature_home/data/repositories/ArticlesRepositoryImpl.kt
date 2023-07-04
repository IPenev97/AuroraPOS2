package com.sistechnology.aurorapos2.feature_home.data.repositories

import com.sistechnology.aurorapos2.feature_home.data.local.dao.article.ArticleDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.article.ArticleGroupDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.article.VatGroupDao
import com.sistechnology.aurorapos2.feature_home.data.local.entities.article.ArticleEntity
import com.sistechnology.aurorapos2.feature_home.data.local.entities.article.ArticleGroupEntity
import com.sistechnology.aurorapos2.feature_home.data.local.entities.article.VatGroupEntity
import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleGroup
import com.sistechnology.aurorapos2.feature_home.domain.models.article.VatGroup
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticlesRepositoryImpl(
    private val articleDao: ArticleDao,
    private val articleGroupDao: ArticleGroupDao,
    private val vatGroupDao: VatGroupDao
) : ArticleRepository {


    //Articles
    private fun ArticleEntity.toModel(): Article {
        return Article(
            id = this.id,
            name = this.name,
            price = this.price,
            articleGroupId = this.articleGroupId,
            vatGroupId = this.vatGroupId,
            favourite = this.favourite
        )
    }

    private fun Article.toEntity(): ArticleEntity {
        return ArticleEntity(
            id = this.id,
            name = this.name,
            price = this.price,
            articleGroupId = this.articleGroupId,
            vatGroupId = this.vatGroupId,
            favourite = this.favourite
        )
    }

    override fun getAllArticles(): Flow<List<Article>> {
        return articleDao.getAll().map { list -> list.map { it.toModel() } }
    }

    override suspend fun getArticleById(id: Int): Article {
        return articleDao.getById(id).toModel()
    }

    override suspend fun editArticle(article: Article) {
        return articleDao.insertArticle(article.toEntity())
    }

    override suspend fun addArticle(vararg article: Article) {
        return articleDao.insertArticle(*article.map { it.toEntity() }.toTypedArray())
    }

    override fun getArticlesByGroupId(id: Int): Flow<List<Article>> {
        return articleDao.getByArticleGroupId(id).map{ list -> list.map{it.toModel()}}
    }

    override fun getFavouriteArticles(): Flow<List<Article>> {
        return articleDao.getAllFavourites().map{ list -> list.map{it.toModel()}}
    }




    //Article Groups
    private fun ArticleGroup.toEntity(): ArticleGroupEntity {
        return ArticleGroupEntity(id = this.id, name = this.name)
    }

    private fun ArticleGroupEntity.toModel(): ArticleGroup {
        return ArticleGroup(id = this.id, name = this.name)
    }

    override fun getAllArticleGroups(): Flow<List<ArticleGroup>> {

        return articleGroupDao.getAll().map { list -> list.map { it.toModel() } }
    }

    override suspend fun getArticleGroupById(id: Int): ArticleGroup {
        return articleGroupDao.getById(id).toModel()
    }

    override suspend fun addArticleGroup(vararg articleGroup: ArticleGroup) {
        return articleGroupDao.insertArticleGroup(*articleGroup.map { it.toEntity() }
            .toTypedArray())
    }


    //Vat Groups
    private fun VatGroup.toEntity(): VatGroupEntity {
        return VatGroupEntity(
            id = this.id,
            group = this.group,
            value = this.value
        )
    }

    private fun VatGroupEntity.toModel(): VatGroup {
        return VatGroup(
            id = this.id,
            group = this.group,
            value = this.value
        )
    }

    override fun getAllVatGroups(): Flow<List<VatGroup>> {
        return vatGroupDao.gelAll().map { list -> list.map { it.toModel() } }
    }

    override suspend fun getVatGroupById(id: Int): VatGroup {
        return vatGroupDao.getById(id).toModel()
    }

    override suspend fun insertVatGroup(vararg vatGroup: VatGroup) {
        return vatGroupDao.insertVatGroup(*vatGroup.map{it.toEntity()}.toTypedArray())
    }


}