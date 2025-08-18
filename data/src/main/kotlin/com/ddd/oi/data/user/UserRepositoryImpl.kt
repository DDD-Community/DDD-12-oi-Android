package com.ddd.oi.data.user

import com.ddd.oi.data.user.local.UserLocalDataSource
import com.ddd.oi.data.user.mapper.toDomain
import com.ddd.oi.data.user.remote.UserRemoteDataSource
import com.ddd.oi.domain.model.SystemInfo
import com.ddd.oi.domain.model.User
import com.ddd.oi.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    
    private var cachedUserInfo: User? = null
    private var cachedSystemInfo: SystemInfo? = null
    
    override suspend fun readUser(): Flow<User> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }
    
    override suspend fun getUserInfo(): User {
        if (cachedUserInfo == null) {
            val response = userRemoteDataSource.getUserInfo().toDomain()
            cachedUserInfo = response.user
            cachedSystemInfo = response.systemInfo
        }
        return cachedUserInfo!!
    }
    
    override suspend fun getSystemInfo(): SystemInfo {
        if (cachedSystemInfo == null) {
            val response = userRemoteDataSource.getUserInfo().toDomain()
            cachedUserInfo = response.user
            cachedSystemInfo = response.systemInfo
        }
        return cachedSystemInfo!!
    }
    
    override suspend fun updateNickname(nickname: String): User {
        val response = userRemoteDataSource.updateNickname(nickname).toDomain()
        cachedUserInfo = response.user
        cachedSystemInfo = response.systemInfo
        return response.user
    }
}