package com.ddd.oi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ddd.oi.navigation.OiNavHost
import com.ddd.oi.presentation.core.designsystem.component.MainBottomBar
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.navigation.OiNavigator

@Composable
fun OiApp(
    navigator: OiNavigator
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = OiTheme.colors.backgroundPrimary,
        snackbarHost = {
            SnackbarHost(
                snackBarHostState,
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
            )
        },
        bottomBar = {
            MainBottomBar(
                modifier = Modifier.navigationBarsPadding(),
                visible = navigator.shouldShowBottomBar(),
                tabs = navigator.mainTabList,
                currentTab = navigator.currentTab,
                onTabSelected = navigator::navigate
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                navigator.shouldShowBottomBar(),
                enter = fadeIn() + slideIn { IntOffset(0, it.height) },
                exit = fadeOut() + slideOut { IntOffset(0, it.height) }) {
                FloatingActionButton(
                    onClick = navigator::navigateToCreateSchedule,
                    shape = CircleShape,
                    containerColor = OiTheme.colors.iconOnSecondary,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    modifier = Modifier.offset(y = 38.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = OiTheme.colors.textOnPrimary
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(padding)
        ) {
            OiNavHost(navigator)
        }
    }
}