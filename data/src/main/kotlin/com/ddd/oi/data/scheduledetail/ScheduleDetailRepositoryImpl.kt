package com.ddd.oi.data.scheduledetail

import com.ddd.oi.data.scheduledetail.mapper.toDomain
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailRemoteDataSource
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailRemoteSource
import com.ddd.oi.data.scheduledetail.remote.SpotRequest
import com.ddd.oi.domain.model.Place
import com.ddd.oi.domain.model.schedule.SchedulePlace
import com.ddd.oi.domain.repository.ScheduleDetailRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

class ScheduleDetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: ScheduleDetailRemoteDataSource,
    private val scheduleDetailRemoteSource: ScheduleDetailRemoteSource
) : ScheduleDetailRepository {

    override suspend fun putScheduleDetail(
        scheduleId: Int,
        detailId: Int,
        place: Place
    ): Boolean {
        val targetDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .toJavaLocalDateTime()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return remoteDataSource.putScheduleDetail(
            scheduleId,
            detailId,
            SpotRequest(
                targetDate = targetDate,
                memo = "",
                spotName = place.title,
                latitude = place.latitude,
                longitude = place.longitude,
                category = place.category
            )
        )
    }

    override suspend fun postScheduleDetail(scheduleId: Int, body: List<Place>, targetDate: String): Boolean {
        return remoteDataSource.postScheduleDetail(
            scheduleId,
            body.map { place ->
                SpotRequest(
                    targetDate = targetDate,
                    memo = "",
                    spotName = place.title,
                    latitude = place.latitude,
                    longitude = place.longitude,
                    category = place.category
                )
            }
        )
    }

    override suspend fun getScheduleDetail(scheduleId: Long): Result<Map<String, List<SchedulePlace>>> {
        return scheduleDetailRemoteSource.getScheduleDetails(scheduleId).map { dtoList ->
            dtoList.associateBy(
                keySelector = { it.targetDate },
                valueTransform = { placeDto->
                    placeDto.details.map { it.toDomain() }
                }
            )
        }
    }

    override suspend fun updateScheduleDetail(scheduleId: Long, scheduleDetail: SchedulePlace): Result<SchedulePlace> {
        return scheduleDetailRemoteSource.updateScheduleDetail(scheduleId, scheduleDetail).map {
            it.toDomain()
        }
    }

    override suspend fun deleteScheduleDetail(
        scheduleId: Long,
        scheduleDetailId: Long
    ): Result<Boolean> {
        return scheduleDetailRemoteSource.deleteScheduleDetail(scheduleId, scheduleDetailId)
    }

    data class LatLng(val latitude: Double, val longitude: Double)

    private fun convertTM128ToWGS84(mapX: Double, mapY: Double): LatLng {
        // 변환 상수
        val RE = 6371.00877 // Earth radius (km)
        val GRID = 5.0      // Grid spacing (km)
        val SLAT1 = 30.0    // Projection latitude 1 (degree)
        val SLAT2 = 60.0    // Projection latitude 2 (degree)
        val OLON = 126.0    // Origin longitude (degree)
        val OLAT = 38.0     // Origin latitude (degree)
        val XO = 43         // Origin X coordinate (GRID)
        val YO = 136        // Origin Y coordinate (GRID)

        val DEGRAD = Math.PI / 180.0
        val RADDEG = 180.0 / Math.PI

        val re = RE / GRID
        val slat1 = SLAT1 * DEGRAD
        val slat2 = SLAT2 * DEGRAD
        val olon = OLON * DEGRAD
        val olat = OLAT * DEGRAD

        val sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        val snLog = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn)
        val sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        val sfLog = sf.pow(snLog) * Math.cos(slat1) / snLog
        val ro = re * sfLog / Math.tan(Math.PI * 0.25 + olat * 0.5).pow(snLog)

        val xn = mapX - XO
        val yn = ro - mapY + YO
        val ra = sqrt(xn * xn + yn * yn)
        var theta = atan2(xn, yn)
        if (theta < 0.0) theta += 2.0 * Math.PI

        val lat = 2.0 * atan(sfLog * re / ra.pow(1.0 / snLog)) - Math.PI * 0.5
        val lng = theta / snLog + olon

        return LatLng(lat * RADDEG, lng * RADDEG)
    }
}
