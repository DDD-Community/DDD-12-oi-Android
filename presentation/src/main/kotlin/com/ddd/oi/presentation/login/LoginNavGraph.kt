package com.ddd.oi.presentation.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToLogin() {
    val navOption = NavOptions.Builder()
        .setPopUpTo(
            destinationId = this.graph.id,
            inclusive = true,
        )
        .setLaunchSingleTop(true)
        .build()
    navigate(Route.Login, navOption)
}

fun NavGraphBuilder.loginNavGraph(
    navigateToHome: () -> Unit,
    onShowSnackbar: (OiSnackbarData) -> Unit
) {
    composable<Route.Login> { backStackEntry ->
        LoginScreen(
            modifier = Modifier.fillMaxSize(),
            onShowSnackbar = onShowSnackbar
        )
    }
}