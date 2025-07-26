package com.ddd.oi.domain.usecase.scheduledetail

import com.ddd.oi.domain.model.schedule.SchedulePlace
import com.ddd.oi.domain.repository.ScheduleDetailRepository

class GetScheduleDetailsUseCaseImpl(
    private val scheduleDetailRepository: ScheduleDetailRepository
): GetScheduleDetailsUseCase {
    override suspend fun invoke(scheduleId: Long): Result<Map<String, List<SchedulePlace>>> {
        return scheduleDetailRepository.getScheduleDetail(scheduleId)
    }
}