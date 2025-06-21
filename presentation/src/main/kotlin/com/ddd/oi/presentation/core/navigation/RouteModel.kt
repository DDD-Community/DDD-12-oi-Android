package com.ddd.oi.presentation.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object UpsertSchedule: Route

    @Serializable
    data object ScheduleDetail: Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Home: MainTabRoute

    @Serializable
    data object Schedule: MainTabRoute
}