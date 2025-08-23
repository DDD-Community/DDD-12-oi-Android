package com.ddd.oi.data.auth

import com.ddd.oi.data.auth.model.LoginRequestDto
import com.ddd.oi.data.auth.model.LoginResponseDto
import com.ddd.oi.data.auth.model.ReissueResponseDto
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

    // 예원님한테 reissue BodyRequest 물어보기 (스웨거에 없음)
    @POST("api/v1/auth/reissue")
    suspend fun reissue(): BaseResponse<ReissueResponseDto>
}