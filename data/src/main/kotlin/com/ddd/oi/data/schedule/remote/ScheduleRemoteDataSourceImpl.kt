package com.ddd.oi.data.schedule.remote

import com.ddd.oi.data.schedule.model.ScheduleDto
import com.ddd.oi.data.core.model.safeApiCall
import com.ddd.oi.data.core.retrofit.api.ScheduleApiService
import com.ddd.oi.data.schedule.model.ScheduleRequest
import javax.inject.Inject

class ScheduleRemoteDataSourceImpl @Inject constructor(
    private val scheduleApiService: ScheduleApiService
): ScheduleRemoteDataSource {
    override suspend fun getScheduleList(year: Int, month: Int): Result<List<ScheduleDto>> {
        return safeApiCall {
            scheduleApiService.getSchedules(year = year, month = month)
        }
    }

    override suspend fun createSchedule(schedule: ScheduleRequest): Result<ScheduleDto> {
        return safeApiCall {
            scheduleApiService.createSchedule(request = schedule)
        }
    }

    override suspend fun deleteSchedule(scheduleId: Long): Result<Boolean> {
        return safeApiCall {
            scheduleApiService.deleteSchedule(scheduleId = scheduleId)
        }
    }

    override suspend fun updateSchedule(
        scheduleId: Long,
        schedule: ScheduleRequest
    ): Result<ScheduleDto> {
        return safeApiCall {
            scheduleApiService.updateSchedule(scheduleId = scheduleId, request = schedule)
        }
    }
}