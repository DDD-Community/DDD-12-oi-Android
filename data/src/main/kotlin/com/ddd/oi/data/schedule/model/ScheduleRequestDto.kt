package com.ddd.oi.data.schedule.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleRequestDto(
    val title: String,
    val startDate: String,
    val endDate: String,
    @SerialName("mobility") val mobility: TransportationDto,
    @SerialName("scheduleTag") val scheduleTag: CategoryDto,
    val groupList: List<String>
)