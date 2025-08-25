package com.ddd.oi.domain.usecase.scheduledetail

import com.ddd.oi.domain.model.schedule.SchedulePlace

interface UpdateScheduleDetailUseCase {
    suspend operator fun invoke(scheduleId: Long, scheduleDetail: SchedulePlace): Result<SchedulePlace>
}