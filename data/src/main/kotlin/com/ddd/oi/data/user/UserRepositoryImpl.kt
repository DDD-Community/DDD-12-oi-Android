package com.ddd.oi.data.user

import com.ddd.oi.data.user.local.UserLocalDataSource
import com.ddd.oi.data.user.remote.UserRemoteDataSource
import com.ddd.oi.domain.model.User
import com.ddd.oi.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override suspend fun readUser(): Flow<User> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }
}