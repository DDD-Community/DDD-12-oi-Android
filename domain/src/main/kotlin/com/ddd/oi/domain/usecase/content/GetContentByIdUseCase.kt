package com.ddd.oi.domain.usecase.content

import com.ddd.oi.domain.model.Content
import com.ddd.oi.domain.repository.ContentRepository
import javax.inject.Inject

class GetContentByIdUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) {
    suspend operator fun invoke(contentId: Long): Result<Content> {
        return try {
            val result = contentRepository.getContent(contentId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}