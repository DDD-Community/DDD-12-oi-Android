package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.schedule.Place

interface ScheduleDetailRepository {
    suspend fun getScheduleDetail(scheduleId: Long): Result<Map<String, List<Place>>>
}