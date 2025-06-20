package com.ddd.oi.data.core.model

internal fun <T> BaseResponse<T>.toResult(): Result<T> {
    return when {
        statusCode in 200..299 && data != null -> {
            Result.success(data)
        }
        statusCode in 200..299 && data == null -> {
            Result.failure(Exception())
        }
        else -> {
            Result.failure(Exception())
        }
    }
}
