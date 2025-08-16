package com.ddd.oi.data.faq

import com.ddd.oi.data.faq.mapper.toDomain
import com.ddd.oi.data.faq.remote.FaqRemoteDataSource
import com.ddd.oi.domain.model.FaqPage
import com.ddd.oi.domain.repository.FaqRepository
import javax.inject.Inject

class FaqRepositoryImpl @Inject constructor(
    private val faqRemoteDataSource: FaqRemoteDataSource
) : FaqRepository {
    override suspend fun getFaqs(page: Int, size: Int): Result<FaqPage> {
        return faqRemoteDataSource.getFaqs(page, size)
            .mapCatching { it.toDomain() }
    }
}