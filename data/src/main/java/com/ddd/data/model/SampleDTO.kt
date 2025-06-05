package com.ddd.data.model

import com.ddd.domain.model.Sample
import kotlinx.serialization.Serializable

@Serializable
data class SampleDTO(
    val refreshToken: String,
    val accessToken: String,
) {
    fun toDomain(): Sample {
        return Sample(refreshToken, accessToken)
    }
}