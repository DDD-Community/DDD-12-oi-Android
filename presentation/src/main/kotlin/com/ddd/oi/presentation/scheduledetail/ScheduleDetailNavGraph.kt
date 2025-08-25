package com.ddd.oi.presentation.scheduledetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.presentation.core.navigation.Route
import com.ddd.oi.presentation.core.navigation.ScheduleNavType
import kotlin.reflect.typeOf

fun NavController.navigateToScheduleDetail(schedule: Schedule) {
    navigate(Route.ScheduleDetail(schedule))
}

fun NavGraphBuilder.scheduleDetailNavGraph(
    navigateToSearchPlace: (Route.SearchPlace) -> Unit,
    navigateToEditPlace: (Route.UpsertPlace) -> Unit,
    onBackClick: () -> Unit
) {
    composable<Route.ScheduleDetail>(
        typeMap = mapOf(typeOf<Schedule>() to ScheduleNavType)
    ) { backStackEntry ->
        ScheduleDetailScreen(
            onBackClick = onBackClick,
            navigateToSearchPlace = { id, date ->
                navigateToSearchPlace(Route.SearchPlace(id, date))
            },
            navigateToEditPlace = { id, place ->
                navigateToEditPlace(Route.UpsertPlace(id, place))
            }
        )
    }
}
