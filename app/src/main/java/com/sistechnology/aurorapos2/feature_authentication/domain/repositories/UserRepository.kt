package com.sistechnology.aurorapos2.feature_authentication.domain.repositories

import com.sistechnology.aurorapos2.feature_authentication.data.local.entities.UserEntity
import com.sistechnology.aurorapos2.feature_authentication.data.local.entities.UserTypeEntity
import com.sistechnology.aurorapos2.feature_authentication.domain.models.User
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun getUsers(): Flow<List<User>>
    suspend fun getUserById(id : Int): User?
    fun getUserTypes() : Flow<List<UserTypeEntity>>
    suspend fun addUser(vararg user: User)
    suspend fun authenticateUser(username: String, password: String) : Int
}