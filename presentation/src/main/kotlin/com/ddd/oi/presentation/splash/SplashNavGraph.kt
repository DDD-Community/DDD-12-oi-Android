package com.ddd.oi.presentation.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.navigation.Route

fun NavGraphBuilder.splashNavGraph(
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    onShowSnackbar: (OiSnackbarData) -> Unit
) {
    composable<Route.Splash> { backStackEntry ->
        SplashScreen(
            modifier = Modifier.fillMaxSize(),
            navigateToLogin = navigateToLogin,
            navigateToHome = navigateToHome
        )
    }
}