package com.ddd.oi.presentation.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

val lightColorScheme = OiColors(
    textBrand = TextBrand,
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    textTertiary = TextTertiary,
    textDisabled = TextDisabled,
    textOnPrimary = TextOnPrimary,
    backgroundContents = BackgroundContents,
    backgroundDisabled = BackgroundDisabled,
    backgroundError = BackgroundError,
    backgroundInfo = BackgroundInfo,
    backgroundPressed = BackgroundPressed,
    backgroundPrimary = BackgroundPrimary,
    backgroundSecondary = BackgroundSecondary,
    backgroundSelected = BackgroundSelected,
    backgroundSuccess = BackgroundSuccess,
    backgroundUnselected = BackgroundUnselected,
    backgroundWarning = BackgroundWarning,
    borderPrimary = BorderPrimary,
    borderBrand = BorderBrand,
    borderSecondary = BorderSecondary,
    iconBrand = IconBrand,
    iconDisabled = IconDisabled,
    iconError = IconError,
    iconInfo = IconInfo,
    iconOnPrimary = IconOnPrimary,
    iconPrimary = IconPrimary,
    iconSecondary = IconSecondary,
    iconSuccess = IconSuccess,
    iconTertiary = IconTertiary,
    iconWarning = IconWarning
)

val darkColorScheme = OiColors(
    textBrand = TextBrand,
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    textTertiary = TextTertiary,
    textDisabled = TextDisabled,
    textOnPrimary = TextOnPrimary,
    backgroundContents = BackgroundContents,
    backgroundDisabled = BackgroundDisabled,
    backgroundError = BackgroundError,
    backgroundInfo = BackgroundInfo,
    backgroundPressed = BackgroundPressed,
    backgroundPrimary = BackgroundPrimary,
    backgroundSecondary = BackgroundSecondary,
    backgroundSelected = BackgroundSelected,
    backgroundSuccess = BackgroundSuccess,
    backgroundUnselected = BackgroundUnselected,
    backgroundWarning = BackgroundWarning,
    borderPrimary = BorderPrimary,
    borderBrand = BorderBrand,
    borderSecondary = BorderSecondary,
    iconBrand = IconBrand,
    iconDisabled = IconDisabled,
    iconError = IconError,
    iconInfo = IconInfo,
    iconOnPrimary = IconOnPrimary,
    iconPrimary = IconPrimary,
    iconSecondary = IconSecondary,
    iconSuccess = IconSuccess,
    iconTertiary = IconTertiary,
    iconWarning = IconWarning
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