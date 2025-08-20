package com.ddd.oi.domain.usecase.auth

import com.ddd.oi.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
): LogoutUseCase {
    override suspend fun invoke() {
        authRepository.logout()
    }
}