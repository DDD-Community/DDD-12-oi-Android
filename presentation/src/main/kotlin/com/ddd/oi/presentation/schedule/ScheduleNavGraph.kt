package com.ddd.oi.presentation.schedule

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.navigation.MainTabRoute
import com.ddd.oi.presentation.core.navigation.Route
import com.ddd.oi.presentation.schedule.model.ScheduleNavData

fun NavController.navigateToSchedule(navOptions: NavOptions) {
    navigate(MainTabRoute.Schedule, navOptions)
}

fun NavGraphBuilder.scheduleNavGraph(
    navigateToCreateSchedule: (ScheduleNavData?, Route.UpsertSchedule) -> Unit,
    navigateToScheduleDetail: (Schedule) -> Unit,
    onShowSnackbar: (OiSnackbarData) -> Unit
) {
    composable<MainTabRoute.Schedule> { backStackEntry ->
        val scheduleCreated = backStackEntry.savedStateHandle.get<Boolean>("schedule_created") ?: false
        
        if (scheduleCreated) {
            backStackEntry.savedStateHandle.remove<Boolean>("schedule_created")
        }
        
        ScheduleScreen(
            navigateToCreateSchedule = navigateToCreateSchedule,
            navigateToScheduleDetail = navigateToScheduleDetail,
            onShowSnackbar = onShowSnackbar,
            scheduleCreated = scheduleCreated,
        )
    }
}
