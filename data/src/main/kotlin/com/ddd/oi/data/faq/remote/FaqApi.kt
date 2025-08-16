package com.ddd.oi.data.faq.remote

import com.ddd.oi.data.core.model.BaseResponse
import com.ddd.oi.data.faq.model.FaqPageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FaqApi {
    @GET("api/v1/faqs")
    suspend fun getFaqs(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<FaqPageDto>
}