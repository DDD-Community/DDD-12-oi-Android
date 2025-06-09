package com.ddd.oi.presentation.createschedule

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToCreateSchedule() {
    navigate(Route.CreateSchedule)
}

fun NavGraphBuilder.createScheduleNavGraph() {
    composable<Route.CreateSchedule> { }
}
