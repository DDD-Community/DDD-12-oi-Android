package com.ddd.oi.domain.usecase.scheduledetail

import com.ddd.oi.domain.model.schedule.SchedulePlace
import com.ddd.oi.domain.repository.ScheduleDetailRepository

class UpdateScheduleDetailUseCaseImpl(
    private val scheduleDetailRepository: ScheduleDetailRepository
): UpdateScheduleDetailUseCase {
    override suspend fun invoke(
        scheduleId: Long,
        scheduleDetail: SchedulePlace
    ): Result<SchedulePlace> {
        return scheduleDetailRepository.updateScheduleDetail(scheduleId, scheduleDetail)
    }
}