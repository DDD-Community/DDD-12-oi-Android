package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import com.ddd.oi.domain.model.Category
import com.ddd.oi.domain.model.LatLng
import com.ddd.oi.domain.model.Party
import com.ddd.oi.domain.model.Place
import com.ddd.oi.domain.model.Schedule
import com.ddd.oi.domain.model.Transportation
import java.time.LocalDate
import kotlin.random.Random

object MockOiCalendarScheduleData {

    private val currentMonth = LocalDate.now().withDayOfMonth(1)

    fun generateMockSchedules(): List<Schedule> {
        return listOf(
            createTravelSchedule(),
        )
    }

    private fun createTravelSchedule(): Schedule {
        val startDate = getRandomDateInMonth()
        val endDate = startDate.plusDays(Random.nextLong(2, 4))

        return Schedule(
            id = 1L,
            createdAt = System.currentTimeMillis() - 86400000,
            updatedAt = System.currentTimeMillis(),
            title = "제주도 여행",
            category = Category.Travel,
            startedAt = startDate.atTime(6, 0).atZone(OiCalendarDefaults.zone).toInstant().toEpochMilli(),
            endedAt = endDate.atTime(23, 59).atZone(OiCalendarDefaults.zone).toInstant().toEpochMilli(),
            transportation = Transportation.Car,
            partySet = setOf(Party.OtherHalf, Party.Friend),
            placeList = listOf(
                Place(
                    id = 1L,
                    title = "공항 도착",
                    memo = "12:30 도착 예정",
                    startedAt = startDate.atTime(12, 30).atZone(OiCalendarDefaults.zone).toInstant().toEpochMilli(),
                    endedAt = startDate.atTime(13, 0).atZone(OiCalendarDefaults.zone).toInstant().toEpochMilli(),
                    latLng = LatLng(33.5067, 126.4928)
                ),
                Place(
                    id = 2L,
                    title = "연돈 치돈",
                    memo = "치즈돈까스",
                    startedAt = startDate.plusDays(1).atTime(6, 0).atZone(OiCalendarDefaults.zone).toInstant().toEpochMilli(),
                    endedAt = startDate.plusDays(1).atTime(8, 0).atZone(OiCalendarDefaults.zone).toInstant().toEpochMilli(),
                    latLng = LatLng(33.4584, 126.9426)
                ),
                Place(
                    id = 3L,
                    title = "제주 흑돼지",
                    memo = "소주 한잔",
                    startedAt = startDate.plusDays(1).atTime(9, 0).atZone(OiCalendarDefaults.zone).toInstant().toEpochMilli(),
                    endedAt = startDate.plusDays(1).atTime(16, 0).atZone(OiCalendarDefaults.zone).toInstant().toEpochMilli(),
                    latLng = LatLng(33.3617, 126.5292)
                )
            )
        )
    }

    private fun getRandomDateInMonth(): LocalDate {
        val daysInMonth = currentMonth.lengthOfMonth()
        val randomDay = Random.nextInt(1, minOf(daysInMonth + 1, 9))
        return currentMonth.withDayOfMonth(randomDay)
    }
}
