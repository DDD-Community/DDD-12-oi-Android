package com.ddd.oi.presentation.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToProfile() {
    navigate(Route.Profile)
}

fun NavGraphBuilder.profileNavGraph(
    navigatePopBack: () -> Unit
) {
    composable<Route.Profile> {
        ProfileScreen(
            onBack = navigatePopBack
        )
    }
}