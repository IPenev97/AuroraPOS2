package com.sistechnology.aurorapos2.feature_authentication.data.repositories

import com.sistechnology.aurorapos2.feature_authentication.data.local.dao.UserDao
import com.sistechnology.aurorapos2.feature_authentication.data.local.dao.UserTypeDao
import com.sistechnology.aurorapos2.feature_authentication.data.local.entities.UserEntity
import com.sistechnology.aurorapos2.feature_authentication.data.local.entities.UserTypeEntity
import com.sistechnology.aurorapos2.feature_authentication.domain.models.User
import com.sistechnology.aurorapos2.feature_authentication.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl (
    private val userDao: UserDao,
    private val userTypeDao: UserTypeDao
) : UserRepository {
    fun UserEntity.toModel(): User {
        return User(
            this.id,
            this.username,
            this.password,
            this.fullName,
            firstName = "default",
            lastName = "default"

        )
    }

    fun User.toEntity(): UserEntity {
        return UserEntity(
            id = this.id?:0,
            username = this.username,
            password = this.password,
            fullName = this.fullName
        )
    }

    override fun getUsers(): Flow<List<User>> {

        return userDao.getAll().map{ list -> list.map { it.toModel() }}
    }

    override suspend fun getUserById(id: Int): User? {
        return userDao.getById(id)?.toModel()
    }

    override fun getUserTypes(): Flow<List<UserTypeEntity>> {
        return userTypeDao.getAllUserTypes()
    }

    override suspend fun addUser(vararg user: User) {
        return userDao.insertUser(*user.map{it.toEntity()}.toTypedArray())
    }

    override suspend fun authenticateUser(username: String, password: String): Int {
        return userDao.authenticateUser(username, password)?:-1
    }


}