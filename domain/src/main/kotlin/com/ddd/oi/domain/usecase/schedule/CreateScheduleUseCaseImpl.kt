package com.ddd.oi.domain.usecase.schedule

import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.repository.ScheduleRepository

class CreateScheduleUseCaseImpl(
    private val scheduleRepository: ScheduleRepository
): CreateScheduleUseCase {
    override suspend fun invoke(schedule: Schedule): Result<Schedule> {
        return scheduleRepository.createSchedule(schedule)
    }
}