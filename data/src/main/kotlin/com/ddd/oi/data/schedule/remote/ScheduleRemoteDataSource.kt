package com.ddd.oi.data.schedule.remote

import com.ddd.oi.data.schedule.model.ScheduleDto
import com.ddd.oi.data.schedule.model.ScheduleRequest

interface ScheduleRemoteDataSource {
    suspend fun getScheduleList(year: Int, month: Int): Result<List<ScheduleDto>>

    suspend fun uploadSchedule(schedule: ScheduleRequest): Result<ScheduleDto>

    suspend fun deleteSchedule(scheduleId: Long): Result<Boolean>

    suspend fun updateSchedule(scheduleId: Long, schedule: ScheduleRequest): Result<ScheduleDto>
}