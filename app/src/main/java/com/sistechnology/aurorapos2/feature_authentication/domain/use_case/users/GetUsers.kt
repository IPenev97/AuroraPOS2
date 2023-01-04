package com.sistechnology.aurorapos2.feature_authentication.domain.use_case.users

import com.sistechnology.aurorapos2.feature_authentication.domain.models.User
import com.sistechnology.aurorapos2.feature_authentication.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class GetUsers(
    private val repository: UserRepository
)
{
    operator fun invoke() : Flow<List<User>> {
        return repository.getUsers()
    }
}