package com.ddd.oi.presentation.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ddd.oi.presentation.R

private val PretendardStyle = TextStyle(
    fontFamily = Pretendard,
)

internal val Typography = OiTypography(
    headlineLargeB = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineMediumB = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    ),
    headlineSmallB = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.sp
    ),
    bodyLargeB = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 20.8.sp,
        letterSpacing = 0.sp
    ),
    bodyLargeM = PretendardStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.8.sp,
        letterSpacing = 0.sp
    ),
    bodyLargeR = PretendardStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.8.sp,
        letterSpacing = 0.sp
    ),
    bodyMediumB = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 18.2.sp,
        letterSpacing = 0.sp
    ),
    bodyMediumM = PretendardStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.2.sp,
        letterSpacing = 0.sp
    ),
    bodyMediumR = PretendardStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.2.sp,
        letterSpacing = 0.sp
    ),
    bodySmallB = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 15.6.sp,
        letterSpacing = 0.sp
    ),
    bodySmallM = PretendardStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 15.6.sp,
        letterSpacing = 0.sp
    ),
    bodySmallR = PretendardStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 15.6.sp,
        letterSpacing = 0.sp
    ),
    bodyXSmallB = PretendardStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 14.3.sp,
        letterSpacing = 0.sp
    ),
    bodyXSmallM = PretendardStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.3.sp,
        letterSpacing = 0.sp
    ),
    bodyXSmallR = PretendardStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 14.3.sp,
        letterSpacing = 0.sp
    )
)

@Immutable
data class OiTypography(
    val headlineLargeB: TextStyle,
    val headlineMediumB: TextStyle,
    val headlineSmallB: TextStyle,

    val bodyLargeB: TextStyle,
    val bodyLargeM: TextStyle,
    val bodyLargeR: TextStyle,
    val bodyMediumB: TextStyle,
    val bodyMediumM: TextStyle,
    val bodyMediumR: TextStyle,
    val bodySmallB: TextStyle,
    val bodySmallM: TextStyle,
    val bodySmallR: TextStyle,
    val bodyXSmallB: TextStyle,
    val bodyXSmallM: TextStyle,
    val bodyXSmallR: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    OiTypography(
        headlineSmallB = PretendardStyle,
        headlineMediumB = PretendardStyle,
        headlineLargeB = PretendardStyle,
        bodyLargeB = PretendardStyle,
        bodyLargeM = PretendardStyle,
        bodyLargeR = PretendardStyle,
        bodyMediumB = PretendardStyle,
        bodyMediumM = PretendardStyle,
        bodyMediumR = PretendardStyle,
        bodySmallB = PretendardStyle,
        bodySmallM = PretendardStyle,
        bodySmallR = PretendardStyle,
        bodyXSmallB = PretendardStyle,
        bodyXSmallM = PretendardStyle,
        bodyXSmallR = PretendardStyle,
    )
}
