package com.ddd.oi.presentation.util

import android.graphics.Bitmap
import androidx.core.graphics.createBitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun pinMarkerBitmap(
    sizePx: Int,
    number: Int,
    backgroundColor: Color
): Bitmap {
    val bitmap = createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // 원의 크기와 위치 설정
    val circleDiameter = sizePx * 0.8f
    val circleRadius = circleDiameter / 2f
    val centerX = sizePx / 2f
    val centerY = sizePx / 2f

    // 그림자 페인트
    val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color(0x40000000).toArgb() // 반투명 검은색 그림자
        setShadowLayer(
            sizePx * 0.08f,
            0f,
            sizePx * 0.03f,
            Color(0x80000000).toArgb()
        )
    }

    // 흰색 테두리 페인트
    val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.White.toArgb()
    }

    // 보라색 원 페인트 (그라데이션)
    val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = LinearGradient(
            centerX, centerY - circleRadius,
            centerX, centerY + circleRadius,
            Color(0xFFB366F4).toArgb(), // 상단 연한 보라색
            Color(0xFF7B4BA2).toArgb(), // 하단 진한 보라색
            Shader.TileMode.CLAMP
        )
    }

    // 텍스트 페인트
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.White.toArgb()
        textSize = (circleDiameter * 0.5f) // 원 크기에 비례한 텍스트 크기
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    // 1. 그림자 그리기
    canvas.drawCircle(
        centerX,
        centerY + sizePx * 0.02f, // 약간 아래로 이동
        circleRadius + sizePx * 0.04f, // 그림자를 약간 크게
        shadowPaint
    )

    // 2. 흰색 테두리 원 그리기
    canvas.drawCircle(
        centerX,
        centerY,
        circleRadius + sizePx * 0.04f, // 테두리 두께만큼 크게
        borderPaint
    )

    // 3. 보라색 원 그리기
    canvas.drawCircle(
        centerX,
        centerY,
        circleRadius,
        circlePaint
    )

    // 4. 숫자 텍스트 그리기
    val numberText = number.toString()
    val textBounds = Rect()
    textPaint.getTextBounds(numberText, 0, numberText.length, textBounds)

    // 텍스트를 원의 중앙에 정확히 위치시키기
    val textY = centerY + textBounds.height() / 2f - textBounds.bottom / 2f
    canvas.drawText(numberText, centerX, textY, textPaint)

    return bitmap

}