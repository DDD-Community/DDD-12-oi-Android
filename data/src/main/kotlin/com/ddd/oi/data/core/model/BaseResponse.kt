package com.ddd.oi.data.core.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val statusCode: Int,
    val resultType: String,
    val data: T? = null,
    val message: String,
    val error: ErrorResponse? = null
)

@Serializable
data class ErrorResponse(
    val message: String,
    val details: List<String> = emptyList()
)

@Serializable
data object EmptyResponse