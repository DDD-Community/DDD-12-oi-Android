package com.ddd.oi.domain.usecase.schedule

import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.repository.ScheduleRepository

class UpdateScheduleUseCaseImpl(
    private val scheduleRepository: ScheduleRepository
): UpdateScheduleUseCase {
    override suspend fun invoke(schedule: Schedule): Result<Schedule> {
        return scheduleRepository.updateSchedule(schedule)
    }
}