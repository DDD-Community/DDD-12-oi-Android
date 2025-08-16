package com.ddd.oi.data.faq.remote

import com.ddd.oi.data.faq.model.FaqPageDto

interface FaqRemoteDataSource {
    suspend fun getFaqs(page: Int, size: Int): Result<FaqPageDto>
}