package com.sistechnology.aurorapos2.feature_clients.domain.repositories

import com.sistechnology.aurorapos2.feature_clients.domain.models.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {

    //Get
    fun getAllClients() : Flow<List<Client>>
    suspend fun getClientById(id: Int) : Client
    fun getClientByName(name: String) : Flow<List<Client>>
    fun getClientByRegNumber(regNumber: String) : Flow<List<Client>>
    fun getClientByVatNumber(vatNumber: String) : Flow<List<Client>>

    //Add
    fun addClient(vararg: Client)
    fun editClient(client: Client)
}