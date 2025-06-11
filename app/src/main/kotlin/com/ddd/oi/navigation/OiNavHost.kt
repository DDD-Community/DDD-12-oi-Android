package com.ddd.oi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.ddd.oi.presentation.core.navigation.OiNavigator
import com.ddd.oi.presentation.upsertschedule.upsertScheduleNavGraph
import com.ddd.oi.presentation.home.homeNavGraph
import com.ddd.oi.presentation.schedule.scheduleNavGraph
import com.ddd.oi.presentation.scheduledetail.scheduleDetailNavGraph

@Composable
fun OiNavHost(
    navigator: OiNavigator,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier
    ) {
        homeNavGraph()
        scheduleNavGraph()
        upsertScheduleNavGraph(
            navigateToScheduleDetail = navigator::navigateToScheduleDetail
        )
        scheduleDetailNavGraph()
    }

}