package com.sistechnology.aurorapos2.feature_authentication.domain.models

data class User (
    val id : Int? = null,
    val username: String,
    val password: String,
    val fullName : String? = "Default",
    val firstName : String? = "Default",
    val lastName : String? = "Default"

)