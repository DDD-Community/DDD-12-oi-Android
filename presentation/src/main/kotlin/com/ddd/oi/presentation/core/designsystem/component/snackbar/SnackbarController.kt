package com.ddd.oi.presentation.core.designsystem.component.snackbar

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Stable
class SnackbarController(
    private val hostState: SnackbarHostState,
) {
    var currentSnackbarData by mutableStateOf<OiSnackbarData?>(null)
        private set

    suspend fun showSnackbar(data: OiSnackbarData) {
        currentSnackbarData = data
        hostState.currentSnackbarData?.dismiss()
        hostState.showSnackbar(data.message)
    }
}

@Composable
fun rememberSnackbarController(
    hostState: SnackbarHostState,
): SnackbarController {
    return remember(hostState) {
        SnackbarController(hostState)
    }
}