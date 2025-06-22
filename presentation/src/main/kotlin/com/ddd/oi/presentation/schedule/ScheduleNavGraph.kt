package com.ddd.oi.presentation.schedule

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.MainTabRoute

fun NavController.navigateToSchedule(navOptions: NavOptions) {
    navigate(MainTabRoute.Schedule, navOptions)
}

fun NavGraphBuilder.scheduleNavGraph(
    navigateToCreateSchedule: () -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    composable<MainTabRoute.Schedule> {
        ScheduleScreen(
            navigateToCreateSchedule = navigateToCreateSchedule,
            onShowSnackbar = onShowSnackbar
        )
    }
}
