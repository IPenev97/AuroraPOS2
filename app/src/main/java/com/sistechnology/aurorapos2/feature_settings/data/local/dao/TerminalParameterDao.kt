package com.sistechnology.aurorapos2.feature_settings.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sistechnology.aurorapos2.feature_settings.data.local.entities.TerminalParameterEntity


@Dao
interface TerminalParameterDao {
    @Query("SELECT * FROM TerminalParameter WHERE id = 1")
    suspend fun getTerminalParameter() : TerminalParameterEntity

    @Insert(onConflict = REPLACE)
    suspend fun editTerminalParameter(printingDeviceInfo: TerminalParameterEntity)
}