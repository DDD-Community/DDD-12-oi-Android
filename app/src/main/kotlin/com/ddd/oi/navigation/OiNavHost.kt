package com.ddd.oi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.navigation.MainTab
import com.ddd.oi.presentation.core.navigation.OiNavigator
import com.ddd.oi.presentation.core.navigation.Route
import com.ddd.oi.presentation.upsertschedule.upsertScheduleNavGraph
import com.ddd.oi.presentation.home.homeNavGraph
import com.ddd.oi.presentation.schedule.scheduleNavGraph
import com.ddd.oi.presentation.scheduledetail.scheduleDetailNavGraph
import com.ddd.oi.presentation.searchplace.searchPlaceNavGraph
import com.ddd.oi.presentation.upsertplace.upsertPlaceNavGraph
import com.ddd.oi.presentation.recommendedlist.recommendedListNavGraph
import com.ddd.oi.presentation.recommendeddetail.recommendedDetailNavGraph
import com.ddd.oi.presentation.setting.settingNavGraph
import com.ddd.oi.presentation.profile.profileNavGraph
import com.ddd.oi.presentation.announcement.announcementNavGraph
import com.ddd.oi.presentation.contactus.contactUsNavGraph

@Composable
fun OiNavHost(
    navigator: OiNavigator,
    onShowSnackbar: (OiSnackbarData) -> Unit,
    modifier: Modifier = Modifier,
) {
    /**
     * todo 스낵바 Throwable 타입으로 던지기?
     */
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier
    ) {
        homeNavGraph(
            navigateToRecommendedList = navigator::navigateToRecommendedList,
            navigateToRecommendedDetail = navigator::navigateToRecommendedDetail,
            navigateToScheduleCreate = { navigator.navigateToUpsertSchedule(null, Route.UpsertSchedule()) },
            navigateToScheduleTab = { navigator.navigate(MainTab.SCHEDULE) },
            navigateToSetting = { navigator.navigateToSetting() }
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

        recommendedListNavGraph(
            navigateToRecommendedDetail = navigator::navigateToRecommendedDetail,
            navigateBack = { navigator.popBackStack() }
        )

        recommendedDetailNavGraph(
            navigateBack = { navigator.popBackStack() }
        )

        settingNavGraph(
            navigatePopBack = { navigator.popBackStack() },
            onNavigateToProfile = { navigator.navigateToProfile() },
            onNavigateToAnnouncement = { navigator.navigateToAnnouncement() },
            onNavigateToContactUs = { navigator.navigateToContactUs() },
            onNavigateToWebView = { title, url -> navigator.navigateToWebView(title, url) }
        )

        profileNavGraph(
            navigatePopBack = { navigator.popBackStack() },
            onChangeNickname = { /* TODO: 닉네임 변경 화면으로 이동 */ },
            onLogout = { /* TODO: 로그아웃 처리 */ },
            onWithdrawAccount = { /* TODO: 회원탈퇴 처리 */ }
        )

        announcementNavGraph(
            navigatePopBack = { navigator.popBackStack() }
        )

        contactUsNavGraph(
            navigatePopBack = { navigator.popBackStack() }
        )
    }
}