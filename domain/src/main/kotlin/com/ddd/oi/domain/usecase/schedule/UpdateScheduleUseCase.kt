package com.ddd.oi.domain.usecase.schedule

import com.ddd.oi.domain.model.schedule.Schedule

interface UpdateScheduleUseCase {
    suspend operator fun invoke(schedule: Schedule): Result<Schedule>
}