package com.ddd.oi.domain.usecase.auth

import com.ddd.oi.domain.model.auth.LoginRequest
import com.ddd.oi.domain.model.auth.LoginResponse
import com.ddd.oi.domain.model.social.SocialType

interface LoginUseCase {
    suspend operator fun invoke(type: SocialType, request: LoginRequest): Result<LoginResponse>
}