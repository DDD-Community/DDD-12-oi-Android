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
    bodyLargeBold = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
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
    bodyMediumBold = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
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
    bodySmallBold = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
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
    bodyXSmallBold = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
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
    val headlineMediumBold: TextStyle,
    val headlineSmallBold: TextStyle,

    val bodyLargeBold: TextStyle,
    val bodyLargeMedium: TextStyle,
    val bodyLargeRegular: TextStyle,
    val bodyMediumBold: TextStyle,
    val bodyMediumMedium: TextStyle,
    val bodyMediumRegular: TextStyle,
    val bodySmallBold: TextStyle,
    val bodySmallMedium: TextStyle,
    val bodySmallRegular: TextStyle,
    val bodyXSmallBold: TextStyle,
    val bodyXSmallMedium: TextStyle,
    val bodyXSmallRegular: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    OiTypography(
        headlineSmallBold = PretendardStyle,
        headlineMediumBold = PretendardStyle,
        headlineLargeBold = PretendardStyle,
        bodyLargeBold = PretendardStyle,
        bodyLargeMedium = PretendardStyle,
        bodyLargeRegular = PretendardStyle,
        bodyMediumBold = PretendardStyle,
        bodyMediumMedium = PretendardStyle,
        bodyMediumRegular = PretendardStyle,
        bodySmallBold = PretendardStyle,
        bodySmallMedium = PretendardStyle,
        bodySmallRegular = PretendardStyle,
        bodyXSmallBold = PretendardStyle,
        bodyXSmallMedium = PretendardStyle,
        bodyXSmallRegular = PretendardStyle,
    )
}
