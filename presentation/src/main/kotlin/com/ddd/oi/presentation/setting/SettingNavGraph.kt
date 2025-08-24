package com.ddd.oi.presentation.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToSetting() {
    navigate(Route.Setting)
}

fun NavGraphBuilder.settingNavGraph(
    navigatePopBack: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAnnouncement: () -> Unit,
    onNavigateToContactUs: () -> Unit,
    onNavigateToWebView: (String, String) -> Unit
) {
    composable<Route.Setting> {
        SettingScreen(
            onBack = navigatePopBack,
            onNavigateToProfile = onNavigateToProfile,
            onNavigateToAnnouncement = onNavigateToAnnouncement,
            onNavigateToContactUs = onNavigateToContactUs,
            onNavigateToWebView = onNavigateToWebView
        )
    }
}