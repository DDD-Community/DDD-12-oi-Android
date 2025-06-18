package com.ddd.oi.presentation.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val teal400 = Color(0xFF00D5BE)

val indigo400 = Color(0xFF7C86FF)

val yellow400 = Color(0xFFFDC700)

val rose400 = Color(0xFFFF637E)

val lime400 = Color(0xFF9AE600)

val asamo700 = Color(0xFF039D58)
val asamo900 = Color(0xFF095E3A)

val neutral100 = Color(0xFFF5F5F5)
val neutral300 = Color(0xFFD4D4D4)
val neutral800 = Color(0xFF262626)

val white = Color(0xFFFFFFFF)

val TextPrimary = Color(0xFF171717)
val TextSecondary = Color(0XFF262626)
val TextTertiary = Color(0xFFA1A1A1)
val TextDisabled = Color(0xFFD4D4D4)
val TextOnPrimary = white
val TextBrand = Color(0xFF039D58)

val BackgroundContents = Color(0xFFFAFAFA)
val BackgroundDisabled = Color(0xFFE5E5E5)
val BackgroundError = Color(0xFFFB2C36)
val BackgroundInfo = Color(0xFF2B7FFF)
val BackgroundPressed = Color(0xFF097244)
val BackgroundPrimary = Color(0xFF00A75C)
val BackgroundSecondary = Color(0xFFE5F8EE)
val BackgroundSelected = Color(0xFF262626)
val BackgroundSuccess = Color(0xFF00C950)
val BackgroundUnselected = Color(0xFFF5F5F5)
val BackgroundWarning = Color(0xFFFE9A00)

val BorderPrimary = Color(0xFFE5E5E5)
val BorderBrand = Color(0xFF00A75C)
val BorderSecondary = Color(0xFFF5F5F5)

val IconBrand = Color(0xFF00A75C)
val IconDisabled = Color(0xFFE5E5E5)
val IconError = Color(0xFFFB2C36)
val IconInfo = Color(0xFF2B7FFF)
val IconOnPrimary = white
val IconPrimary = Color(0xFF171717)
val IconSecondary = Color(0xFF404040)
val IconSuccess = Color(0xFF00C950)
val IconTertiary = Color(0xFFA1A1A1)
val IconWarning = Color(0xFFFE9A00)

val primary = asamo700

@Immutable
data class OiColors(
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textDisabled: Color,
    val textOnPrimary: Color,
    val textBrand: Color,

    val backgroundContents: Color,
    val backgroundDisabled: Color,
    val backgroundError: Color,
    val backgroundInfo: Color,
    val backgroundPressed: Color,
    val backgroundPrimary: Color,
    val backgroundSecondary: Color,
    val backgroundSelected: Color,
    val backgroundSuccess: Color,
    val backgroundUnselected: Color,
    val backgroundWarning: Color,


    val borderPrimary: Color,
    val borderBrand: Color,
    val borderSecondary: Color,

    val iconBrand: Color,
    val iconDisabled: Color,
    val iconError: Color,
    val iconInfo: Color,
    val iconOnPrimary: Color,
    val iconPrimary: Color,
    val iconSecondary: Color,
    val iconSuccess: Color,
    val iconTertiary: Color,
    val iconWarning: Color,
)

val LocalColors = staticCompositionLocalOf {
    lightColorScheme
}
