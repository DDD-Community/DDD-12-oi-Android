package com.ddd.oi.presentation.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.ddd.oi.presentation.R

enum class MainTab(
    @DrawableRes val iconResId: Int,
    @StringRes val contentDescription: Int,
    val route: MainTabRoute
) {
    HOME(
        iconResId = R.drawable.ic_home,
        contentDescription = R.string.home,
        route = MainTabRoute.Home
    ),

    SCHEDULE(
        iconResId = R.drawable.ic_schedule,
        contentDescription = R.string.schedule,
        route = MainTabRoute.Schedule
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}