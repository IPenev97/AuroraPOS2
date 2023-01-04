package com.sistechnology.aurorapos2.feature_authentication.ui.users

import com.sistechnology.aurorapos2.feature_authentication.domain.models.User

sealed class UserEvent {
    data class SelectUser(val user: User? = null): UserEvent()
    data class Login(val username: String, val password: String) : UserEvent()
    data class ToggleDialog(val visible: Boolean) : UserEvent()
    data class ToggleLogo(val visible: Boolean) : UserEvent()
}
