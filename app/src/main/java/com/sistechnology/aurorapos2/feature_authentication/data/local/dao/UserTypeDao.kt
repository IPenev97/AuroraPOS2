package com.sistechnology.aurorapos2.feature_authentication.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.sistechnology.aurorapos2.feature_authentication.data.local.entities.UserTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserTypeDao {
    @Query("SELECT * FROM UserType")
    fun getAllUserTypes(): Flow<List<UserTypeEntity>>
}