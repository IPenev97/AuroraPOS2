package com.sistechnology.aurorapos2.feature_clients.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Client")
data class ClientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "registrationNumber")
    val registrationNumber: String,
    @ColumnInfo(name = "vatNumber")
    val vatNumber: String,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "phone")
    val phone: String,
)
