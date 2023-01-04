package com.sistechnology.aurorapos2.feature_home.ui.articles

import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article

sealed class ArticleEvent {
    data class SelectArticleGroup(val id: Int): ArticleEvent()
    data class AddToReceipt(val article: Article) : ArticleEvent()
    data class SelectArticle(val article: Article) : ArticleEvent()
}