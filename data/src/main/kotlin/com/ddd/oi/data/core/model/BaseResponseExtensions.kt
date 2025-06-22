package com.ddd.oi.data.core.model

internal fun <T> BaseResponse<T>.toResult(): Result<T> {
    return when {
        statusCode in 200..299 && data != null -> {
            Result.success(data)
        }
        statusCode in 200..299 && data == null -> {
            Result.failure(Exception("Data is null"))
        }
        else -> {
            Result.failure(Exception(error?.message ?: message))
        }
    }
}

internal suspend fun <T> safeApiCall(apiCall: suspend () -> BaseResponse<T>): Result<T> {
    return runCatching {
        apiCall()
    }.fold(
        onSuccess = { it.toResult() },
        onFailure = { Result.failure(it) }
    )
}
