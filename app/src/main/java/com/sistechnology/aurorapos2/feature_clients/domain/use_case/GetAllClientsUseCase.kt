package com.sistechnology.aurorapos2.feature_clients.domain.use_case


import com.sistechnology.aurorapos2.feature_clients.domain.models.Client
import com.sistechnology.aurorapos2.feature_clients.domain.repositories.ClientRepository
import kotlinx.coroutines.flow.Flow

class GetAllClientsUseCase(
    private val clientsRepository: ClientRepository

) {

    operator fun invoke(): Flow<List<Client>> {
        return clientsRepository.getAllClients()
    }
}