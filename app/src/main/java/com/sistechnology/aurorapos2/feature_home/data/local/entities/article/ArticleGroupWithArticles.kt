package com.sistechnology.aurorapos2.feature_home.data.local.entities.article

import androidx.room.Embedded
import androidx.room.Relation

data class ArticleGroupWithArticles(
    @Embedded
    val articleGroupEntity: ArticleGroupEntity,
    @Relation(parentColumn = "id", entityColumn = "articleGroupId")
    val articles: List<ArticleEntity>

)
