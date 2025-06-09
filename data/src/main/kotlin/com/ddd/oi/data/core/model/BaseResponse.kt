package com.ddd.oi.data.core.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val statusCode: Int,
    val resultType: String,
    val data: T?,
    val message: String
)