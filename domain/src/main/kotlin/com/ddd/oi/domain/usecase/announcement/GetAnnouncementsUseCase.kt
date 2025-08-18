package com.ddd.oi.domain.usecase.announcement

import com.ddd.oi.domain.model.AnnouncementPage
import com.ddd.oi.domain.repository.AnnouncementRepository
import javax.inject.Inject

class GetAnnouncementsUseCase @Inject constructor(
    private val announcementRepository: AnnouncementRepository
) {
    suspend operator fun invoke(pageNumber: Int, pageSize: Int): Result<AnnouncementPage> {
        return announcementRepository.getNotices(pageNumber, pageSize)
    }
}