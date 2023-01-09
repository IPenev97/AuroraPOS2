package com.sistechnology.aurorapos2.feature_home.domain.use_case.articles

import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleInfo

data class ArticlesUseCases(
    val getArticlesUseCase: GetArticlesUseCase,
    val getArticleGroupsUseCase: GetArticleGroupsUseCase,
    val getArticlesByGroupUseCase: GetArticlesByGroupUseCase,
    val getArticleInfo: GetArticleInfo,
    val editArticle: EditArticle
)