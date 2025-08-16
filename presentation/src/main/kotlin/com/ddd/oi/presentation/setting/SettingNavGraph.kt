package com.ddd.oi.presentation.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToSetting() {
    navigate(Route.Setting)
}

fun NavController.navigateToWebView(title: String, url: String = "https://www.naver.com") {
    navigate(Route.WebView(title, url))
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
    
    composable<Route.WebView> { backStackEntry ->
        val webViewRoute = backStackEntry.toRoute<Route.WebView>()
        
        WebViewScreen(
            title = webViewRoute.title,
            url = webViewRoute.url,
            onBack = navigatePopBack
        )
    }
}