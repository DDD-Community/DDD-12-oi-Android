package com.ddd.oi.data.scheduledetail.remote

import com.ddd.oi.data.scheduledetail.model.PlaceDto

interface ScheduleDetailRemoteSource {
    suspend fun getScheduleDetails(scheduleId: Long): Result<List<PlaceDto>>
}