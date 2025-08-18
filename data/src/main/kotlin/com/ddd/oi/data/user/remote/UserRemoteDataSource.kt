package com.ddd.oi.data.user.remote

import com.ddd.oi.data.user.model.UserResponseDto

interface UserRemoteDataSource {
    suspend fun getUserInfo(): UserResponseDto
    suspend fun updateNickname(nickname: String): UserResponseDto
}