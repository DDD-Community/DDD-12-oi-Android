package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.Place
import com.ddd.oi.domain.model.schedule.SchedulePlace


interface ScheduleDetailRepository {
    suspend fun getScheduleDetail(scheduleId: Long): Result<Map<String, List<SchedulePlace>>>

    suspend fun updateScheduleDetail(scheduleId: Long, scheduleDetail: SchedulePlace): Result<SchedulePlace>

    suspend fun putScheduleDetail(
        scheduleId: Int,
        detailId: Int,
        place: Place
    ): Boolean

    suspend fun postScheduleDetail(
        scheduleId: Int,
        body: List<Place>,
        targetDate: String
    ): Boolean
}