package com.sistechnology.aurorapos2.feature_clients.domain.models

data class Client(
    val id: Int = 0,
    val name: String,
    val registrationNumber: String,
    val vatNumber: String,
    val address: String,
    val phone: String,



) {
}