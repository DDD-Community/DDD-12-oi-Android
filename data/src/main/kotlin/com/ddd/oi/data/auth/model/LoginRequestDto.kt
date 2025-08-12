package com.ddd.oi.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val oauthAccessToken: String
)