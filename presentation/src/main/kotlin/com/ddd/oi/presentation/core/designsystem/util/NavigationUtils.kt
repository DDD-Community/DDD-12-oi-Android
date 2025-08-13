package com.ddd.oi.presentation.core.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberThrottledNavigation(
    throttleTimeMs: Long = 500L
): (() -> Unit) -> Unit {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    
    return { action ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= throttleTimeMs) {
            lastClickTime = currentTime
            action()
        }
    }
}
