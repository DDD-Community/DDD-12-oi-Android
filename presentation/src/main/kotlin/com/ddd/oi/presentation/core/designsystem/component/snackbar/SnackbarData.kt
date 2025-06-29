package com.ddd.oi.presentation.core.designsystem.component.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.ddd.oi.presentation.R

enum class SnackbarType {
    SUCCESS,
    WARNING,
}

@Immutable
data class OiSnackbarData(
    val message: String,
    val type: SnackbarType? = null,
) {
    @DrawableRes
    fun getIcon(): Int? = when (type) {
        SnackbarType.SUCCESS -> R.drawable.ic_success
        SnackbarType.WARNING -> R.drawable.ic_warning
        null -> null
    }
}