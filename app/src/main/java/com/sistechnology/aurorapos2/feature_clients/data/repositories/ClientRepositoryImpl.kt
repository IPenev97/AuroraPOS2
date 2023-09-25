package com.sistechnology.aurorapos2.feature_clients.data.repositories

import com.sistechnology.aurorapos2.feature_clients.data.local.dao.ClientDao
import com.sistechnology.aurorapos2.feature_clients.data.local.entities.ClientEntity
import com.sistechnology.aurorapos2.feature_clients.domain.models.Client
import com.sistechnology.aurorapos2.feature_clients.domain.repositories.ClientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClientRepositoryImpl(
    private val clientDao: ClientDao
) : ClientRepository {

    private fun Client.toEntity() : ClientEntity {
        return ClientEntity(
            this.id,
        this.name,
        this.registrationNumber,
        this.vatNumber,
        this.address,
        this.phone,
        )
    }

    private fun ClientEntity.toModel() : Client{
        return Client(
            this.id,
            this.name,
            this.registrationNumber,
            this.vatNumber,
            this.address,
            this.phone,
        )
    }

    override fun getAllClients(): Flow<List<Client>> {
        return clientDao.getAll().map{ list -> list.map{it.toModel()}}
    }

    override suspend fun getClientById(id: Int): Client {
        TODO("Not yet implemented")
    }

    override fun getClientByName(name: String): Flow<List<Client>> {
        TODO("Not yet implemented")
    }

    override fun getClientByRegNumber(regNumber: String): Flow<List<Client>> {
        TODO("Not yet implemented")
    }

    override fun getClientByVatNumber(vatNumber: String): Flow<List<Client>> {
        TODO("Not yet implemented")
    }

    override fun addClient(vararg: Client) {
        TODO("Not yet implemented")
    }

    override fun editClient(client: Client) {
        TODO("Not yet implemented")
    }
}