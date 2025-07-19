package com.ddd.oi.presentation.core.designsystem.component.oibottomsheetscaffold

import android.util.Log
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Velocity

class OiSheetState(
    initialValue: OiSheetValue = OiSheetValue.Collapsed,
) {
    val currentValue: OiSheetValue
        get() = anchoredDraggableState.currentValue



    fun requireOffset(): Float = anchoredDraggableState.requireOffset()

    internal suspend fun animateTo(
        targetValue: OiSheetValue,
    ) {
        anchoredDraggableState.animateTo(targetValue)
    }

    fun updateAnchors(anchors: DraggableAnchors<OiSheetValue>) {
        anchoredDraggableState.updateAnchors(anchors)
    }

    suspend fun settle(animationSpec: AnimationSpec<Float>) {
        anchoredDraggableState.settle(animationSpec)
    }

    var anchoredDraggableState =
        AnchoredDraggableState(
            initialValue = initialValue,
        )

    companion object {
        fun Saver(
        ): Saver<OiSheetState, OiSheetValue> =
            Saver(
                save = { it.currentValue },
                restore = { savedValue ->
                    OiSheetState(
                        initialValue = savedValue,
                    )
                }
            )
    }
}

enum class OiSheetValue {
    Collapsed,
    HalfExpanded,
    FullyExpanded,
}

internal fun consumeSwipeNestedScrollConnection(
    sheetState: OiSheetState,
    orientation: Orientation,
    onFling: (velocity: Float) -> Unit
): NestedScrollConnection = object : NestedScrollConnection {

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val delta = available.toFloat()
        val currentState = sheetState.currentValue
        if (source == NestedScrollSource.UserInput) {
            return when (currentState) {
                OiSheetValue.Collapsed -> {
                    sheetState.anchoredDraggableState.dispatchRawDelta(delta)
                        .toOffset()
                }

                OiSheetValue.HalfExpanded -> {
                    Offset.Zero
                }

                OiSheetValue.FullyExpanded -> {
                    Offset.Zero
                }
            }
        }
        return Offset.Zero
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        val currentState = sheetState.currentValue
        if (source == NestedScrollSource.UserInput) {
            return when (currentState) {
                OiSheetValue.Collapsed -> {
                    sheetState.anchoredDraggableState.dispatchRawDelta(available.toFloat())
                        .toOffset()
                }
                OiSheetValue.FullyExpanded -> {
                    Offset.Zero
                }
                OiSheetValue.HalfExpanded -> {
                    Offset.Zero
                }
            }
        }
        return Offset.Zero
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        val toFling = available.toFloat()
        val currentState = sheetState.currentValue

        return when (currentState) {
            OiSheetValue.Collapsed -> {
                onFling(toFling)
                available
            }
            OiSheetValue.FullyExpanded -> {
                Velocity.Zero
            }
            OiSheetValue.HalfExpanded -> {
                Velocity.Zero
            }
        }
    }

    private fun Float.toOffset(): Offset =
        Offset(
            x = if (orientation == Orientation.Horizontal) this else 0f,
            y = if (orientation == Orientation.Vertical) this else 0f
        )

    @JvmName("velocityToFloat")
    private fun Velocity.toFloat() = if (orientation == Orientation.Horizontal) x else y

    @JvmName("offsetToFloat")
    private fun Offset.toFloat(): Float = if (orientation == Orientation.Horizontal) x else y
}
