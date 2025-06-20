package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.schedule.Schedule

interface ScheduleRepository {
    suspend fun getScheduleList(year: Int, month: Int): Result<List<Schedule>>
}