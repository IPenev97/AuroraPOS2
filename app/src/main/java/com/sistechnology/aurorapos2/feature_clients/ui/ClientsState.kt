package com.sistechnology.aurorapos2.feature_clients.ui

import com.sistechnology.aurorapos2.feature_clients.domain.models.Client


data class ClientsState(
    val clientsList: List<Client> = emptyList()
) {

}