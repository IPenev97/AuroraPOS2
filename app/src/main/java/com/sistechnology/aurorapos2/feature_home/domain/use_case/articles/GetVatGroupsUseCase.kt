package com.sistechnology.aurorapos2.feature_home.domain.use_case.articles

import com.sistechnology.aurorapos2.feature_home.domain.models.article.VatGroup
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetVatGroupsUseCase(
    private val articleRepository: ArticleRepository
) {
    operator fun invoke(): Flow<List<VatGroup>>{
        return articleRepository.getAllVatGroups()
    }
}