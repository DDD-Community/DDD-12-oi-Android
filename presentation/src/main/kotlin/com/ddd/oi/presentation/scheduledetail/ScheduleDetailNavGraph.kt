package com.ddd.oi.presentation.scheduledetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToScheduleDetail() {
    navigate(Route.ScheduleDetail)
}

fun NavGraphBuilder.scheduleDetailNavGraph() {
    composable<Route.ScheduleDetail> {
        ScheduleDetailScreen()
    }
}
