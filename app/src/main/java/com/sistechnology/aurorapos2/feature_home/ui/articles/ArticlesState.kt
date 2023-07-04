package com.sistechnology.aurorapos2.feature_home.ui.articles

import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleGroup
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleInfo
import com.sistechnology.aurorapos2.feature_home.domain.models.article.VatGroup

data class ArticlesState(

    //Lists
    val articleList: List<Article> = emptyList(),
    val articleGroupList: List<ArticleGroup> = emptyList(),
    val vatGroupList: List<VatGroup> = emptyList(),

    //Selected Models
    val selectedArticleGroupId: Int = -1,
    val selectedArticleInfo: ArticleInfo? = null,

    //Dialogs, Boxes
    val showEditArticleErrorDialog: Boolean = false,
    val showEditArticleSuccessDialog: Boolean = false,
    val editArticleErrorMessage: String = "",
    val showEditArticleBox: Boolean = false,





) {
}