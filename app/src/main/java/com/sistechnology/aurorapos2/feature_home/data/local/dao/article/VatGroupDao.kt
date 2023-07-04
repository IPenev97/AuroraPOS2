package com.sistechnology.aurorapos2.feature_home.data.local.dao.article

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sistechnology.aurorapos2.feature_home.data.local.entities.article.VatGroupEntity
import com.sistechnology.aurorapos2.feature_home.domain.models.article.VatGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface VatGroupDao {
    @Query("SELECT * FROM Vat")
    fun gelAll() : Flow<List<VatGroupEntity>>

    @Query("SELECT * FROM Vat WHERE id = :id")
    suspend fun getById(id: Int) : VatGroupEntity

    @Insert(onConflict = REPLACE)
    suspend fun insertVatGroup(vararg vatGroup: VatGroupEntity)
}