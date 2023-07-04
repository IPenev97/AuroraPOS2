package com.sistechnology.aurorapos2.feature_home.ui.articles

import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleInfo

sealed class ArticleEvent {
    data class SelectArticleGroup(val id: Int): ArticleEvent()
    data class AddToReceipt(val article: Article) : ArticleEvent()
    data class SelectArticle(val article: Article) : ArticleEvent()

    data class EditArticle(val articleInfo: ArticleInfo) : ArticleEvent()
    data class NameEntered(val name: String) : ArticleEvent()
    data class PriceEntered(val price: String) : ArticleEvent()



    data class ToggleEditArticleSuccessDialog(val show: Boolean) : ArticleEvent()
    data class ToggleEditArticleBox(val show: Boolean): ArticleEvent()
}