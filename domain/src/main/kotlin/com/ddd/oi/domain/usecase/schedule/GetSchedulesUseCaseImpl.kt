package com.ddd.oi.domain.usecase.schedule

import com.ddd.oi.domain.repository.ScheduleRepository
import com.ddd.oi.domain.model.schedule.ScheduleResult
import com.ddd.oi.domain.model.schedule.groupByDate

class GetSchedulesUseCaseImpl(
    private val scheduleRepository: ScheduleRepository
): GetSchedulesUseCase {

    override suspend fun invoke(year: Int, month: Int): Result<ScheduleResult> {
        return scheduleRepository.getScheduleList(year, month)
            .map { schedules ->
                ScheduleResult(
                    schedules = schedules.groupByDate(),
                    availableCategory = schedules.map { it.category }.toSet()
                )
            }
    }
}