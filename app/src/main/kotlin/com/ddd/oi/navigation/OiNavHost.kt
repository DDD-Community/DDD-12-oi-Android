package com.ddd.oi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.navigation.MainTab
import com.ddd.oi.presentation.core.navigation.OiNavigator
import com.ddd.oi.presentation.core.navigation.Route
import com.ddd.oi.presentation.upsertschedule.upsertScheduleNavGraph
import com.ddd.oi.presentation.home.homeNavGraph
import com.ddd.oi.presentation.login.loginNavGraph
import com.ddd.oi.presentation.schedule.scheduleNavGraph
import com.ddd.oi.presentation.scheduledetail.scheduleDetailNavGraph
import com.ddd.oi.presentation.searchplace.searchPlaceNavGraph
import com.ddd.oi.presentation.splash.splashNavGraph
import com.ddd.oi.presentation.upsertplace.upsertPlaceNavGraph
import com.ddd.oi.presentation.webview.webViewNavGraph
import com.ddd.oi.presentation.recommendedlist.recommendedListNavGraph
import com.ddd.oi.presentation.recommendeddetail.recommendedDetailNavGraph

@Composable
fun OiNavHost(
    navigator: OiNavigator,
    onShowSnackbar: (OiSnackbarData) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier
    ) {

        splashNavGraph(
            navigateToLogin = { navigator.navigateToLogin() },
            navigateToHome = {
                val navOptions = navOptions {
                    popUpTo(navigator.navController.graph.id) {
                        inclusive = true
                    }
                }
                navigator.navigateToHome(navOptions)
            },
            onShowSnackbar = onShowSnackbar
        )

        loginNavGraph(
            navigateToHome = {
                val navOptions = navOptions {
                    popUpTo(Route.Login) {
                        inclusive = true  // 로그인 화면도 스택에서 제거
                        saveState = false // 로그인 화면 상태 저장하지 않음
                    }
                    launchSingleTop = true
                }
                navigator.navigateToHome(navOptions)
            },
            onShowSnackbar = onShowSnackbar,
            onNavigateToWebView = navigator::navigateToWebView
        )


        homeNavGraph(
            navigateToRecommendedList = navigator::navigateToRecommendedList,
            navigateToRecommendedDetail = navigator::navigateToRecommendedDetail,
            navigateToScheduleCreate = { navigator.navigateToUpsertSchedule(null, Route.UpsertSchedule()) },
            navigateToScheduleTab = { navigator.navigate(MainTab.SCHEDULE) }
        )

        scheduleNavGraph(
            navigateToScheduleDetail = navigator::navigateToScheduleDetail,
            navigateToCreateSchedule = { schedule, scheduleCopy ->
                navigator.navigateToUpsertSchedule(schedule, scheduleCopy)
            },
            onShowSnackbar = onShowSnackbar
        )

        upsertScheduleNavGraph(
            navigator = navigator,
            navigatePopBack = { scheduleCreated ->
                navigator.navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("schedule_created", scheduleCreated)
                navigator.popBackStack()
            },
            onShowSnackbar = onShowSnackbar
        )
        scheduleDetailNavGraph(
            onBackClick = { navigator.popBackStack() },
            navigateToSearchPlace = { navigator.navigateToSearchPlace(it) }
        )

        searchPlaceNavGraph(
            navigatePopBack = {
                navigator.popBackStack()
            }
        )

        upsertPlaceNavGraph(
            navigatePopBack = {
                navigator.popBackStack()
            },
            onShowSnackBar = onShowSnackbar
        )

        webViewNavGraph(
            onBackClick = { navigator.popBackStack() }
        )
        recommendedListNavGraph(
            navigateToRecommendedDetail = navigator::navigateToRecommendedDetail,
            navigateBack = { navigator.popBackStack() }
        )

        recommendedDetailNavGraph(
            navigateBack = { navigator.popBackStack() }
        )
    }
}