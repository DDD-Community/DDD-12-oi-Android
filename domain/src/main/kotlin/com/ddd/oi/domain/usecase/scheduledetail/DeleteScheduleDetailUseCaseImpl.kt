package com.ddd.oi.domain.usecase.scheduledetail

import com.ddd.oi.domain.repository.ScheduleDetailRepository

class DeleteScheduleDetailUseCaseImpl(
    private val scheduleDetailRepository: ScheduleDetailRepository
): DeleteScheduleDetailUseCase{
    override suspend fun invoke(
        scheduleId: Long,
        scheduleDetailId: Long
    ): Result<Boolean> {
        return scheduleDetailRepository.deleteScheduleDetail(scheduleId, scheduleDetailId)
    }
}