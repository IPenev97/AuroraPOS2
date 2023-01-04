package com.sistechnology.aurorapos2.feature_authentication.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @ColumnInfo(name = "fullName")
    val fullName : String? = "Default",
    @ColumnInfo(name = "password")
    val password : String,
    @ColumnInfo(name = "username")
    val username : String

)

