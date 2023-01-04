package com.sistechnology.aurorapos2.feature_authentication.ui.users

import com.sistechnology.aurorapos2.feature_authentication.domain.models.User

data class UserState(
    val userList : List<User> = emptyList(),
    val selectedUser: User? = null,
    val logoVisibility: Boolean = true,
    val showDialog: Boolean = false,
    val dialogMessage: String = ""
)
