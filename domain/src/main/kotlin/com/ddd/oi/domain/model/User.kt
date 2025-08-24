package com.ddd.oi.domain.model

data class User(
    val id: Long,
    val token: String,
    val name: String? = null,
    val email: String? = null,
    val providerInfo: String? = null
)
