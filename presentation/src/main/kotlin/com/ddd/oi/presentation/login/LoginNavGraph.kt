package com.ddd.oi.presentation.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToLogin(navOptions: NavOptions) {
    navigate(Route.Login)
}

fun NavGraphBuilder.loginNavGraph(
    navigateToHome: () -> Unit,
) {
    composable<Route.Login> { backStackEntry ->
        LoginScreen(modifier = Modifier.fillMaxSize())
    }
}