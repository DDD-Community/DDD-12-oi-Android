package com.ddd.oi.data.schedule.remote

import com.ddd.oi.data.schedule.model.ScheduleDto
import com.ddd.oi.data.schedule.model.ScheduleRequestDto

interface ScheduleRemoteDataSource {
    suspend fun getScheduleList(year: Int, month: Int): Result<List<ScheduleDto>>

    suspend fun uploadSchedule(schedule: ScheduleRequestDto): Result<ScheduleDto>

    suspend fun deleteSchedule(scheduleId: Long): Result<Unit>

}