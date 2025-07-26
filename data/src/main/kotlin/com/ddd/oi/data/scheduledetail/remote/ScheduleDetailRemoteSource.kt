package com.ddd.oi.data.scheduledetail.remote

import com.ddd.oi.data.scheduledetail.model.EditPlaceDto
import com.ddd.oi.data.scheduledetail.model.PlaceDto
import com.ddd.oi.domain.model.schedule.SchedulePlace

interface ScheduleDetailRemoteSource {
    suspend fun getScheduleDetails(scheduleId: Long): Result<List<PlaceDto>>

    suspend fun updateScheduleDetail(scheduleId: Long, schedulePlace: SchedulePlace): Result<EditPlaceDto>

    suspend fun deleteScheduleDetail(scheduleId: Long, scheduleDetailId: Long): Result<Boolean>
}