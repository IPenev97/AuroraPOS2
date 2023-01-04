package com.sistechnology.aurorapos2.feature_home.data.local.dao.article

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sistechnology.aurorapos2.feature_home.data.local.entities.article.ArticleGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleGroupDao {
    @Query("SELECT * FROM ArticleGroup")
    fun getAll() : Flow<List<ArticleGroupEntity>>
    @Query("SELECT * FROM ArticleGroup WHERE id = :id")
    suspend fun getById(id: Int) : ArticleGroupEntity
    @Insert(onConflict = REPLACE)
    suspend fun insertArticleGroup(vararg articleGroup: ArticleGroupEntity)
}