package com.ddd.oi.data.schedule.model

import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Party
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

    @SerialName("groups")
    val groupList: List<GroupDto> = emptyList()
) {
    fun toDomain(): Schedule {
        return Schedule(
            id = scheduleId,
            title = title,
            category =  scheduleTag.toDomain(),
            startedAt = LocalDate.parse(startDate),
            endedAt = LocalDate.parse(endDate),
            transportation = mobility.toDomain(),
            partySet = groupList.map { it.toDomain() }.toSet(),
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

@Serializable
enum class GroupDto {
    @SerialName("SOLO")
    Solo,
    
    @SerialName("COUPLE")
    Couple,
    
    @SerialName("FRIEND")
    Friend,
    
    @SerialName("PARENTS")
    Parents,
    
    @SerialName("SIBLINGS")
    Siblings,
    
    @SerialName("CHILDREN")
    Children,
    
    @SerialName("PET")
    Pet,
    
    @SerialName("OTHER")
    Other
}

fun GroupDto.toDomain(): Party = when (this) {
    GroupDto.Solo -> Party.Alone
    GroupDto.Couple -> Party.OtherHalf
    GroupDto.Friend -> Party.Friend
    GroupDto.Parents -> Party.Parent
    GroupDto.Siblings -> Party.Sibling
    GroupDto.Children -> Party.Children
    GroupDto.Pet -> Party.Pet
    GroupDto.Other -> Party.Etc
}