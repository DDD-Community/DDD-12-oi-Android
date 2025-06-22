package com.ddd.oi.data.schedule.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDto(
    val scheduleId: Long,

    val scheduleTag: CategoryDto,

    val title: String,

    val startDate: String,
    val endDate: String,

    val mobility: TransportationDto,

    @SerialName("groups")
    val groupList: List<GroupDto> = emptyList()
)


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