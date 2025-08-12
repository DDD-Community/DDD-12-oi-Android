package com.ddd.oi.data.auth.datasourceImpl

import com.ddd.oi.data.auth.AuthService
import com.ddd.oi.data.auth.datasource.AuthRemoteDataSource
import com.ddd.oi.data.auth.model.LoginRequestDto
import com.ddd.oi.data.auth.model.LoginResponseDto
import com.ddd.oi.data.core.model.safeApiCall
import com.ddd.oi.domain.model.social.SocialType
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun login(
        type: SocialType,
        loginRequestDto: LoginRequestDto
    ): Result<LoginResponseDto> {
        return safeApiCall {
            authService.login(type.name.lowercase(), loginRequestDto)
        }
    }
}