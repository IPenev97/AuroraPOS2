package com.sistechnology.aurorapos2.feature_clients.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sistechnology.aurorapos2.feature_clients.domain.use_case.ClientUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ClientsScreenViewModel @Inject constructor(
    val clientUseCases: ClientUseCases
) : ViewModel() {


    private val _clientsState = mutableStateOf(ClientsState())
    val clientsState: State<ClientsState> = _clientsState




    private var getAllClientsJob: Job? = null

    init{
        getAllClients()
    }

    private fun getAllClients(){
        getAllClientsJob?.cancel()
        getAllClientsJob = clientUseCases.getAllClientsUseCase().onEach { clientsList ->
            _clientsState.value = clientsState.value.copy(clientsList = clientsList)
        }.launchIn(viewModelScope)
    }
}