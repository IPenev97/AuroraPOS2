package com.sistechnology.aurorapos2.feature_home.domain.use_case.articles

data class ArticlesUseCases(
    val getArticlesUseCase: GetArticlesUseCase,
    val getArticleGroupsUseCase: GetArticleGroupsUseCase,
    val getArticlesByGroupUseCase: GetArticlesByGroupUseCase,
    val getArticleInfo: GetArticleInfoUseCase,
    val editArticle: EditArticleUseCase,
    val getVatGroupsUseCase: GetVatGroupsUseCase,
    val getFavouriteArticlesUseCase: GetFavouriteArticlesUseCase
)