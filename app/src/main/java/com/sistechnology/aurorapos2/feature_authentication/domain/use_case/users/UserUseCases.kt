package com.sistechnology.aurorapos2.feature_authentication.domain.use_case.users

data class UserUseCases(
    val getUser: GetUser,
    val getUsers: GetUsers,
    val getUserTypes: GetUserTypes,
    val authenticateUser: AuthenticateUser
)
