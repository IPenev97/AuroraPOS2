package com.sistechnology.aurorapos2.feature_home.data.local.entities.article

import androidx.room.Embedded
import androidx.room.Relation

data class VatGroupWithArticles(
    @Embedded
    val vatGroup: VatGroupEntity,
    @Relation(parentColumn = "id", entityColumn = "vatGroupId")
    val articles: List<ArticleEntity>
) {
}