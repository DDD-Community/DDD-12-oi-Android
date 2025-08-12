package com.ddd.oi.domain.usecase.auth

import com.ddd.oi.domain.model.auth.LoginRequest
import com.ddd.oi.domain.model.auth.LoginResponse
import com.ddd.oi.domain.model.social.SocialType
import com.ddd.oi.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
): LoginUseCase {
    override suspend fun invoke(
        type: SocialType,
        request: LoginRequest
    ): Result<LoginResponse> {
        return authRepository.login(type, request)
    }
}