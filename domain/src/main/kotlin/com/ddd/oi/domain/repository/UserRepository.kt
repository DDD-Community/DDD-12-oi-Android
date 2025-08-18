package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.SystemInfo
import com.ddd.oi.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun readUser(): Flow<User>
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun getUserInfo(): User
    suspend fun getSystemInfo(): SystemInfo
    suspend fun updateNickname(nickname: String): User
}