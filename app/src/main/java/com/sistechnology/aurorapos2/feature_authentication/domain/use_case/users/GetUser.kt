package com.sistechnology.aurorapos2.feature_authentication.domain.use_case.users

import com.sistechnology.aurorapos2.feature_authentication.domain.models.User
import com.sistechnology.aurorapos2.feature_authentication.domain.repositories.UserRepository

class GetUser(
    private val repository: UserRepository
)
{
    suspend operator fun invoke(id : Int) : User?{
        return repository.getUserById(id)
    }
}