package com.ddd.oi.presentation.recommendeddetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToRecommendedDetail(contentId: Long) {
    navigate(Route.RecommendedDetail(contentId))
}

fun NavGraphBuilder.recommendedDetailNavGraph(
    navigateBack: () -> Unit
) {
    composable<Route.RecommendedDetail> { backStackEntry ->
        val route = backStackEntry.toRoute<Route.RecommendedDetail>()
        RecommendedDetailScreen(
            contentId = route.contentId,
            onNavigateBack = navigateBack
        )
    }
}