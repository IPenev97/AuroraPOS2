package com.sistechnology.aurorapos2.feature_home.ui.articles

import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleGroup
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleInfo

data class ArticlesState(
    val articleList: List<Article> = emptyList(),
    val articleGroupList: List<ArticleGroup> = emptyList(),
    val selectedArticleGroupId: Int = -1,
    val selectedArticleInfo: ArticleInfo? = null,
    val showEditArticleErrorDialog: Boolean = false,
    val showEditArticleSuccessDialog: Boolean = false,
    val editArticleErrorMessage: String = "",
) {
}