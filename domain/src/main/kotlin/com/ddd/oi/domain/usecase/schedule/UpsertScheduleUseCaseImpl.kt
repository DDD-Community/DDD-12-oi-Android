package com.ddd.oi.domain.usecase.schedule

import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.repository.ScheduleRepository

class UpsertScheduleUseCaseImpl(
    private val scheduleRepository: ScheduleRepository
): UpsertScheduleUseCase {
    override suspend fun invoke(schedule: Schedule): Result<Schedule> {
        return scheduleRepository.upsertSchedule(schedule)
    }
}