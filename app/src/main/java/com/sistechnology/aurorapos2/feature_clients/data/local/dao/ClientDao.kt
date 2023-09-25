package com.sistechnology.aurorapos2.feature_clients.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sistechnology.aurorapos2.feature_clients.data.local.entities.ClientEntity
import com.sistechnology.aurorapos2.feature_clients.domain.models.Client
import kotlinx.coroutines.flow.Flow
@Dao
interface ClientDao {
    @Query("SELECT * FROM Client")
    fun getAll() : Flow<List<ClientEntity>>

    @Query("SELECT * FROM Client WHERE id = :id")
    suspend fun getById(id: Int) : ClientEntity

    @Query("SELECT * FROM Client WHERE name = :name")
    fun getByName(name: String) : Flow<List<ClientEntity>>

    @Query("SELECT * FROM Client WHERE registrationNumber = :regNumber")
    fun getByRegNumber(regNumber: String) : Flow<List<ClientEntity>>

    @Query("SELECT * FROM Client WHERE vatNumber = :vatNumber")
    fun getByVatNumber(vatNumber: String) : Flow<List<ClientEntity>>

    @Insert(onConflict = REPLACE)
    fun insertClient(vararg clientEntity: ClientEntity)


}