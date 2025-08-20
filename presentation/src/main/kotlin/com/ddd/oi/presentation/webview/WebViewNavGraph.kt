package com.ddd.oi.presentation.webview

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToWebView(url: String, title: String) {
    navigate(Route.WebView(url, title))
}

fun NavGraphBuilder.webViewNavGraph(
    onBackClick: () -> Unit
) {
    composable<Route.WebView> { backStackEntry ->
        val webViewData = backStackEntry.toRoute<Route.WebView>()

        
        WebViewScreen(
            url = webViewData.url,
            title = webViewData.title,
            onBackClick = onBackClick
        )
    }
}