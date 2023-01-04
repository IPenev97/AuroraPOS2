package com.sistechnology.aurorapos2.feature_authentication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sistechnology.aurorapos2.feature_authentication.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM User ORDER BY id ASC")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getById(id : Int) : UserEntity?

    @Insert(onConflict = REPLACE)
    suspend fun insertUser(vararg user: UserEntity)

    @Query("SELECT id FROM User WHERE username = :username AND password = :password")
    suspend fun authenticateUser(username: String, password: String) : Int?

}