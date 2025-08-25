package com.ddd.oi.presentation.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.createBitmap

fun circleMarkerBitmap(
    sizePx: Int,
    number: Int,
    backgroundColor: Color
): Bitmap {
    val bitmap = createBitmap(sizePx, sizePx)
    val canvas = Canvas(bitmap)

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 원형 배경 그리기
    paint.color = backgroundColor.toArgb()
    val radius = sizePx / 2f
    canvas.drawCircle(radius, radius, radius - 2f, paint)

    // 테두리
    paint.color = android.graphics.Color.WHITE
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = 8f
    canvas.drawCircle(radius, radius, radius - 4f, paint)

    // 숫자쪽
    paint.color = Color.White.toArgb()
    paint.style = Paint.Style.FILL
    paint.isFakeBoldText = true
    paint.textSize = sizePx * 0.55f
    paint.textAlign = Paint.Align.CENTER

    val textBounds = Rect()
    val text = number.toString()
    paint.getTextBounds(text, 0, text.length, textBounds)

    val x = radius
    val y = radius + textBounds.height() / 2f

    canvas.drawText(text, x, y, paint)

    return bitmap
}