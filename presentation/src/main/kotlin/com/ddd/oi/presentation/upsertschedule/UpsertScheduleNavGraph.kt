package com.ddd.oi.presentation.upsertschedule

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.navigation.OiNavigator
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToInsertSchedule(scheduleCopy: Route.UpsertSchedule) {
    navigate(scheduleCopy)
}

fun NavGraphBuilder.upsertScheduleNavGraph(
    navigator: OiNavigator,
    onShowSnackbar: (OiSnackbarData) -> Unit,
    navigatePopBack: (Boolean) -> Unit
) {
    composable<Route.UpsertSchedule> {
        val scheduleNavData = navigator.consumeTempSchedule()
        UpsertScheduleScreen(
            navigatePopBack = navigatePopBack,
            scheduleNavData = scheduleNavData,
            onShowSnackbar = onShowSnackbar
        )
    }
}
