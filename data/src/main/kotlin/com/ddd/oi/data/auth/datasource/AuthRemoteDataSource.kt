package com.ddd.oi.data.auth.datasource

import com.ddd.oi.data.auth.model.LoginRequestDto
import com.ddd.oi.data.auth.model.LoginResponseDto
import com.ddd.oi.domain.model.social.SocialType

interface AuthRemoteDataSource {
    suspend fun login(type: SocialType, loginRequestDto: LoginRequestDto): Result<LoginResponseDto>
}