package com.ddd.oi.data.auth.repository

import com.ddd.oi.data.auth.datasource.AuthLocalDataSource
import com.ddd.oi.data.auth.datasource.AuthRemoteDataSource
import com.ddd.oi.data.auth.mapper.toDomain
import com.ddd.oi.data.auth.mapper.toDto
import com.ddd.oi.domain.model.auth.LoginRequest
import com.ddd.oi.domain.model.auth.LoginResponse
import com.ddd.oi.domain.model.social.SocialType
import com.ddd.oi.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
): AuthRepository {
    override suspend fun login(
        type: SocialType,
        request: LoginRequest
    ): Result<LoginResponse> {
        return try {
            val result = authRemoteDataSource.login(type, request.toDto())
            result.fold(
                onSuccess = { loginResponseDto ->
                    authLocalDataSource.saveAuthData(
                        accessToken = loginResponseDto.accessToken,
                        refreshToken = loginResponseDto.refreshToken,
                        userId = loginResponseDto.id,
                        socialType = type
                    )
                    Result.success(loginResponseDto.toDomain())
                },
                onFailure = { exception ->
                    Result.failure(exception)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentLoginType(): Flow<String?> {
        return authLocalDataSource.getCurrentSocialType()
    }

    override fun getUserId(): Flow<Long?> {
        return authLocalDataSource.getUserId()
    }

    override suspend fun logout() {
        authLocalDataSource.logout()
    }
}