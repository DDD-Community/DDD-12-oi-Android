package com.ddd.oi.presentation.core.designsystem.component.oibottomsheetscaffold

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxOfOrNull
import com.ddd.oi.presentation.core.designsystem.theme.white
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun OiBottomSheetScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: OiBottomSheetScaffoldState = rememberOiBottomSheetScaffoldState(),
    sheetDragHandle: @Composable () -> Unit,
    topBar: @Composable () -> Unit,
    titleContent: @Composable () -> Unit,
    mapContent: @Composable () -> Unit,
    sheetContent: @Composable () -> Unit,
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    containerColor: Color = white,
    contentColor: Color = contentColorFor(containerColor)
) {
    OiBottomSheetScaffoldLayout(
        modifier = modifier,
        topBar = topBar,
        titleContent = titleContent,
        mapContent = mapContent,
        bottomSheet = {
            OiStandardBottomSheet(
                state = scaffoldState.bottomSheetState,
                containerColor = containerColor,
                contentColor = contentColor,
                dragHandle = sheetDragHandle,
                content = sheetContent,
                oiSheetValue = scaffoldState.bottomSheetState.currentValue
            )
        },
        sheetOffset = { scaffoldState.bottomSheetState.requireOffset() },
        snackbarHost = { snackbarHost(scaffoldState.snackbarHostState) },
        sheetState = scaffoldState.bottomSheetState
    )
}

@Stable
class OiBottomSheetScaffoldState(
    val bottomSheetState: OiSheetState,
    val snackbarHostState: SnackbarHostState
)

@Composable
fun rememberOiBottomSheetScaffoldState(
    bottomSheetState: OiSheetState = rememberOiStandardBottomSheetState(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): OiBottomSheetScaffoldState {
    return remember(bottomSheetState, snackbarHostState) {
        OiBottomSheetScaffoldState(
            bottomSheetState = bottomSheetState,
            snackbarHostState = snackbarHostState
        )
    }
}

@Composable
fun rememberOiStandardBottomSheetState(
    skipHiddenState: Boolean = true,
) =
    rememberOiSheetState(
        skipHiddenState = skipHiddenState,
    )

@Composable
internal fun rememberOiSheetState(
    confirmValueChange: (OiSheetValue) -> Boolean = { true },
    initialValue: OiSheetValue = OiSheetValue.Collapsed,
    skipHiddenState: Boolean = false,
): OiSheetState {
    return rememberSaveable(
        confirmValueChange,
        skipHiddenState,
        saver =
            OiSheetState.Saver()
    ) {
        OiSheetState(
            initialValue = initialValue,
        )
    }
}

@Composable
internal fun OiStandardBottomSheet(
    state: OiSheetState,
    containerColor: Color,
    contentColor: Color,
    oiSheetValue: OiSheetValue,
    dragHandle: @Composable (() -> Unit)?,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val orientation = Orientation.Vertical

    val cornerRadius by animateDpAsState(
        targetValue = if (oiSheetValue == OiSheetValue.FullyExpanded) 0.dp else 24.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "cornerRadius"
    )

    val nestedScroll = Modifier.nestedScroll(
        remember(state.anchoredDraggableState) {
            consumeSwipeNestedScrollConnection(
                sheetState = state,
                orientation = orientation,
                onFling = { velocity ->
                    scope.launch {
                        val currentState = state.currentValue
                        val currentOffset = state.requireOffset()

                        val targetValue = when {
                            currentState == OiSheetValue.Collapsed -> {
                                // Collapsed 상태에서 스크롤 또는 fling 시 Half로 이동
                                if (velocity < 0 || currentOffset < state.anchoredDraggableState.anchors.positionOf(
                                        OiSheetValue.Collapsed
                                    )
                                ) {
                                    OiSheetValue.HalfExpanded
                                } else {
                                    OiSheetValue.Collapsed
                                }
                            }

                            else -> state.anchoredDraggableState.anchors.closestAnchor(currentOffset)
                        }

                        if (targetValue != null) {
                            state.animateTo(targetValue)
                        }
                    }
                }
            )
        }
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .then(nestedScroll)
            .clipToBounds(),
        color = containerColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius),
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (dragHandle != null) {
                Box(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .anchoredDraggable(
                            state = state.anchoredDraggableState,
                            orientation = orientation,
                            enabled = true,
                            flingBehavior = AnchoredDraggableDefaults.flingBehavior(state.anchoredDraggableState)
                        )
                ) {
                    dragHandle()
                }
            }
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
        }
    }
}

