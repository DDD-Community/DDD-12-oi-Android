package com.ddd.oi.domain.usecase.scheduledetail

import com.ddd.oi.domain.model.schedule.SchedulePlace

interface GetScheduleDetailsUseCase {
    suspend operator fun invoke(scheduleId: Long): Result<Map<String, List<SchedulePlace>>>
}