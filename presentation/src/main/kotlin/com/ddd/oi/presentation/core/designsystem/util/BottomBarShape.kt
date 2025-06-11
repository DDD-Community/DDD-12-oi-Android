package com.ddd.oi.presentation.core.designsystem.util

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.unit.Dp

/**
 * 가운데 곡선이 있는 커스텀 바텀 바.
 * [GenericShape](https://developer.android.com/reference/kotlin/androidx/compose/foundation/shape/GenericShape/)
 * @return 가운데 커브가 들어간 바텀 바
 */
fun bottomBarShape(
    widthDp: Dp = BottomBarShapeDimens.width,
    heightDp: Dp = BottomBarShapeDimens.height,
    point1Dp: Dp = BottomBarShapeDimens.curveStart,
    point2Dp: Dp = BottomBarShapeDimens.curveEnd
) = GenericShape { size, _ ->
    val widthPx = widthDp.value
    val heightPx = heightDp.value
    val point1Px = point1Dp.value
    val point2Px = point2Dp.value

    reset()
    moveTo(0f, 0f)

    // 왼쪽 직선
    lineTo(size.width / 2 - widthPx, 0f)

    // 왼쪽 곡선
    cubicTo(
        size.width / 2 - point1Px, 0f,
        size.width / 2 - point2Px, heightPx,
        size.width / 2, heightPx
    )
    // 오른쪽 곡선
    cubicTo(
        size.width / 2 + point2Px, heightPx,
        size.width / 2 + point1Px, 0f,
        size.width / 2 + widthPx, 0f
    )


    lineTo(size.width, 0f)
    lineTo(size.width, size.height)
    lineTo(0f, size.height)
    close()
}