package com.sistechnology.aurorapos2.feature_authentication.domain.use_case.users

import com.sistechnology.aurorapos2.feature_authentication.data.local.entities.UserTypeEntity
import com.sistechnology.aurorapos2.feature_authentication.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class GetUserTypes (
    private val repository: UserRepository
    ){
    operator fun invoke() : Flow<List<UserTypeEntity>> {
        return repository.getUserTypes()
    }

}