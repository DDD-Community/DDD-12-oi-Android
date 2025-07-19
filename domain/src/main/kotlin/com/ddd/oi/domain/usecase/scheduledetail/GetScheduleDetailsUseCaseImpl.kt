package com.ddd.oi.domain.usecase.scheduledetail

import com.ddd.oi.domain.model.schedule.Place
import com.ddd.oi.domain.repository.ScheduleDetailRepository

class GetScheduleDetailsUseCaseImpl(
    private val scheduleDetailRepository: ScheduleDetailRepository
): GetScheduleDetailsUseCase {
    override suspend fun invoke(scheduleId: Long): Result<Map<String, List<Place>>> {
        return scheduleDetailRepository.getScheduleDetail(scheduleId)
    }
}