@Composable
internal fun OiBottomSheetScaffoldLayout(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit,
    titleContent: @Composable () -> Unit,
    mapContent: @Composable () -> Unit,
    bottomSheet: @Composable () -> Unit,
    snackbarHost: @Composable () -> Unit,
    sheetOffset: () -> Float,
    sheetState: OiSheetState,
) {
    Layout(
        modifier = modifier.background(white),
        contents =
            listOf<@Composable () -> Unit>(
                topBar,
                titleContent,
                mapContent,
                bottomSheet,
                snackbarHost
            )
    ) { (topBarMeasurable, titleMeasurable, mapMeasurable, sheetMeasurable, snackbarMeasurable),
        constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val topBarPlaceable = topBarMeasurable.fastMap { it.measure(looseConstraints) }
        val topBarHeight = topBarPlaceable.fastMaxOfOrNull { it.height } ?: 0

        val collapsedSheetHeight = 200.dp.toPx()
        val halfExpandedSheetHeight = layoutHeight / 2f

        val newAnchors = DraggableAnchors {
            OiSheetValue.Collapsed at layoutHeight - collapsedSheetHeight
            OiSheetValue.HalfExpanded at layoutHeight - halfExpandedSheetHeight
            OiSheetValue.FullyExpanded at topBarHeight.toFloat()
        }
        sheetState.updateAnchors(newAnchors)

        val currentOffset = sheetOffset()

        // 바텀시트 상태에 따라 최대 높이 계산 (가장 큰 값 사용)
        val sheetMaxHeight = (layoutHeight - currentOffset).coerceAtLeast(0f).toInt()
        val sheetConstraints = looseConstraints.copy(maxHeight = sheetMaxHeight)
        val sheetPlaceable = sheetMeasurable.fastMap { it.measure(sheetConstraints) }

        val titlePlaceable = titleMeasurable.fastMap { it.measure(looseConstraints) }
        val titleHeight = titlePlaceable.fastMaxOfOrNull { it.height } ?: 0

        val collapsedOffset = layoutHeight - collapsedSheetHeight
        val halfExpandedOffset = layoutHeight - halfExpandedSheetHeight
        val transitionRange = (collapsedOffset - halfExpandedOffset).coerceAtLeast(1f)

        val titleProgress =
            ((collapsedOffset - currentOffset) / transitionRange).coerceIn(0f, 1f)

        val titleY = topBarHeight - (titleHeight * titleProgress).roundToInt()
        val mapY = topBarHeight + titleHeight - (titleHeight * titleProgress).roundToInt()

        val mapPlaceable = mapMeasurable.fastMap {
            it.measure(looseConstraints.copy(maxHeight = layoutHeight - collapsedSheetHeight.toInt() - topBarHeight - titleHeight + 24.dp.toPx().toInt()))
        }

        val snackbarPlaceable = snackbarMeasurable.fastMap { it.measure(looseConstraints) }

        layout(layoutWidth, layoutHeight) {
            val sheetWidth = sheetPlaceable.fastMaxOfOrNull { it.width } ?: 0
            val sheetOffsetX = Integer.max(0, (layoutWidth - sheetWidth) / 2)

            mapPlaceable.fastForEach {
                it.placeRelative(0, mapY)
            }

            titlePlaceable.fastForEach {
                it.placeRelativeWithLayer(
                    x = 0,
                    y = titleY
                ) {
                    alpha = 1f - titleProgress
                }
            }

            sheetPlaceable.fastForEach {
                it.placeRelative(sheetOffsetX, currentOffset.roundToInt())
            }

            topBarPlaceable.fastForEach { it.placeRelative(0, 0) }

            val snackbarWidth = snackbarPlaceable.fastMaxOfOrNull { it.width } ?: 0
            val snackbarHeight = snackbarPlaceable.fastMaxOfOrNull { it.height } ?: 0
            val snackbarOffsetX = (layoutWidth - snackbarWidth) / 2
            val snackbarOffsetY = currentOffset.roundToInt() - snackbarHeight
            snackbarPlaceable.fastForEach {
                it.placeRelative(snackbarOffsetX, snackbarOffsetY)
            }
        }
    }
}