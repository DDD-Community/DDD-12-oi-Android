package com.ddd.oi.data.auth

import com.ddd.oi.data.auth.model.LoginRequestDto
import com.ddd.oi.data.auth.model.LoginResponseDto
import com.ddd.oi.data.core.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("api/v1/auth/login/{provider}")
    suspend fun login(
        @Path("provider") provider: String,
        @Body request: LoginRequestDto
    ): BaseResponse<LoginResponseDto>
}