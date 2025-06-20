package com.ddd.oi.domain.model.schedule

import kotlinx.datetime.LocalDate

data class CreateScheduleParam(
    val title: String,
    val category: Category,
    val startedAt: LocalDate,
    val endedAt: LocalDate,
    val transportation: Transportation,
    val partySet: Set<Party>,
    val placeList: List<Place>
)