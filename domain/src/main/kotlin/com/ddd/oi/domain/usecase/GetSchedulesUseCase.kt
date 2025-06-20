package com.ddd.oi.domain.usecase

import com.ddd.oi.domain.model.schedule.ScheduleResult

interface GetSchedulesUseCase {
    suspend operator fun invoke(year: Int, month: Int): Result<ScheduleResult>
}