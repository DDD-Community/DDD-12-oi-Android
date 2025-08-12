package com.ddd.oi.data.auth.datasourceImpl

import com.ddd.oi.data.auth.datasource.AuthLocalDataSource
import com.ddd.oi.data.core.datastore.AuthDataStore
import com.ddd.oi.domain.model.social.SocialType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val authDataStore: AuthDataStore
) : AuthLocalDataSource {
    override suspend fun getAccessToken(): String? {
        return authDataStore.getAccessToken()
    }

    override fun getCurrentSocialType(): Flow<String?> {
        return authDataStore.getCurrentLoginType()
    }

    override fun getUserId(): Flow<Long?> {
        return authDataStore.getUserId()
    }

    override suspend fun saveAuthData(
        accessToken: String,
        refreshToken: String,
        userId: Long,
        socialType: SocialType
    ) {
        authDataStore.saveAuthData(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userId = userId,
            socialType = socialType
        )
    }
}