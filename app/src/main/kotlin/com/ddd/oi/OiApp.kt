package com.ddd.oi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ddd.oi.navigation.OiNavHost
import com.ddd.oi.presentation.core.designsystem.component.MainBottomBar
import com.ddd.oi.presentation.core.designsystem.component.scaffold.OiScaffold
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarHost
import com.ddd.oi.presentation.core.designsystem.component.snackbar.rememberSnackbarController
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.designsystem.util.OiButtonDimens
import com.ddd.oi.presentation.core.navigation.MainTabRoute
import com.ddd.oi.presentation.core.navigation.OiNavigator
import com.ddd.oi.presentation.core.navigation.Route
import kotlinx.coroutines.launch

@Composable
fun OiApp(
    navigator: OiNavigator
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val snackbarController = rememberSnackbarController(snackBarHostState)
    val currentRoute = navigator.currentRoute

    OiScaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = OiTheme.colors.backgroundContents,
        snackbarHost = {
            OiSnackbarHost(modifier = Modifier.offset {
                val offsetY = when (currentRoute) {
                    MainTabRoute.Schedule -> 0
                    is Route.UpsertSchedule -> -(Dimens.paddingMedium + Dimens.paddingSmall + OiButtonDimens.largeHeight).roundToPx()
                    else -> -Dimens.paddingMedium.roundToPx()
                }
                IntOffset(0, offsetY)
            },hostState = snackBarHostState, controller = snackbarController)
        },
        bottomBar = {
            MainBottomBar(
                modifier = Modifier,
                visible = navigator.shouldShowBottomBar(),
                tabs = navigator.mainTabList,
                currentTab = navigator.currentTab,
                onTabSelected = navigator::navigate
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = navigator.shouldShowBottomBar(),
                enter = fadeIn() + slideIn { IntOffset(0, it.height) },
                exit = fadeOut() + slideOut { IntOffset(0, it.height) }
            ) {
                FloatingActionButton(
                    onClick = { navigator.navigateToUpsertSchedule(null, Route.UpsertSchedule()) },
                    shape = CircleShape,
                    containerColor = OiTheme.colors.iconBrand,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    modifier = Modifier.size(Dimens.fabSize)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = OiTheme.colors.textOnPrimary
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OiNavHost(
                navigator = navigator,
                onShowSnackbar = { snackbarData ->
                    coroutineScope.launch {
                        snackbarController.showSnackbar(snackbarData)
                    }
                }
            )
        }
    }
}