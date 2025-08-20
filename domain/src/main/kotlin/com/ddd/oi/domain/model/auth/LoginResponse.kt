package com.ddd.oi.domain.model.auth

data class LoginResponse(
    val id: Long,
    val nickname: String,
    val email: String,
    val providerInfo: String,
    val role: String,
    val accessToken: String,
    val refreshToken: String,
    val oauthAccessToken: String,
    val lastReadAt: String? = null,
)