package com.ddd.oi.domain.usecase.scheduledetail

interface DeleteScheduleDetailUseCase {
    suspend operator fun invoke(scheduleId: Long, scheduleDetailId: Long): Result<Boolean>
}