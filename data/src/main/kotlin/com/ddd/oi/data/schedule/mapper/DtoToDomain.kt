package com.ddd.oi.data.schedule.mapper

import com.ddd.oi.data.schedule.model.CategoryDto
import com.ddd.oi.data.schedule.model.GroupDto
import com.ddd.oi.data.schedule.model.ScheduleDto
import com.ddd.oi.data.schedule.model.TransportationDto
import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Party
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.model.schedule.Transportation
import kotlinx.datetime.LocalDate


fun ScheduleDto.toDomain(): Schedule {
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
fun CategoryDto.toDomain(): Category = when (this) {
    CategoryDto.Trip -> Category.Travel
    CategoryDto.Date -> Category.Date
    CategoryDto.Daily -> Category.Daily
    CategoryDto.Business -> Category.Business
    CategoryDto.Etc -> Category.Etc
}

fun TransportationDto.toDomain(): Transportation = when (this) {
    TransportationDto.Car -> Transportation.Car
    TransportationDto.Public -> Transportation.Public
    TransportationDto.Bicycle -> Transportation.Bicycle
    TransportationDto.Walk -> Transportation.Walk
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