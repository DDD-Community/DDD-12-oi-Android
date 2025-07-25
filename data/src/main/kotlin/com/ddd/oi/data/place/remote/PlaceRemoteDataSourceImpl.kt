package com.ddd.oi.data.place.remote

import com.ddd.oi.data.core.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class PlaceRemoteDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val api: PlaceApi
) : PlaceRemoteDataSource {
    companion object {
        private const val DEFAULT_DISPLAY = 5
        private const val DEFAULT_START = 1
        private const val DEFAULT_SORT = "random"
    }

    override suspend fun queryPlace(query: String): PlaceSearchResponse =
        withContext(ioDispatcher) {
            api.getPlace(
                query = query,
                display = DEFAULT_DISPLAY,
                start = DEFAULT_START,
                sort = DEFAULT_SORT
            ).run {
                if (isSuccessful) {
                    body() ?: throw IllegalArgumentException("Body is null.")
                } else {
                    throw HttpException(this)
                }
            }
        }
}