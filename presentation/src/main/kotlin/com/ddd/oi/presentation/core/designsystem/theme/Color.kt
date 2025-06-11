package com.ddd.oi.presentation.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val TextPrimary = Color(0xFF171717)
val TextSecondary = Color(0XFF262626)
val TextTertiary = Color(0xFFA1A1A1)
val TextDisabled = Color(0xFFD4D4D4)
val TextOnPrimary = Color(0xFFFFFFFF)
val TextOnSecondary = Color(0xFF039D58)

val BackgroundBrand = Color(0xFF039D58)
val BackgroundBrandSecondary = Color(0xFFE5F8EE)
val BackgroundPrimary = Color(0xFFFFFFFF)
val BackgroundSecondary = Color(0xFFFAFAFA)
val BackgroundTertiary = Color(0xFFE5E5E5)

val BorderPrimary = Color(0xFFE5E5E5)
val BorderBrand = Color(0xFF039D58)

val IconPrimary = Color(0xFF171717)
val IconSecondary = Color(0xFF404040)
val IconTertiary = Color(0xFFA1A1A1)
val IconDisabled = Color(0xFFD4D4D4)
val IconOnPrimary = Color(0xFFFFFFFF)
val IconOnSecondary = Color(0xFF039D58)

val error = Color(0xFFFB2C36)
val caution = Color(0xFFF0B100)
val success = Color(0xFF00C950)
val info = Color(0xFF2B7FFF)
val primary = Color(0xFF039D58)

@Immutable
data class OiColors(
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textDisabled: Color,
    val textOnPrimary: Color,
    val textOnSecondary: Color,

    val backgroundBrand: Color,
    val backgroundBrandSecondary: Color,
    val backgroundPrimary: Color,
    val backgroundSecondary: Color,
    val backgroundTertiary: Color,

    val borderPrimary: Color,
    val borderBrand: Color,

    val iconPrimary: Color,
    val iconSecondary: Color,
    val iconTertiary: Color,
    val iconDisabled: Color,
    val iconOnPrimary: Color,
    val iconOnSecondary: Color,

    val error: Color,
    val caution: Color,
    val success: Color,
    val info: Color,
    val primary: Color
)

val LocalColors = staticCompositionLocalOf {
    lightColorScheme
}
