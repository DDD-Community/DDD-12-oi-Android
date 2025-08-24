package com.ddd.oi.domain.usecase.faq

import com.ddd.oi.domain.model.FaqPage
import com.ddd.oi.domain.repository.FaqRepository
import javax.inject.Inject

class GetFaqsUseCase @Inject constructor(
    private val faqRepository: FaqRepository
) {
    suspend operator fun invoke(page: Int, size: Int): Result<FaqPage> {
        return faqRepository.getFaqs(page, size)
    }
}