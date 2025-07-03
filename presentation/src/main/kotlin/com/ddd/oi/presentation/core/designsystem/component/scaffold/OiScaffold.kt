package com.ddd.oi.presentation.core.designsystem.component.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import com.ddd.oi.presentation.core.navigation.Route

@Composable
fun OiScaffold(
    modifier: Modifier = Modifier,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.Center,
    isFloatingActionButtonDocked: Boolean = false,
    containerColor: Color = Color.Transparent,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable (PaddingValues) -> Unit,
) {
    val safeInsets = remember(contentWindowInsets) { MutableWindowInsets(contentWindowInsets) }



    Surface(
        modifier =
            modifier.onConsumedWindowInsetsChanged { consumedWindowInsets ->
                safeInsets.insets = contentWindowInsets.exclude(consumedWindowInsets)
            },
        color = containerColor,
        contentColor = contentColor,
    ) {
        ScaffoldLayout(
            isFabDocked = isFloatingActionButtonDocked,
            fabPosition = floatingActionButtonPosition,
            topBar = topBar,
            content = content,
            contentWindowInsets = safeInsets,
            snackbar = snackbarHost,
            fab = floatingActionButton,
            bottomBar = bottomBar,
        )
    }
}

@Composable
@UiComposable
private fun ScaffoldLayout(
    isFabDocked: Boolean,
    fabPosition: FabPosition,
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    snackbar: @Composable () -> Unit,
    fab: @Composable () -> Unit,
    contentWindowInsets: WindowInsets,
    bottomBar: @Composable () -> Unit,
) {
    // Create the backing value for the content padding
    // These values will be updated during measurement, but before subcomposing the body content
    // Remembering and updating a single PaddingValues avoids needing to recompose when the values
    // change
    val contentPadding = remember {
        object : PaddingValues {
            var paddingHolder by mutableStateOf(PaddingValues(0.dp))

            override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp =
                paddingHolder.calculateLeftPadding(layoutDirection)

            override fun calculateTopPadding(): Dp = paddingHolder.calculateTopPadding()

            override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp =
                paddingHolder.calculateRightPadding(layoutDirection)

            override fun calculateBottomPadding(): Dp = paddingHolder.calculateBottomPadding()
        }
    }

    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val topBarPlaceables =
            subcompose(ScaffoldLayoutContent.TopBar, topBar).fastMap {
                it.measure(looseConstraints)
            }

        val topBarHeight = topBarPlaceables.fastMaxBy { it.height }?.height ?: 0

        val snackbarPlaceables =
            subcompose(ScaffoldLayoutContent.Snackbar, snackbar).fastMap {
                // respect only bottom and horizontal for snackbar and fab
                val leftInset = contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
                val rightInset =
                    contentWindowInsets.getRight(this@SubcomposeLayout, layoutDirection)
                val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)
                // offset the snackbar constraints by the insets values
                it.measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))
            }

        val snackbarHeight = snackbarPlaceables.fastMaxBy { it.height }?.height ?: 0

        val fabPlaceables =
            subcompose(ScaffoldLayoutContent.Fab, fab).fastMap { measurable ->
                // respect only bottom and horizontal for snackbar and fab
                val leftInset = contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
                val rightInset =
                    contentWindowInsets.getRight(this@SubcomposeLayout, layoutDirection)
                val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)
                measurable.measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))
            }

        val fabPlacement =
            if (fabPlaceables.isNotEmpty()) {
                val fabWidth = fabPlaceables.fastMaxBy { it.width }?.width ?: 0
                val fabHeight = fabPlaceables.fastMaxBy { it.height }?.height ?: 0
                // FAB distance from the left of the layout, taking into account LTR / RTL
                if (fabWidth != 0 && fabHeight != 0) {
                    val fabLeftOffset =
                        when (fabPosition) {
                            FabPosition.Start -> {
                                if (layoutDirection == LayoutDirection.Ltr) {
                                    FabSpacing.roundToPx()
                                } else {
                                    layoutWidth - FabSpacing.roundToPx() - fabWidth
                                }
                            }

                            FabPosition.End -> {
                                if (layoutDirection == LayoutDirection.Ltr) {
                                    layoutWidth - FabSpacing.roundToPx() - fabWidth
                                } else {
                                    FabSpacing.roundToPx()
                                }
                            }

                            else -> (layoutWidth - fabWidth) / 2
                        }

                    FabPlacement(
                        isDocked = isFabDocked,
                        left = fabLeftOffset,
                        width = fabWidth,
                        height = fabHeight,
                    )
                } else {
                    null
                }
            } else {
                null
            }

        val bottomBarPlaceables =
            subcompose(ScaffoldLayoutContent.BottomBar) {
                CompositionLocalProvider(
                    LocalFabPlacement provides fabPlacement,
                    content = bottomBar,
                )
            }
                .fastMap { it.measure(looseConstraints) }

        val bottomBarHeight = bottomBarPlaceables.fastMaxBy { it.height }?.height
        val fabOffsetFromBottom =
            fabPlacement?.let {
                if (bottomBarHeight == null) {
                    it.height +
                            FabSpacing.roundToPx() +
                            contentWindowInsets.getBottom(this@SubcomposeLayout)
                } else {
                    if (isFabDocked) {
                        // Total height is the bottom bar height + half the FAB height
                        bottomBarHeight + (it.height / 2)
                    } else {
                        // Total height is the bottom bar height + the FAB height + the padding
                        // between the FAB and bottom bar
                        bottomBarHeight + it.height + FabSpacing.roundToPx()
                    }
                }
            }

        val snackbarOffsetFromBottom =
            if (snackbarHeight != 0) {
                snackbarHeight +
                        (fabOffsetFromBottom?.let { it + 8.dp.roundToPx() }
                            ?: bottomBarHeight
                            ?: contentWindowInsets.getBottom(this@SubcomposeLayout))
            } else {
                0
            }

        // Update the backing state for the content padding before subcomposing the body
        val insets = contentWindowInsets.asPaddingValues(this)
        contentPadding.paddingHolder =
            PaddingValues(
                top =
                    if (topBarPlaceables.isEmpty()) {
                        insets.calculateTopPadding()
                    } else {
                        0.dp
                    },
                bottom =
                    if (bottomBarPlaceables.isEmpty() || bottomBarHeight == null) {
                        insets.calculateBottomPadding()
                    } else {
                        bottomBarHeight.toDp()
                    },
                start = insets.calculateStartPadding(layoutDirection),
                end = insets.calculateEndPadding(layoutDirection),
            )

        val bodyContentHeight = layoutHeight - topBarHeight

        val bodyContentPlaceables =
            subcompose(ScaffoldLayoutContent.MainContent) { content(contentPadding) }
                .fastMap { it.measure(looseConstraints.copy(maxHeight = bodyContentHeight)) }

        layout(layoutWidth, layoutHeight) {
            // Placing to control drawing order to match default elevation of each placeable

            bodyContentPlaceables.fastForEach { it.place(0, topBarHeight) }
            topBarPlaceables.fastForEach { it.place(0, 0) }
            snackbarPlaceables.fastForEach { it.place(0, layoutHeight - snackbarOffsetFromBottom) }
            // The bottom bar is always at the bottom of the layout
            bottomBarPlaceables.fastForEach { it.place(0, layoutHeight - (bottomBarHeight ?: 0)) }
            // Explicitly not using placeRelative here as `leftOffset` already accounts for RTL
            fabPlaceables.fastForEach {
                it.place(fabPlacement?.left ?: 0, layoutHeight - (fabOffsetFromBottom ?: 0))
            }
        }
    }
}

object ScaffoldDefaults {
    /** Default insets to be used and consumed by the scaffold content slot */
    val contentWindowInsets: WindowInsets
        @Composable get() = WindowInsets.systemBars
}

internal class FabPlacement(val isDocked: Boolean, val left: Int, val width: Int, val height: Int)

internal val LocalFabPlacement = staticCompositionLocalOf<FabPlacement?> { null }

private val FabSpacing = 16.dp

private enum class ScaffoldLayoutContent {
    TopBar,
    MainContent,
    Snackbar,
    Fab,
    BottomBar,
}

private class MutableWindowInsets(
    initialInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
) : WindowInsets {
    /**
     * The [WindowInsets] that are used for [left][getLeft], [top][getTop], [right][getRight], and
     * [bottom][getBottom] values.
     */
    var insets by mutableStateOf(initialInsets)

    override fun getLeft(density: Density, layoutDirection: LayoutDirection): Int =
        insets.getLeft(density, layoutDirection)

    override fun getTop(density: Density): Int = insets.getTop(density)

    override fun getRight(density: Density, layoutDirection: LayoutDirection): Int =
        insets.getRight(density, layoutDirection)

    override fun getBottom(density: Density): Int = insets.getBottom(density)
}