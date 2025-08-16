package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.FaqPage

interface FaqRepository {
    suspend fun getFaqs(page: Int, size: Int): Result<FaqPage>
}