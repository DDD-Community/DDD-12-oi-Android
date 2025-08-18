package com.ddd.oi.data.core.retrofit.api

import com.ddd.oi.data.core.model.BaseResponse
import com.ddd.oi.data.user.model.UserResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApiService {
    
    @GET("api/v1/user")
    suspend fun getUserInfo(
        @Header("user-no") userId: Long = 1L
    ): BaseResponse<UserResponseDto>
}