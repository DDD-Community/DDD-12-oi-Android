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
    
    override suspend fun withdrawUser(): Unit {
        // TODO: OAuth 토큰을 적절한 방법으로 가져와서 전달해야 함
        val oauthToken = "Bearer token_here" // 임시로 하드코딩, 실제로는 토큰 저장소에서 가져와야 함
        return safeApiCall { userApiService.withdrawUser(oauthToken) }.getOrThrow()
    }
}