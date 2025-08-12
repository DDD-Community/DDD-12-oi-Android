package com.ddd.oi.data.auth.datasource

import com.ddd.oi.domain.model.social.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    suspend fun getAccessToken(): String?
    fun getCurrentSocialType(): Flow<String?>
    fun getUserId(): Flow<Long?>
    suspend fun saveAuthData(accessToken: String, refreshToken: String, userId: Long, socialType: SocialType)
}