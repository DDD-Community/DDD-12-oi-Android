package com.ddd.oi.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class ReissueRequestDto(
    val refreshToken: String
)