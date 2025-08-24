package com.ddd.oi.presentation.withdraw

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToWithdraw() {
    navigate(Route.Withdraw)
}

fun NavGraphBuilder.withdrawNavGraph(
    navigatePopBack: () -> Unit,
    onWithdraw: () -> Unit = {}
) {
    composable<Route.Withdraw> {
        WithdrawScreen(
            onBack = navigatePopBack,
            onWithdraw = onWithdraw
        )
    }
}