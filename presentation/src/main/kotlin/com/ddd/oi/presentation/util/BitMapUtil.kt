package com.ddd.oi.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.compositionContext
import androidx.core.graphics.createBitmap
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme

fun createBitmapFromComposable(
    context: Context,
    parentContext: CompositionContext,
    widthPx: Int,
    heightPx: Int,
    content: @Composable () -> Unit
): Bitmap {
    val composeView = ComposeView(context)
    composeView.compositionContext = parentContext
    composeView.setContent {
        OiTheme {
            content()
        }
    }


    val widthSpec = View.MeasureSpec.makeMeasureSpec(widthPx, View.MeasureSpec.EXACTLY)
    val heightSpec = View.MeasureSpec.makeMeasureSpec(heightPx, View.MeasureSpec.EXACTLY)

    composeView.measure(widthSpec, heightSpec)

    val measuredWidth = composeView.measuredWidth
    val measuredHeight = composeView.measuredHeight
    Log.d("BitmapUtil", "Final measured size: ${measuredWidth}x$measuredHeight")

    composeView.layout(0, 0, measuredWidth, measuredHeight)
    val bitmap = createBitmap(measuredWidth, measuredHeight)
    val canvas = Canvas(bitmap)
    composeView.draw(canvas)

    return bitmap
}