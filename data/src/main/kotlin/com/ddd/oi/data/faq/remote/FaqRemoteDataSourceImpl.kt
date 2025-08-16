package com.ddd.oi.data.faq.remote

import com.ddd.oi.data.core.model.safeApiCall
import com.ddd.oi.data.faq.model.FaqPageDto
import javax.inject.Inject

class FaqRemoteDataSourceImpl @Inject constructor(
    private val faqApi: FaqApi
) : FaqRemoteDataSource {
    override suspend fun getFaqs(page: Int, size: Int): Result<FaqPageDto> {
        return safeApiCall {
            faqApi.getFaqs(page, size)
        }
    }
}