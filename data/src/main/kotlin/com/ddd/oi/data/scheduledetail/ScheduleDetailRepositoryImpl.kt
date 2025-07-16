package com.ddd.oi.data.scheduledetail

import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailRemoteDataSource
import com.ddd.oi.data.scheduledetail.remote.SpotRequest
import com.ddd.oi.domain.model.Place
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
    private val remoteDataSource: ScheduleDetailRemoteDataSource
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
//        val latlng = convertTM128ToWGS84(place.mapX.toDouble(), place.mapY.toDouble())
        return remoteDataSource.putScheduleDetail(
            scheduleId,
            detailId,
            SpotRequest(
                targetDate = targetDate,
                memo = "",
                spotName = place.title,
                latitude = place.mapY.div(10_000_000.0),
                longitude = place.mapX.div(10_000_000.0),
                category = place.category
            )
        )
    }

    override suspend fun postScheduleDetail(scheduleId: Int, body: List<Place>): Boolean {
        val targetDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .toJavaLocalDateTime()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return remoteDataSource.postScheduleDetail(
            scheduleId,
            body.map { place ->
                val latlng = convertTM128ToWGS84(place.mapX.toDouble(), place.mapY.toDouble())
                SpotRequest(
                    targetDate = targetDate,
                    memo = "",
                    spotName = place.title,
                    latitude = place.mapY.div(10_000_000.0),
                    longitude = place.mapX.div(10_000_000.0),
                    category = place.category
                )
            }
        )
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