package com.ddd.oi.presentation.core.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

val lightColorScheme = OiColors(
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    textTertiary = TextTertiary,
    textDisabled = TextDisabled,
    textonPrimary = TextonPrimary,
    textonSecondary = TextonSecondary,
    backgroundBrand = BackgroundBrand,
    backgroundBrandSecondary = BackgroundBrandSecondary,
    backgroundPrimary = BackgroundPrimary,
    backgroundSecondary = BackgroundSecondary,
    backgroundTetiary = BackgroundTetiary,
    borderPrimary = BorderPrimary,
    borderBrand = BorderBrand,
    iconPrimary = IconPrimary,
    iconSecondary = IconSecondary,
    iconTetiary = IconTetiary,
    iconDisabled = IconDisabled,
    icononPrimary = IcononPrimary,
    icononSecondary = IcononSecondary,
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
    textonPrimary = TextonPrimary,
    textonSecondary = TextonSecondary,
    backgroundBrand = BackgroundBrand,
    backgroundBrandSecondary = BackgroundBrandSecondary,
    backgroundPrimary = BackgroundPrimary,
    backgroundSecondary = BackgroundSecondary,
    backgroundTetiary = BackgroundTetiary,
    borderPrimary = BorderPrimary,
    borderBrand = BorderBrand,
    iconPrimary = IconPrimary,
    iconSecondary = IconSecondary,
    iconTetiary = IconTetiary,
    iconDisabled = IconDisabled,
    icononPrimary = IcononPrimary,
    icononSecondary = IcononSecondary,
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