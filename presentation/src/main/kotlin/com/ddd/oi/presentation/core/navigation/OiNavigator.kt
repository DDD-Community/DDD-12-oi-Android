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
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.model.schedule.ScheduleDetail
import com.ddd.oi.presentation.upsertschedule.navigateToInsertSchedule
import com.ddd.oi.presentation.home.navigateToHome
import com.ddd.oi.presentation.schedule.model.ScheduleNavData
import com.ddd.oi.presentation.schedule.navigateToSchedule
import com.ddd.oi.presentation.scheduledetail.navigateToScheduleDetail
import com.ddd.oi.presentation.searchplace.navigateToSearchPlace
import com.ddd.oi.presentation.upsertplace.navigateToUpsertPlace
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Stable
class OiNavigator(
    val navController: NavHostController
) {
    /**
     * TODO: local 일정의 id 값으로 받아오는 방향으로 리팩터링
     */
    private var tempScheduleData: ScheduleNavData? = null

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

    val currentRoute: Route?
        @Composable get() = when {
            currentDestination?.hasRoute(Route.UpsertSchedule::class) == true -> Route.UpsertSchedule()
            currentDestination?.hasRoute(MainTabRoute.Schedule::class) == true -> MainTabRoute.Schedule
            else -> null
        }

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

    fun navigateToUpsertSchedule(
        schedule: ScheduleNavData?,
        scheduleCopyState: Route.UpsertSchedule
    ) {
        tempScheduleData = schedule
        navController.navigateToInsertSchedule(scheduleCopyState)
    }


    fun navigateToSchedulePlace(scheduleId: Long) = navController.navigateToSearchPlace(scheduleId)

    fun navigateToScheduleDetail(scheduleId: Schedule) = navController.navigateToScheduleDetail(scheduleId)

    fun navigateToSearchPlace(scheduleId: Long) = navController.navigateToSearchPlace(scheduleId)

    fun navigateToUpsertPlace(scheduleId: Long, placeName: String) = navController.navigateToUpsertPlace(scheduleId, placeName)

    fun popBackStack() {
        navController.popBackStack()
    }

    fun consumeTempSchedule(): ScheduleNavData? {
        return tempScheduleData.also {
            tempScheduleData = null
        }
    }
}

@Composable
fun rememberOiNavigator(
    navController: NavHostController = rememberNavController(),
): OiNavigator = remember(navController) {
    OiNavigator(navController)
}