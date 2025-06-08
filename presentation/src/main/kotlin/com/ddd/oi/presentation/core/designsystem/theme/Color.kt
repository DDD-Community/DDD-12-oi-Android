package com.ddd.oi.presentation.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val TextPrimary = Color(0xFF171717)
val TextSecondary = Color(0XFF262626)
val TextTertiary = Color(0xFFA1A1A1)
val TextDisabled = Color(0xFFD4D4D4)
val TextonPrimary = Color(0xFFFFFFFF)
val TextonSecondary = Color(0xFF039D58)

val BackgroundBrand = Color(0xFF039D58)
val BackgroundBrandSecondary = Color(0xFFE5F8EE)
val BackgroundPrimary = Color(0xFF171717)
val BackgroundSecondary = Color(0xFFFAFAFA)
val BackgroundTetiary = Color(0xFFE5E5E5)

val BorderPrimary = Color(0xFFE5E5E5)
val BorderBrand = Color(0xFF039D58)

val IconPrimary = Color(0xFF171717)
val IconSecondary = Color(0xFF404040)
val IconTetiary = Color(0xFFA1A1A1)
val IconDisabled = Color(0xFFD4D4D4)
val IcononPrimary = Color(0xFFFFFFFF)
val IcononSecondary = Color(0xFF039D58)

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
    val textonPrimary: Color,
    val textonSecondary: Color,

    val backgroundBrand: Color,
    val backgroundBrandSecondary: Color,
    val backgroundPrimary: Color,
    val backgroundSecondary: Color,
    val backgroundTetiary: Color,

    val borderPrimary: Color,
    val borderBrand: Color,

    val iconPrimary: Color,
    val iconSecondary: Color,
    val iconTetiary: Color,
    val iconDisabled: Color,
    val icononPrimary: Color,
    val icononSecondary: Color,

    val error: Color,
    val caution: Color,
    val success: Color,
    val info: Color,
    val primary: Color
)

val LocalColors = staticCompositionLocalOf {
    OiColors(
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
}
