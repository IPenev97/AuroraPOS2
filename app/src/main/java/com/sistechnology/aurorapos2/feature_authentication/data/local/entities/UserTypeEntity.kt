package com.sistechnology.aurorapos2.feature_authentication.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserType")
data class UserTypeEntity(
    @PrimaryKey
    val id : Int,
    @ColumnInfo(name = "name")
    val name : String
) {

}