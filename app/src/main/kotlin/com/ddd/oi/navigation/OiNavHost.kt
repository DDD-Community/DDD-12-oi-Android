package com.ddd.oi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.navigation.OiNavigator
import com.ddd.oi.presentation.upsertschedule.upsertScheduleNavGraph
import com.ddd.oi.presentation.home.homeNavGraph
import com.ddd.oi.presentation.schedule.scheduleNavGraph
import com.ddd.oi.presentation.scheduledetail.scheduleDetailNavGraph
import com.ddd.oi.presentation.searchplace.searchPlaceNavGraph
import com.ddd.oi.presentation.upsertplace.upsertPlaceNavGraph

@Composable
fun OiNavHost(
    navigator: OiNavigator,
    onShowSnackbar: (OiSnackbarData) -> Unit,
    modifier: Modifier = Modifier,
) {
    /**
     * todo 스낵바 Throwable 타입으로 던지기?
     */
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier
    ) {
        homeNavGraph()

        scheduleNavGraph(
            navigateToScheduleDetail = navigator::navigateToScheduleDetail,
            navigateToCreateSchedule = { schedule, scheduleCopy ->
                navigator.navigateToUpsertSchedule(schedule, scheduleCopy)
            },
            onShowSnackbar = onShowSnackbar
        )

        upsertScheduleNavGraph(
            navigator = navigator,
            navigatePopBack = { scheduleCreated ->
                navigator.navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("schedule_created", scheduleCreated)
                navigator.popBackStack()
            },
            onShowSnackbar = onShowSnackbar
        )
        scheduleDetailNavGraph(
            onBackClick = { navigator.popBackStack() },
            navigateToSearchPlace = { navigator.navigateToSearchPlace(it) }
        )

        searchPlaceNavGraph(
            navigatePopBack = {
                navigator.popBackStack()
            }
        )

        upsertPlaceNavGraph(
            navigatePopBack = {
                navigator.popBackStack()
            },
            onShowSnackBar = onShowSnackbar
        )
    }
}