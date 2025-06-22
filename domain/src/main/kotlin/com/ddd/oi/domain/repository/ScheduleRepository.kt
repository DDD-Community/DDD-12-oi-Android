package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.schedule.Schedule

interface ScheduleRepository {
    suspend fun getScheduleList(year: Int, month: Int): Result<List<Schedule>>
    suspend fun deleteSchedule(scheduleId: Long): Result<Boolean>
    suspend fun createSchedule(schedule: Schedule): Result<Schedule>
    suspend fun updateSchedule(schedule: Schedule): Result<Schedule>
}