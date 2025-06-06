package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun readUser(): Flow<User>
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
}