package com.ddd.oi.presentation.upsertschedule.contract

import androidx.compose.runtime.Stable
import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Party
import com.ddd.oi.domain.model.schedule.SchedulePlace
import com.ddd.oi.domain.model.schedule.Transportation
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.todayIn

@Stable
data class UpsertScheduleState(
    val id: Long,
    val title: String,
    val category: Category?,
    val startDate: Long,
    val endDate: Long,
    val transportation: Transportation?,
    val party: Set<Party>,
    val placeList: List<SchedulePlace>
) {

    companion object {
        val default = UpsertScheduleState(
            id = -1,
            title = "",
            category = null,
            startDate = -1,
            endDate = -1L,
            transportation = null,
            party = emptySet(),
            placeList = emptyList()
        )
    }

    val isButtonEnable: Boolean get() = title.isNotEmpty() &&
            category != null &&
            startDate > 0L &&
            transportation != null &&
            party.isNotEmpty()

    val isPastDate: Boolean get() {
        val zone = TimeZone.currentSystemDefault()
        val today = Clock.System.todayIn(zone)
            .atStartOfDayIn(zone)
            .toEpochMilliseconds()
        return startDate < today
    }
}