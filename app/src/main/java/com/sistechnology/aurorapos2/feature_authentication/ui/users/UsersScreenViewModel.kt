package com.sistechnology.aurorapos2.feature_authentication.ui.users

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sistechnology.aurorapos2.core.ui.Screen
import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_authentication.domain.use_case.users.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersScreenViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val sharedPrefsHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _state = mutableStateOf(UserState())
    val state: State<UserState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getUsersJob: Job? = null

    init {
        getUsers()
    }



    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.SelectUser -> {
                _state.value = state.value.copy(selectedUser = event.user)
            }
            is UserEvent.Login -> {
                viewModelScope.launch {
                    if (event.password.isEmpty()) {
                        _state.value = state.value.copy(
                            showDialog = true,
                            dialogMessage = "Please enter a password"
                        )
                    } else {
                        val userId =
                            userUseCases.authenticateUser(event.username, event.password)
                        if (userId!=-1) {
                            sharedPrefsHelper.setCurrentUserName(event.username)
                            sharedPrefsHelper.setCurrentUserId(userId)
                            _eventFlow.emit(UiEvent.Navigate(Screen.HomeScreen.route))
                        } else {
                            _state.value = state.value.copy(
                                showDialog = true,
                                dialogMessage = "Incorrect Password"
                            )
                        }
                    }
                }
            }
            is UserEvent.ToggleDialog -> {
                _state.value = state.value.copy(showDialog = event.visible)
            }
            is UserEvent.ToggleLogo -> {
                _state.value = state.value.copy(logoVisibility = event.visible)
            }

        }

    }

    private fun getUsers() {

        getUsersJob?.cancel()
        getUsersJob = userUseCases.getUsers()
            .onEach { users ->
                _state.value = state.value.copy(
                    userList = users
                )
            }
            .launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class Navigate(val route: String) : UiEvent()
    }


}