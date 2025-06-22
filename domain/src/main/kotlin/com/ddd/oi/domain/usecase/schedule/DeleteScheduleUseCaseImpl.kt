package com.ddd.oi.domain.usecase.schedule

import com.ddd.oi.domain.repository.ScheduleRepository

class DeleteScheduleUseCaseImpl(
    private val scheduleRepository: ScheduleRepository
): DeleteScheduleUseCase {
    override suspend fun invoke(scheduleId: Long): Result<Boolean> {
        return scheduleRepository.deleteSchedule(scheduleId)
    }
}