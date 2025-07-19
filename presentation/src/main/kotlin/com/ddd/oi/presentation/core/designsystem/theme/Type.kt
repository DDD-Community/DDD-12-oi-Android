package com.ddd.oi.presentation.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val PretendardStyle = TextStyle(
    fontFamily = Pretendard,
)

internal val Typography = OiTypography(
    headlineLargeBold = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineLargeSemiBold = PretendardStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineMediumRegular = PretendardStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    ),
    headlineMediumBold = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    ),
    headlineSmallBold = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.sp
    ),
    headLineSmallSemibold = PretendardStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.sp
    ),
    bodyLargeSemibold = PretendardStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.8.sp,
        letterSpacing = 0.sp
    ),
    bodyLargeMedium = PretendardStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.8.sp,
        letterSpacing = 0.sp
    ),
    bodyLargeRegular = PretendardStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.8.sp,
        letterSpacing = 0.sp
    ),
    bodyMediumSemibold = PretendardStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.2.sp,
        letterSpacing = 0.sp
    ),
    bodyMediumMedium = PretendardStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.2.sp,
        letterSpacing = 0.sp
    ),
    bodyMediumRegular = PretendardStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.2.sp,
        letterSpacing = 0.sp
    ),
    bodySmallSemibold = PretendardStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 15.6.sp,
        letterSpacing = 0.sp
    ),
    bodySmallMedium = PretendardStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 15.6.sp,
        letterSpacing = 0.sp
    ),
    bodySmallRegular = PretendardStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 15.6.sp,
        letterSpacing = 0.sp
    ),
    bodyXSmallSemibold = PretendardStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 14.3.sp,
        letterSpacing = 0.sp
    ),
    bodyXSmallMedium = PretendardStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.3.sp,
        letterSpacing = 0.sp
    ),
    bodyXSmallRegular = PretendardStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 14.3.sp,
        letterSpacing = 0.sp
    )
)

@Immutable
data class OiTypography(
    val headlineLargeBold: TextStyle,
    val headlineLargeSemiBold: TextStyle,

    val headlineMediumBold: TextStyle,
    val headlineMediumRegular: TextStyle,
    val headlineSmallBold: TextStyle,
    val headLineSmallSemibold: TextStyle,

    val bodyLargeSemibold: TextStyle,
    val bodyLargeMedium: TextStyle,
    val bodyLargeRegular: TextStyle,
    val bodyMediumSemibold: TextStyle,
    val bodyMediumMedium: TextStyle,
    val bodyMediumRegular: TextStyle,
    val bodySmallSemibold: TextStyle,
    val bodySmallMedium: TextStyle,
    val bodySmallRegular: TextStyle,
    val bodyXSmallSemibold: TextStyle,
    val bodyXSmallMedium: TextStyle,
    val bodyXSmallRegular: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    OiTypography(
        headlineSmallBold = PretendardStyle,
        headlineMediumBold = PretendardStyle,
        headlineMediumRegular = PretendardStyle,
        headlineLargeSemiBold = PretendardStyle,
        headlineLargeBold = PretendardStyle,
        headLineSmallSemibold = PretendardStyle,
        bodyLargeSemibold = PretendardStyle,
        bodyLargeMedium = PretendardStyle,
        bodyLargeRegular = PretendardStyle,
        bodyMediumSemibold = PretendardStyle,
        bodyMediumMedium = PretendardStyle,
        bodyMediumRegular = PretendardStyle,
        bodySmallSemibold = PretendardStyle,
        bodySmallMedium = PretendardStyle,
        bodySmallRegular = PretendardStyle,
        bodyXSmallSemibold = PretendardStyle,
        bodyXSmallMedium = PretendardStyle,
        bodyXSmallRegular = PretendardStyle,
    )
}
