package com.ddd.oi.presentation.login.social

import android.content.Context

interface AuthHandler {
    suspend fun loginWithSocial(context: Context): SignInResult
    suspend fun logout(): Boolean
}

sealed interface SignInResult {
    data class Success(val accessToken: String) : SignInResult
    data class Failure(val throwable: Throwable) : SignInResult
}