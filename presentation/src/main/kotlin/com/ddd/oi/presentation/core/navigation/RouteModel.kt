package com.ddd.oi.presentation.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data class UpsertSchedule(val mode: UpsertMode = UpsertMode.CREATE): Route

    @Serializable
    data class ScheduleDetail(val scheduleId: Long): Route

    @Serializable
    data class SearchPlace(val scheduleId: Long): Route

    @Serializable
    data class UpsertPlace(val scheduleId: Long): Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Home: MainTabRoute

    @Serializable
    data object Schedule: MainTabRoute
}


@Serializable
enum class UpsertMode {
    CREATE,
    EDIT,
    COPY
}