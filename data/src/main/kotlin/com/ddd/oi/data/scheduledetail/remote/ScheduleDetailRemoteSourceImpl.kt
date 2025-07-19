package com.ddd.oi.data.scheduledetail.remote

import com.ddd.oi.data.core.model.safeApiCall
import com.ddd.oi.data.core.retrofit.api.ScheduleApiService
import com.ddd.oi.data.scheduledetail.model.PlaceDto
import com.ddd.oi.data.scheduledetail.model.PlaceInfo
import javax.inject.Inject

class ScheduleDetailRemoteSourceImpl @Inject constructor(
    private val scheduleApiService: ScheduleApiService
): ScheduleDetailRemoteSource {
    override suspend fun getScheduleDetails(scheduleId: Long): Result<List<PlaceDto>> {
        return safeApiCall {
            scheduleApiService.getScheduleDetails(scheduleId = scheduleId)
        }
    }
}