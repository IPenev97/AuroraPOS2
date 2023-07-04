package com.sistechnology.aurorapos2.feature_home.data.local.entities.article

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Article")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "articleGroupId")
    val articleGroupId: Int,
    @ColumnInfo(name = "vatGroupId")
    val vatGroupId: Int,
    @ColumnInfo(name = "favourite")
    val favourite: Boolean = false,


    )
