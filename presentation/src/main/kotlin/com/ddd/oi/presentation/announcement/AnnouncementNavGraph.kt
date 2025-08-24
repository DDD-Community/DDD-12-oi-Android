package com.ddd.oi.presentation.announcement

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ddd.oi.presentation.core.navigation.Route

fun NavController.navigateToAnnouncement() {
    navigate(Route.Announcement)
}

fun NavGraphBuilder.announcementNavGraph(
    navigatePopBack: () -> Unit
) {
    composable<Route.Announcement> {
        AnnouncementScreen(
            onBack = navigatePopBack
        )
    }
}