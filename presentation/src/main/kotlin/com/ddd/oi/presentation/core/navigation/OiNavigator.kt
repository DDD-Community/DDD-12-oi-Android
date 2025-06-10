package com.ddd.oi.presentation.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.ddd.oi.presentation.createschedule.navigateToCreateSchedule
import com.ddd.oi.presentation.home.navigateToHome
import com.ddd.oi.presentation.schedule.navigateToSchedule
import com.ddd.oi.presentation.scheduledetail.navigateToScheduleDetail
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Stable
class OiNavigator(
    val navController: NavHostController
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    private val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    val mainTabList: PersistentList<MainTab> = MainTab.entries.toPersistentList()
    val startDestination: Route = MainTabRoute.Home

    @Composable
    fun shouldShowBottomBar(): Boolean = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions)
            MainTab.SCHEDULE -> navController.navigateToSchedule(navOptions)
        }
    }

    fun navigateToCreateSchedule() = navController.navigateToCreateSchedule()
    fun navigateToScheduleDetail() = navController.navigateToScheduleDetail()
    fun popBackStack() {
        navController.popBackStack()
    }
}

@Composable
fun rememberOiNavigator(
    navController: NavHostController = rememberNavController(),
): OiNavigator = remember(navController) {
    OiNavigator(navController)
}