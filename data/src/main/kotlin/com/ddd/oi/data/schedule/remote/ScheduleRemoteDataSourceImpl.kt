package com.ddd.oi.data.schedule.remote

import com.ddd.oi.data.schedule.model.ScheduleDto
import com.ddd.oi.data.core.model.safeApiCall
import com.ddd.oi.data.schedule.model.ScheduleRequestDto
import javax.inject.Inject

class ScheduleRemoteDataSourceImpl @Inject constructor(
    private val scheduleApiService: ScheduleApiService
): ScheduleRemoteDataSource {
    override suspend fun getScheduleList(year: Int, month: Int): Result<List<ScheduleDto>> {
        return safeApiCall {
            scheduleApiService.getSchedules(year = year, month = month)
        }
    }

    override suspend fun uploadSchedule(schedule: ScheduleRequestDto): Result<ScheduleDto> {
        return safeApiCall {
            scheduleApiService.uploadSchedule(request = schedule)
        }
    }

    override suspend fun deleteSchedule(scheduleId: Long): Result<Unit> {
        return safeApiCall {
            scheduleApiService.deleteSchedule(scheduleId = scheduleId)
        }.map { Unit }
    }
}