package com.ddd.oi.data.scheduledetail.remote

interface ScheduleDetailRemoteDataSource {

    suspend fun putScheduleDetail(
        scheduleId: Int,
        detailId: Int,
        body: SpotRequest
    ): Boolean

    suspend fun postScheduleDetail(
        scheduleId: Int,
        body: List<SpotRequest>
    ): Boolean
}