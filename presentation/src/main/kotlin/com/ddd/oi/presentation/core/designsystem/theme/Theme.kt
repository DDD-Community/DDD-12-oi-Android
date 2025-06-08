package com.ddd.oi.presentation.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

val lightColorScheme = OiColors(
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    textTertiary = TextTertiary,
    textDisabled = TextDisabled,
    textOnPrimary = TextOnPrimary,
    textOnSecondary = TextOnSecondary,
    backgroundBrand = BackgroundBrand,
    backgroundBrandSecondary = BackgroundBrandSecondary,
    backgroundPrimary = BackgroundPrimary,
    backgroundSecondary = BackgroundSecondary,
    backgroundTertiary = BackgroundTertiary,
    borderPrimary = BorderPrimary,
    borderBrand = BorderBrand,
    iconPrimary = IconPrimary,
    iconSecondary = IconSecondary,
    iconTertiary = IconTetiary,
    iconDisabled = IconDisabled,
    iconOnPrimary = IcononPrimary,
    iconOnSecondary = IcononSecondary,
    error = error,
    caution = caution,
    success = success,
    info = info,
    primary = primary
)

val darkColorScheme = OiColors(
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    textTertiary = TextTertiary,
    textDisabled = TextDisabled,
    textOnPrimary = TextOnPrimary,
    textOnSecondary = TextOnSecondary,
    backgroundBrand = BackgroundBrand,
    backgroundBrandSecondary = BackgroundBrandSecondary,
    backgroundPrimary = BackgroundPrimary,
    backgroundSecondary = BackgroundSecondary,
    backgroundTertiary = BackgroundTertiary,
    borderPrimary = BorderPrimary,
    borderBrand = BorderBrand,
    iconPrimary = IconPrimary,
    iconSecondary = IconSecondary,
    iconTertiary = IconTetiary,
    iconDisabled = IconDisabled,
    iconOnPrimary = IcononPrimary,
    iconOnSecondary = IcononSecondary,
    error = error,
    caution = caution,
    success = success,
    info = info,
    primary = primary
)

@Composable
fun OiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme

    CompositionLocalProvider(
        LocalTypography provides Typography,
        LocalColors provides colorScheme
    ) {
        MaterialTheme(
            content = content
        )
    }
}

object OiTheme {
    val typography: OiTypography
        @Composable
        get() = LocalTypography.current

    val colors: OiColors
        @Composable
        get() = LocalColors.current
}