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
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.presentation.upsertschedule.navigateToInsertSchedule
import com.ddd.oi.presentation.home.navigateToHome
import com.ddd.oi.presentation.login.navigateToLogin
import com.ddd.oi.presentation.recommendeddetail.navigateToRecommendedDetail
import com.ddd.oi.presentation.recommendedlist.navigateToRecommendedList
import com.ddd.oi.presentation.schedule.model.ScheduleNavData
import com.ddd.oi.presentation.schedule.navigateToSchedule
import com.ddd.oi.presentation.scheduledetail.navigateToScheduleDetail
import com.ddd.oi.presentation.searchplace.navigateToSearchPlace
import com.ddd.oi.presentation.upsertplace.navigateToUpsertPlace
import com.ddd.oi.presentation.webview.navigateToWebView
import com.ddd.oi.presentation.upsertschedule.navigateToInsertSchedule
import com.ddd.oi.presentation.setting.navigateToSetting
import com.ddd.oi.presentation.profile.navigateToProfile
import com.ddd.oi.presentation.announcement.navigateToAnnouncement
import com.ddd.oi.presentation.contactus.navigateToContactUs
import com.ddd.oi.presentation.withdraw.navigateToWithdraw
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
    val startDestination: Route = Route.Splash

    val currentRoute: Route?
        @Composable get() = when {
            currentDestination?.hasRoute(Route.UpsertSchedule::class) == true -> Route.UpsertSchedule()
            currentDestination?.hasRoute(MainTabRoute.Schedule::class) == true -> MainTabRoute.Schedule
            currentDestination?.hasRoute(Route.Splash::class) == true -> Route.Splash
            currentDestination?.hasRoute(Route.UpsertPlace::class) == true -> Route.UpsertPlace(0L, "")
            currentDestination?.hasRoute(Route.Setting::class) == true -> Route.Setting
            currentDestination?.hasRoute(Route.Profile::class) == true -> Route.Profile
            currentDestination?.hasRoute(Route.Announcement::class) == true -> Route.Announcement
            currentDestination?.hasRoute(Route.ContactUs::class) == true -> Route.ContactUs
            currentDestination?.hasRoute(Route.WebView::class) == true -> Route.WebView("", "")
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



    fun navigateToLogin() = navController.navigateToLogin()

    fun navigateToHome(navOptions: NavOptions) = navController.navigateToHome(navOptions)

    fun navigateToScheduleDetail(scheduleId: Schedule) = navController.navigateToScheduleDetail(scheduleId)

    fun navigateToSearchPlace(searchPlace: Route.SearchPlace) = navController.navigateToSearchPlace(searchPlace)

    fun navigateToUpsertPlace(schedulePlace: Route.UpsertPlace) = navController.navigateToUpsertPlace(schedulePlace)

    fun navigateToWebView(url: String, title: String) = navController.navigateToWebView(url, title)

    fun navigateToRecommendedList() = navController.navigateToRecommendedList()

    fun navigateToRecommendedDetail(contentId: Long) = navController.navigateToRecommendedDetail(contentId)

    fun navigateToSetting() = navController.navigateToSetting()

    fun navigateToProfile() = navController.navigateToProfile()

    fun navigateToAnnouncement() = navController.navigateToAnnouncement()

    fun navigateToContactUs() = navController.navigateToContactUs()

    fun navigateToWithdraw() = navController.navigateToWithdraw()

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