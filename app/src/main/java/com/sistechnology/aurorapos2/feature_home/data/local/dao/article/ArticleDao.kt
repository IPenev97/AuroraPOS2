package com.sistechnology.aurorapos2.feature_home.data.local.dao.article

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sistechnology.aurorapos2.feature_home.data.local.entities.article.ArticleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {
    @Query("SELECT * FROM Article")
    fun getAll() : Flow<List<ArticleEntity>>

    @Query("SELECT * FROM Article WHERE id = :id")
    suspend fun getById(id: Int) : ArticleEntity

    @Query("SELECT * FROM Article WHERE articleGroupId = :articleGroupId")
    fun getByArticleGroupId(articleGroupId: Int) : Flow<List<ArticleEntity>>

    @Query("SELECT * FROM Article WHERE favourite = 1")
    fun getAllFavourites() : Flow<List<ArticleEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertArticle(vararg article: ArticleEntity)
}