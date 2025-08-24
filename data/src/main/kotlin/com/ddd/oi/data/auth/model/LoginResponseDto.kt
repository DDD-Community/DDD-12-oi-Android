package com.ddd.oi.data.auth.model

import kotlinx.serialization.Serializable


@Serializable
data class LoginResponseDto(
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
