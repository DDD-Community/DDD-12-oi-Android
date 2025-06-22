package com.ddd.oi.domain.usecase.schedule

import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.repository.ScheduleRepository

class UploadScheduleUseCaseImpl(
    private val scheduleRepository: ScheduleRepository
): UploadScheduleUseCase {
    override suspend fun invoke(schedule: Schedule): Result<Schedule> {
        return scheduleRepository.uploadSchedule(schedule)
    }
}