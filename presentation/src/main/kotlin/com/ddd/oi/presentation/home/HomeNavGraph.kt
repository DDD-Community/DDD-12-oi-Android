package com.ddd.oi.presentation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.MainTabRoute

fun NavController.navigateToHome(navOptions: NavOptions) {
    navigate(MainTabRoute.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    navigateToRecommendedList: () -> Unit,
    navigateToRecommendedDetail: (Long) -> Unit,
    navigateToScheduleCreate: () -> Unit,
    navigateToScheduleTab: () -> Unit
) {
    composable<MainTabRoute.Home> {
        HomeScreen(
            onNavigateToRecommendedList = navigateToRecommendedList,
            onNavigateToRecommendedDetail = navigateToRecommendedDetail,
            onNavigateToScheduleCreate = navigateToScheduleCreate,
            onNavigateToScheduleTab = navigateToScheduleTab
        )
    }
}