package com.sistechnology.aurorapos2.feature_authentication.domain.use_case.users

import com.sistechnology.aurorapos2.feature_authentication.domain.repositories.UserRepository

class AuthenticateUser(
    private val repository: UserRepository
) {

    suspend operator fun invoke(username: String, password: String): Int{
        return repository.authenticateUser(username, password)
    }

}