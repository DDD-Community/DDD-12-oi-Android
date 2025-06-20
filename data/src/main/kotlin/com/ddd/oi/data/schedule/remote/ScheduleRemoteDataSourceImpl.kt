package com.ddd.oi.data.schedule.remote

import com.ddd.oi.data.schedule.model.ScheduleDto
import com.ddd.oi.data.core.model.toResult
import com.ddd.oi.data.schedule.model.ScheduleRequestDto
import javax.inject.Inject

class ScheduleRemoteDataSourceImpl @Inject constructor(
    private val scheduleApiService: ScheduleApiService
): ScheduleRemoteDataSource {
    override suspend fun getScheduleList(year: Int, month: Int): Result<List<ScheduleDto>> {
        return scheduleApiService.getSchedules(year = year, month = month).toResult()
    }

    override suspend fun uploadSchedule(schedule: ScheduleRequestDto): Result<ScheduleDto> {
        return scheduleApiService.uploadSchedule(request = schedule).toResult()
    }
}