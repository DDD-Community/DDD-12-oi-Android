package com.ddd.oi.presentation.upsertplace

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToUpsertPlace(scheduleId: Long, placeName: String) {
    navigate(Route.UpsertPlace(scheduleId, placeName))
}

fun NavGraphBuilder.upsertPlaceNavGraph(
    navigatePopBack: () -> Unit,
    onShowSnackBar: (OiSnackbarData) -> Unit,
) {
    composable<Route.UpsertPlace> { backStackEntry ->
        val upsertPlace = backStackEntry.toRoute<Route.UpsertPlace>()
        UpsertPlaceScreen(
            scheduleId = upsertPlace.scheduleId,
            placeName = upsertPlace.placeName,
            onBack = navigatePopBack,
            onShowSnackBar = onShowSnackBar
        )
    }
}