package com.sistechnology.aurorapos2.feature_home.data.local.entities.article

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Vat")
data class VatGroupEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "group")
    val group: String,
    @ColumnInfo(name = "value")
    val value: Double
) {
}