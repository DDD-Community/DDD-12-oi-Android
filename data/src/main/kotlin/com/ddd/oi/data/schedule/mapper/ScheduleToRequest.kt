package com.ddd.oi.data.schedule.mapper

import com.ddd.oi.data.schedule.model.CategoryDto
import com.ddd.oi.data.schedule.model.GroupDto
import com.ddd.oi.data.schedule.model.ScheduleRequest
import com.ddd.oi.data.schedule.model.TransportationDto
import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Party
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.model.schedule.Transportation

fun Schedule.toRequest(): ScheduleRequest {
    return ScheduleRequest(
        title = title,
        startDate = startedAt.toString(),
        endDate = endedAt.toString(),
        mobility = transportation.toDto(),
        scheduleTag = category.toDto(),
        groupList = partySet.map { it.toDto() }
    )
}

fun Transportation.toDto(): TransportationDto {
    return when (this) {
        Transportation.Car -> TransportationDto.Car
        Transportation.Public -> TransportationDto.Public
        Transportation.Bicycle -> TransportationDto.Bicycle
        Transportation.Walk -> TransportationDto.Walk
    }
}

fun Category.toDto(): CategoryDto {
    return when (this) {
        Category.Travel -> CategoryDto.Trip
        Category.Date -> CategoryDto.Date
        Category.Daily -> CategoryDto.Daily
        Category.Business -> CategoryDto.Business
        Category.Etc -> CategoryDto.Etc
    }
}

fun Party.toDto(): GroupDto {
    return when (this) {
        Party.Alone -> GroupDto.Solo
        Party.Friend -> GroupDto.Friend
        Party.OtherHalf -> GroupDto.Couple
        Party.Parent -> GroupDto.Parents
        Party.Sibling -> GroupDto.Siblings
        Party.Children -> GroupDto.Children
        Party.Pet -> GroupDto.Pet
        Party.Etc -> GroupDto.Other
    }
}