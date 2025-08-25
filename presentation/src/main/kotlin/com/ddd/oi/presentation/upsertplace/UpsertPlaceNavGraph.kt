package com.ddd.oi.presentation.upsertplace

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ddd.oi.domain.model.schedule.SchedulePlace
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.navigation.Route
import com.ddd.oi.presentation.core.navigation.SchedulePlaceNavType
import kotlin.reflect.typeOf

fun NavController.navigateToUpsertPlace(schedulePlace: Route.UpsertPlace) {
    navigate(schedulePlace)
}

fun NavGraphBuilder.upsertPlaceNavGraph(
    navigatePopBack: () -> Unit,
    onShowSnackBar: (OiSnackbarData) -> Unit,
) {
    composable<Route.UpsertPlace>(
        typeMap = mapOf(typeOf<SchedulePlace>() to SchedulePlaceNavType)
    ) { backStackEntry ->
        val upsertPlace = backStackEntry.toRoute<Route.UpsertPlace>()
        UpsertPlaceScreen(
            scheduleId = upsertPlace.schedulePlace.id,
            placeName = upsertPlace.schedulePlace.spotName,
            onBack = navigatePopBack,
            onShowSnackBar = onShowSnackBar
        )
    }
}