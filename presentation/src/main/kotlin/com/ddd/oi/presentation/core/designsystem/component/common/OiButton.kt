package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.OiButtonDimens

@Composable
fun OiButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    style: OiButtonStyle,
    colorType: OiButtonColorType = OiButtonColorType.Primary,
    enabled: Boolean = true,
    title: String,
    @DrawableRes leftIconDrawableRes: Int? = null,
    @DrawableRes rightIconDrawableRes: Int? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Button(
        modifier = modifier
            .height(style.height),
        contentPadding = contentPadding,
        shape = style.shape,
        enabled = enabled,
        colors = getButtonColors(colorType, isPressed),
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(OiButtonDimens.componentMargin),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight()
        ) {
            leftIconDrawableRes?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = "Left icon"
                )
            }

            Text(
                text = title,
                style = style.getTextStyle(),
            )

            rightIconDrawableRes?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = "Right icon"
                )
            }
        }
    }
}

@Composable
fun OiNavigationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    style: OiButtonStyle = OiButtonStyle.Large48Oval,
    enabled: Boolean = true,
    @StringRes textStringRes: Int,
    @DrawableRes leftIconDrawableRes: Int? = null,
    @DrawableRes rightIconDrawableRes: Int? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(style.height),
        shape = style.shape,
        enabled = enabled,
        colors = getNavigationButtonColors(isPressed),
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(OiButtonDimens.componentMargin),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight()
        ) {
            leftIconDrawableRes?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = "Left icon"
                )
            }

            Text(
                text = stringResource(textStringRes),
                style = style.getTextStyle(),
            )

            rightIconDrawableRes?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = "Right icon"
                )
            }
        }
    }
}

@Composable
private fun getNavigationButtonColors(
    isPressed: Boolean
): ButtonColors {
    return if (isPressed) {
        ButtonColors(
            contentColor = OiTheme.colors.textOnPrimary,
            containerColor = OiTheme.colors.backgroundPressed,
            disabledContentColor = OiTheme.colors.textDisabled,
            disabledContainerColor = OiTheme.colors.backgroundUnselected,
        )
    } else {
        ButtonColors(
            contentColor = OiTheme.colors.textOnPrimary,
            containerColor = OiTheme.colors.backgroundPrimary,
            disabledContentColor = OiTheme.colors.textDisabled,
            disabledContainerColor = OiTheme.colors.backgroundUnselected,
        )
    }
}

@Composable
private fun getButtonColors(colorType: OiButtonColorType, isPressed: Boolean): ButtonColors {
    return when (colorType) {
        OiButtonColorType.Primary -> {
            if (isPressed) {
                ButtonColors(
                    contentColor = OiTheme.colors.textOnPrimary,
                    containerColor = OiTheme.colors.backgroundPressed,
                    disabledContentColor = OiTheme.colors.textDisabled,
                    disabledContainerColor = OiTheme.colors.backgroundDisabled,
                )
            } else {
                ButtonColors(
                    contentColor = OiTheme.colors.textOnPrimary,
                    containerColor = OiTheme.colors.backgroundPrimary,
                    disabledContentColor = OiTheme.colors.textDisabled,
                    disabledContainerColor = OiTheme.colors.backgroundDisabled,
                )
            }
        }

        OiButtonColorType.Secondary -> {
            if (isPressed) {
                ButtonColors(
                    contentColor = OiTheme.colors.textPrimary,
                    containerColor = OiTheme.colors.backgroundPressed,
                    disabledContentColor = OiTheme.colors.textDisabled,
                    disabledContainerColor = OiTheme.colors.backgroundDisabled,
                )
            } else {
                ButtonColors(
                    contentColor = OiTheme.colors.textPrimary,
                    containerColor = OiTheme.colors.backgroundUnselected,
                    disabledContentColor = OiTheme.colors.textDisabled,
                    disabledContainerColor = OiTheme.colors.backgroundDisabled,
                )
            }
        }

        OiButtonColorType.Danger -> {
            if (isPressed) {
                ButtonColors(
                    contentColor = OiTheme.colors.textOnPrimary,
                    containerColor = OiTheme.colors.backgroundPressed,
                    disabledContentColor = OiTheme.colors.textDisabled,
                    disabledContainerColor = OiTheme.colors.backgroundDisabled,
                )
            } else {
                ButtonColors(
                    contentColor = OiTheme.colors.textOnPrimary,
                    containerColor = OiTheme.colors.backgroundError,
                    disabledContentColor = OiTheme.colors.textDisabled,
                    disabledContainerColor = OiTheme.colors.backgroundDisabled,
                )
            }
        }
    }
}

@Immutable
enum class OiButtonColorType {
    Primary,
    Secondary,
    Danger
}

@Immutable
sealed interface OiButtonStyle {
    data object Small32Rect : OiButtonStyle
    data object Small32Oval : OiButtonStyle
    data object Medium40Rect : OiButtonStyle
    data object Medium40Oval : OiButtonStyle
    data object Large48Rect : OiButtonStyle
    data object Large48Oval : OiButtonStyle
    data object Xlarge56Rect : OiButtonStyle
    data object Xlarge56Oval : OiButtonStyle

    companion object {
        val entries: Set<OiButtonStyle>
            get() = setOf(
                Small32Rect,
                Small32Oval,
                Medium40Rect,
                Medium40Oval,
                Large48Rect,
                Large48Oval,
                Xlarge56Rect,
                Xlarge56Oval
            )
    }

    val height: Dp
        get() = when (this) {
            Small32Oval, Small32Rect -> OiButtonDimens.smallHeight
            Medium40Rect, Medium40Oval -> OiButtonDimens.mediumHeight
            Large48Oval, Large48Rect -> OiButtonDimens.largeHeight
            Xlarge56Oval, Xlarge56Rect -> OiButtonDimens.xlargeHeight
        }

    val shape: Shape
        get() = when (this) {
            Small32Oval,
            Medium40Oval,
            Large48Oval,
            Xlarge56Oval -> RoundedCornerShape(OiButtonDimens.ovalRadius)

            Small32Rect,
            Medium40Rect,
            Large48Rect,
            Xlarge56Rect -> RoundedCornerShape(OiButtonDimens.roundedRectRadius)
        }

    @Composable
    fun getTextStyle(): TextStyle = when (this) {
        Small32Oval, Small32Rect -> OiTheme.typography.bodySmallSemibold
        Medium40Rect, Medium40Oval -> OiTheme.typography.bodyMediumSemibold
        Large48Oval, Large48Rect -> OiTheme.typography.bodyLargeSemibold
        Xlarge56Oval, Xlarge56Rect -> OiTheme.typography.bodyLargeSemibold
    }
}

@Preview
@Composable
private fun OiButtonPreview() {
    OiTheme {
        Column(verticalArrangement = Arrangement.spacedBy(OiButtonDimens.componentMargin)) {
            OiButtonColorType.entries.forEach { colorType ->
                Text(text = colorType.name, style = OiTheme.typography.bodyMediumSemibold)
                OiButtonStyle.entries.take(3).forEach { style ->
                    OiButton(
                        style = style,
                        colorType = colorType,
                        title = stringResource(R.string.button_eng),
                        leftIconDrawableRes = R.drawable.ic_add_plus,
                        rightIconDrawableRes = R.drawable.ic_chevron_right,
                    )
                }
            }
        }
    }
}
