package com.ddd.oi.presentation.upsertschedule.contract

import androidx.compose.runtime.Stable
import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Party
import com.ddd.oi.domain.model.schedule.Transportation

@Stable
data class UpsertScheduleState(
    val id: Int,
    val title: String,
    val category: Category?,
    val startDate: Long,
    val endDate: Long,
    val transportation: Transportation?,
    val party: Set<Party>,
) {

    companion object {
        val default = UpsertScheduleState(
            id = -1,
            title = "",
            category = null,
            startDate = System.currentTimeMillis(),
            endDate = -1L,
            transportation = null,
            party = emptySet()
        )
    }

    val isButtonEnable: Boolean get() = title.isNotEmpty() &&
            category != null &&
            startDate > 0L &&
            transportation != null &&
            party.isNotEmpty()
}