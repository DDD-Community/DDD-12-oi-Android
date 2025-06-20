package com.ddd.oi.data.schedule.model

import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.model.schedule.Transportation
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDto(
    @SerialName("scheduleId")
    val scheduleId: Long,

    @SerialName("scheduleTag")
    val scheduleTag: CategoryDto,

    val title: String,

    val startDate: String,
    val endDate: String,

    @SerialName("mobility")
    val mobility: TransportationDto,

    @SerialName("groupList")
    val groupList: List<String> = emptyList()
) {
    fun toDomain(): Schedule {
        return Schedule(
            id = scheduleId,
            title = title,
            createdAt = 0L,
            updatedAt = 0L,
            category =  scheduleTag.toDomain(),
            startedAt = LocalDate.parse(startDate),
            endedAt = LocalDate.parse(endDate),
            transportation = mobility.toDomain(),
            partySet = emptySet(),
            placeList = emptyList()
        )
    }
}


@Serializable
enum class CategoryDto {
    @SerialName("TRIP")
    Trip,

    @SerialName("DATE")
    Date,

    @SerialName("DAILY")
    Daily,

    @SerialName("BUSINESS")
    Business,

    @SerialName("OTHER")
    Etc;

}
fun CategoryDto.toDomain(): Category = when (this) {
    CategoryDto.Trip -> Category.Travel
    CategoryDto.Date -> Category.Date
    CategoryDto.Daily -> Category.Daily
    CategoryDto.Business -> Category.Business
    CategoryDto.Etc -> Category.Etc
}

@Serializable
enum class TransportationDto {
    @SerialName("CAR")
    Car,

    @SerialName("PUBLIC_TRANSPORT")
    Public,

    @SerialName("BICYCLE")
    Bicycle,

    @SerialName("WALK")
    Walk
}
fun TransportationDto.toDomain(): Transportation = when (this) {
    TransportationDto.Car -> Transportation.Car
    TransportationDto.Public -> Transportation.Public
    TransportationDto.Bicycle -> Transportation.Bicycle
    TransportationDto.Walk -> Transportation.Walk
}