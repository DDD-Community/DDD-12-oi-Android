package com.ddd.oi.presentation.scheduledetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToScheduleDetail(scheduleId: Long) {
    navigate(Route.ScheduleDetail(scheduleId))
}

fun NavGraphBuilder.scheduleDetailNavGraph(
    navigateToSearchPlace: (Long) -> Unit
) {
    composable<Route.ScheduleDetail> { backStackEntry ->
        val scheduleId = backStackEntry.toRoute<Route.ScheduleDetail>().scheduleId
        ScheduleDetailScreen(
            scheduleId = scheduleId,
            navigateToSearchPlace = { navigateToSearchPlace(scheduleId) }
        )
    }
}
