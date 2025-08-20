package com.ddd.oi.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class ReissueResponseDto(
    val accessToken: String,
    val refreshToken: String
)