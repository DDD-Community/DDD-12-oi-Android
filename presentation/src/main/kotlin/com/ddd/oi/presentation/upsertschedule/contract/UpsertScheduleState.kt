package com.ddd.oi.presentation.upsertschedule.contract

import androidx.compose.runtime.Stable
import com.ddd.oi.domain.model.Category
import com.ddd.oi.domain.model.Party
import com.ddd.oi.domain.model.Transportation

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
}