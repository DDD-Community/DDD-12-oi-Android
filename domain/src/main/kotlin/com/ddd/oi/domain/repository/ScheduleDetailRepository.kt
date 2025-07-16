package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.Place

interface ScheduleDetailRepository {
    suspend fun putScheduleDetail(
        scheduleId: Int,
        detailId: Int,
        place: Place
    ): Boolean

    suspend fun postScheduleDetail(
        scheduleId: Int,
        body: List<Place>
    ): Boolean
}