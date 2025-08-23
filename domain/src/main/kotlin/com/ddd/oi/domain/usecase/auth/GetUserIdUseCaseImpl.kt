package com.ddd.oi.domain.usecase.auth

import com.ddd.oi.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserIdUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
): GetUserIdUseCase {
    override fun invoke(): Flow<Long?> {
        return authRepository.getUserId()
    }
}