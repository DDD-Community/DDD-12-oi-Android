package com.ddd.oi.presentation.searchplace

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToSearchPlace(scheduleId: Long) {
    navigate(Route.SearchPlace(scheduleId))
}

fun NavGraphBuilder.searchPlaceNavGraph() {
    composable<Route.SearchPlace> { backStackEntry ->
        val searchPlace = backStackEntry.toRoute<Route.SearchPlace>()
        SearchPlaceScreen(
            scheduleId = searchPlace.scheduleId
        )
    }
}