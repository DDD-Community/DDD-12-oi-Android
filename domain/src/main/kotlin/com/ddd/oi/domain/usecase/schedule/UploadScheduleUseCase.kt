package com.ddd.oi.domain.usecase.schedule

import com.ddd.oi.domain.model.schedule.Schedule

interface UploadScheduleUseCase {
    suspend fun invoke(schedule: Schedule): Result<Schedule>
}