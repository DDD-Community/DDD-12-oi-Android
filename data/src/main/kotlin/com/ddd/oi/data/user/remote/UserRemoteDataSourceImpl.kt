package com.ddd.oi.data.user.remote

import com.ddd.oi.data.core.model.safeApiCall
import com.ddd.oi.data.core.retrofit.api.UserApiService
import com.ddd.oi.data.user.model.UserResponseDto
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserRemoteDataSource {
    
    override suspend fun getUserInfo(): UserResponseDto {
        return safeApiCall { userApiService.getUserInfo() }.getOrThrow()
    }
    
    override suspend fun updateNickname(nickname: String): UserResponseDto {
        return safeApiCall { userApiService.updateNickname(nickname = nickname) }.getOrThrow()
    }
}