package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.auth.LoginRequest
import com.ddd.oi.domain.model.auth.LoginResponse
import com.ddd.oi.domain.model.social.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(type: SocialType, accessToken: LoginRequest): Result<LoginResponse>
    fun getCurrentLoginType(): Flow<String?>
    fun getUserId() :Flow<Long?>
    suspend fun logout()
}