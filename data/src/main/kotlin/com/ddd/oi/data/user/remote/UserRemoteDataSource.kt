package com.ddd.oi.data.user.remote

import com.ddd.oi.data.user.model.UserResponseDto

interface UserRemoteDataSource {
    suspend fun getUserInfo(): UserResponseDto
}