package com.ddd.oi.presentation.recommendedlist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToRecommendedList() {
    navigate(Route.RecommendedList)
}

fun NavGraphBuilder.recommendedListNavGraph(
    navigateToRecommendedDetail: (Long) -> Unit,
    navigateBack: () -> Unit
) {
    composable<Route.RecommendedList> {
        RecommendedListScreen(
            onNavigateToDetail = navigateToRecommendedDetail,
            onNavigateBack = navigateBack
        )
    }
}