package com.sistechnology.aurorapos2.feature_home.domain.use_case.articles

import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetFavouriteArticlesUseCase(
    val articleRepository: ArticleRepository
) {
    operator fun invoke() : Flow<List<Article>>{
        return articleRepository.getFavouriteArticles()
    }
}