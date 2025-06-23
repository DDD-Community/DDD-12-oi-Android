package com.ddd.oi.presentation.upsertschedule

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToInsertSchedule() {
    navigate(Route.UpsertSchedule)
}

fun NavGraphBuilder.upsertScheduleNavGraph(
    navigatePopBack: (Boolean) -> Unit
) {
    composable<Route.UpsertSchedule> {
        UpsertScheduleScreen(
            navigatePopBack = navigatePopBack
        )
    }
}
