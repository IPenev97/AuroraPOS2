package com.sistechnology.aurorapos2.feature_home.domain.use_case.articles

import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleGroup
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetArticleGroupsUseCase(
    private val articleRepository: ArticleRepository
) {
     operator fun invoke() : Flow<List<ArticleGroup>>{
        return articleRepository.getAllArticleGroups()
    }
}