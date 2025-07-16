package com.ddd.oi.data.scheduledetail.remote

import android.util.Log
import retrofit2.HttpException
import javax.inject.Inject

class ScheduleDetailRemoteDataSourceImpl @Inject constructor(
    private val api: ScheduleDetailApi
) : ScheduleDetailRemoteDataSource {

    override suspend fun putScheduleDetail(
        scheduleId: Int,
        detailId: Int,
        body: SpotRequest
    ): Boolean {
        return api.putScheduleDetail(1L, scheduleId, detailId, body).run {
            isSuccessful.also {
                if(it.not()) {
                    Log.e("API_TEST", "${HttpException(this)}")
                }
            }
        }
    }

    override suspend fun postScheduleDetail(
        scheduleId: Int,
        body: List<SpotRequest>
    ): Boolean {
        return api.postScheduleDetail(1L, scheduleId, body).run {
            isSuccessful.also {
                if(it.not()) {
                    Log.e("API_TEST", "${HttpException(this)}")
                }
            }
        }
    }
}