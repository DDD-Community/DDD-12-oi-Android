package com.ddd.oi.presentation.schedule.model

import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Party
import com.ddd.oi.domain.model.schedule.Place
import com.ddd.oi.domain.model.schedule.Transportation
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleNavData(
    val id: Long? = null,
    val title: String? = null,
    val category: Category? = null,
    val startedAt: Long? = null,
    val endedAt: Long? = null,
    val transportation: Transportation? = null,
    val party: Set<Party>? = null,
    val placeList: List<Place>? = null
)
