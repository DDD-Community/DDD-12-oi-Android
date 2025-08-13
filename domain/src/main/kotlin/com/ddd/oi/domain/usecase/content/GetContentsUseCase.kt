package com.ddd.oi.domain.usecase.content

import com.ddd.oi.domain.model.Content
import com.ddd.oi.domain.repository.ContentRepository
import javax.inject.Inject

class GetContentsUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) {
    suspend operator fun invoke(userId: Long = 1L): List<Content> {
        return contentRepository.getContents(userId)
    }
}