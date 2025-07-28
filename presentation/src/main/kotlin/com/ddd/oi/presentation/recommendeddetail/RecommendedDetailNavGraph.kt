package com.ddd.oi.presentation.recommendeddetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToRecommendedDetail() {
    navigate(Route.RecommendedDetail)
}

fun NavGraphBuilder.recommendedDetailNavGraph() {
    composable<Route.RecommendedDetail> {
        RecommendedDetailScreen()
    }
}