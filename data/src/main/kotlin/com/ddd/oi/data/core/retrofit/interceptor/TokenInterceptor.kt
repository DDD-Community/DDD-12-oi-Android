package com.ddd.oi.data.core.retrofit.interceptor

import com.ddd.oi.data.core.datastore.AuthDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val authDataStore: AuthDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = runBlocking { authDataStore.getAccessToken() }

        if (accessToken.isNullOrEmpty()) {
            return chain.proceed(originalRequest)
        }
        val request = chain.request().newBuilder().apply {
            header(AUTH_HEADER, "$BEARER $accessToken")
        }.build()
        return chain.proceed(request)
    }

    companion object {
        const val AUTH_HEADER = "Authorization"
        const val BEARER = "Bearer"
    }
}