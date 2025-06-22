package com.ddd.oi.domain.usecase.schedule

interface DeleteScheduleUseCase {
    suspend operator fun invoke(scheduleId: Long): Result<Boolean>
}