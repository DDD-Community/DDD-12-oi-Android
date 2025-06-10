package com.ddd.oi.presentation.core.designsystem.util

import androidx.compose.foundation.shape.GenericShape

/**
 * 가운데 곡선이 있는 커스텀 바텀 바.
 * 참고 자료: https://victorbrandalise.com/curved-bottom-bar-in-jetpack-compose/
 * @return 가운데 커브가 들어간 바텀 바
 */
fun bottomBarShape() = GenericShape { size, _ ->
    reset()

    moveTo(0f, 0f)

    val width = 150f
    val height = 90f

    val point1 = 75f
    val point2 = 85f

    lineTo(size.width / 2 - width, 0f)

    cubicTo(
        size.width / 2 - point1, 0f,
        size.width / 2 - point2, height,
        size.width / 2, height
    )

    cubicTo(
        size.width / 2 + point2, height,
        size.width / 2 + point1, 0f,
        size.width / 2 + width, 0f
    )

    lineTo(size.width / 2 + width, 0f)

    lineTo(size.width, 0f)
    lineTo(size.width, size.height)
    lineTo(0f, size.height)

    close()
}