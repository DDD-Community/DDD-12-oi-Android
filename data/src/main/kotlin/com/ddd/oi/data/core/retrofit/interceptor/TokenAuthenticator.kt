package com.ddd.oi.data.core.retrofit.interceptor

import com.ddd.oi.data.auth.AuthService
import com.ddd.oi.data.core.datastore.AuthDataStore
import com.ddd.oi.data.core.exception.TokenExpiredException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val authService: AuthService,
    private val authDataStore: AuthDataStore
): Authenticator {

    companion object {
        private const val UNAUTHORIZED = 401
        private const val MAX_RETRY = 2
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }

    private val mutex = Mutex()
    
    override fun authenticate(route: Route?, response: Response): Request? {
        if (!shouldRetry(response)) {
            return null
        }

        val originalToken = response.request.header(AUTHORIZATION)
        
        return try {
            val newAccessToken = refreshToken(originalToken)
            if (newAccessToken != null) {
                response.request.newBuilder()
                    .header(AUTHORIZATION, "$BEARER $newAccessToken")
                    .build()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun refreshToken(originalToken: String?): String? {
        return runBlocking {
            mutex.withLock {
                try {
                    val currentAccessToken = authDataStore.getAccessToken()
                    val currentTokenHeader = "$BEARER $currentAccessToken"

                    if (originalToken != currentTokenHeader && currentAccessToken != null) {
                        // 이미 토큰 갱신되었다고 판단
                        return@withLock currentAccessToken
                    }

                    val refreshToken = authDataStore.getRefreshToken()
                        ?: throw TokenExpiredException()
                    
                    val response = authService.reissue()

                    if (response.data != null) {
                        val newTokens = response.data
                        authDataStore.saveTokens(
                            accessToken = newTokens.accessToken,
                            refreshToken = newTokens.refreshToken
                        )
                        newTokens.accessToken
                    } else {
                        throw TokenExpiredException()
                    }
                } catch (e: Exception) {
                    authDataStore.clear()
                    throw TokenExpiredException()
                }
            }
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }

    private fun shouldRetry(response: Response): Boolean {
        return response.code == UNAUTHORIZED && responseCount(response) < MAX_RETRY
    }
}