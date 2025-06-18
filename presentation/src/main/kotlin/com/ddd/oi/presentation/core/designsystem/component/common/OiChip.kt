package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.TextOnPrimary
import com.ddd.oi.presentation.core.designsystem.theme.TextSecondary
import com.ddd.oi.presentation.core.designsystem.theme.indigo400
import com.ddd.oi.presentation.core.designsystem.theme.lime400
import com.ddd.oi.presentation.core.designsystem.theme.rose400
import com.ddd.oi.presentation.core.designsystem.theme.teal400
import com.ddd.oi.presentation.core.designsystem.theme.yellow400
import com.ddd.oi.presentation.core.designsystem.util.OiChipDimens
import com.ddd.oi.presentation.core.designsystem.util.OiChipTabDimes

@Composable
fun OiRoundRectChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    oiChipIcon: OiChipIcon = OiChipIcon.None,
    @StringRes textStringRes: Int,
) {
    Row(
        modifier = modifier
            .height(OiChipDimens.RoundRect.height)
            .background(
                color = getRoundRectBackgroundColor(isSelected),
                shape = RoundedCornerShape(OiChipDimens.RoundRect.ovalRadius)
            )
            .padding(
                horizontal = OiChipDimens.RoundRect.horizontalPadding,
                vertical = OiChipDimens.RoundRect.verticalPadding
            ),
        horizontalArrangement = Arrangement.spacedBy(OiChipDimens.RoundRect.componentMargin),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (oiChipIcon != OiChipIcon.None) {
            Icon(
                modifier = Modifier.size(OiChipDimens.RoundRect.iconSize),
                painter = painterResource(oiChipIcon.iconDrawableRes),
                tint = oiChipIcon.iconColor,
                contentDescription = "Chip icon"
            )
        }
        Text(
            text = stringResource(textStringRes),
            style = getRoundRectTextStyle(isSelected),
            color = getChipTextColor(isSelected)
        )
    }
}

@Composable
fun OiOvalChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    oiChipIcon: OiChipIcon = OiChipIcon.None,
    @StringRes textStringRes: Int,
) {
    check(oiChipIcon != OiChipIcon.None)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(OiChipDimens.Oval.componentMargin)
    ) {
        Box(
            modifier = modifier
                .size(OiChipDimens.Oval.size)
                .background(
                    color = getOvalBackgroundColor(isSelected),
                    shape = RoundedCornerShape(OiChipDimens.RoundRect.ovalRadius)
                )
                .border(
                    width = OiChipDimens.Oval.borderStroke,
                    color = getOvalBorderColor(isSelected),
                    shape = RoundedCornerShape(OiChipDimens.RoundRect.ovalRadius)
                )
                .padding(OiChipDimens.Oval.padding)
        ) {
            Icon(
                modifier = Modifier.size(OiChipDimens.Oval.iconSize),
                painter = painterResource(oiChipIcon.iconDrawableRes),
                tint = oiChipIcon.getColor(isSelected),
                contentDescription = "Chip icon"
            )
        }

        Text(
            text = stringResource(textStringRes),
            style = getOvalTextStyle(isSelected)
        )
    }

}


@Composable
private fun getRoundRectTextStyle(isSelected: Boolean): TextStyle {
    return if (isSelected) {
        OiTheme.typography.bodyMediumSemibold
    } else {
        OiTheme.typography.bodyMediumRegular
    }
}

@Composable
private fun getOvalTextStyle(isSelected: Boolean): TextStyle {
    return if (isSelected) {
        OiTheme.typography.bodySmallSemibold
    } else {
        OiTheme.typography.bodySmallRegular
    }
}

@Composable
private fun getRoundRectBackgroundColor(selected: Boolean): Color {
    return if (selected) {
        OiTheme.colors.textPrimary
    } else {
        OiTheme.colors.backgroundUnselected
    }
}

@Composable
private fun getOvalBorderColor(selected: Boolean): Color {
    return if (selected) {
        Color.Black
    } else {
        OiTheme.colors.borderPrimary
    }
}

@Composable
private fun getOvalBackgroundColor(selected: Boolean): Color {
    return if (selected) {
        OiTheme.colors.backgroundSelected
    } else {
        Color.Transparent
    }
}

private fun getChipTextColor(selected: Boolean): Color {
    return if (selected) {
        TextOnPrimary
    } else {
        TextSecondary
    }
}

@Immutable
sealed class OiChipIcon(
    @DrawableRes val iconDrawableRes: Int,
    val iconColor: Color,
) {
    data object Business : OiChipIcon(
        iconDrawableRes = R.drawable.ic_business,
        iconColor = indigo400
    )

    data object Daily : OiChipIcon(
        iconDrawableRes = R.drawable.ic_daily,
        iconColor = yellow400
    )

    data object Date : OiChipIcon(
        iconDrawableRes = R.drawable.ic_date,
        iconColor = rose400
    )

    data object Etc : OiChipIcon(
        iconDrawableRes = R.drawable.ic_etc,
        iconColor = lime400
    )

    data object Travel : OiChipIcon(
        iconDrawableRes = R.drawable.ic_travel,
        iconColor = teal400
    )

    data object None : OiChipIcon(
        iconDrawableRes = 0,
        iconColor = Color.Black
    )
}

@Composable
private fun OiChipIcon.getColor(isSelected: Boolean): Color {
    return if (this == OiChipIcon.None) {
        Color.Black
    } else {
        if (isSelected) {
            iconColor
        } else {
            OiTheme.colors.backgroundUnselected
        }
    }
}

@Preview
@Composable
private fun OiRoundRectChipPreview() {
    Column(
        modifier = Modifier.padding(
            vertical = OiChipTabDimes.RoundRect.verticalPadding,
            horizontal = OiChipTabDimes.RoundRect.horizontalPadding
        ),
        verticalArrangement = Arrangement.spacedBy(OiChipDimens.RoundRect.componentMargin)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(OiChipDimens.RoundRect.componentMargin),
        ) {
            OiRoundRectChip(
                isSelected = false,
                textStringRes = R.string.whole,
                oiChipIcon = OiChipIcon.None
            )

            OiRoundRectChip(
                isSelected = false,
                textStringRes = R.string.travel,
                oiChipIcon = OiChipIcon.Travel
            )

            OiRoundRectChip(
                isSelected = false,
                textStringRes = R.string.date,
                oiChipIcon = OiChipIcon.Date
            )

            OiRoundRectChip(
                isSelected = false,
                textStringRes = R.string.daily,
                oiChipIcon = OiChipIcon.Daily
            )


            OiRoundRectChip(
                isSelected = false,
                textStringRes = R.string.business,
                oiChipIcon = OiChipIcon.Business
            )

            OiRoundRectChip(
                isSelected = false,
                textStringRes = R.string.etc,
                oiChipIcon = OiChipIcon.Etc
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(OiChipDimens.RoundRect.componentMargin),
        ) {
            OiRoundRectChip(
                isSelected = true,
                textStringRes = R.string.whole,
                oiChipIcon = OiChipIcon.None
            )

            OiRoundRectChip(
                isSelected = true,
                textStringRes = R.string.travel,
                oiChipIcon = OiChipIcon.Travel
            )

            OiRoundRectChip(
                isSelected = true,
                textStringRes = R.string.date,
                oiChipIcon = OiChipIcon.Date
            )

            OiRoundRectChip(
                isSelected = true,
                textStringRes = R.string.daily,
                oiChipIcon = OiChipIcon.Daily
            )

            OiRoundRectChip(
                isSelected = true,
                textStringRes = R.string.business,
                oiChipIcon = OiChipIcon.Business
            )

            OiRoundRectChip(
                isSelected = true,
                textStringRes = R.string.etc,
                oiChipIcon = OiChipIcon.Etc
            )
        }
    }
}

@Preview
@Composable
private fun OiOvalChipPreview() {
    Column(
        modifier = Modifier.padding(
            horizontal = OiChipTabDimes.Oval.horizontalPadding,
            vertical = OiChipTabDimes.Oval.verticalPadding
        ),
        verticalArrangement = Arrangement.spacedBy(OiChipTabDimes.Oval.verticalPadding)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(OiChipDimens.Oval.componentMargin),
        ) {
            OiOvalChip(
                isSelected = false,
                oiChipIcon = OiChipIcon.Travel,
                textStringRes = R.string.travel
            )

            OiOvalChip(
                isSelected = false,
                oiChipIcon = OiChipIcon.Date,
                textStringRes = R.string.date
            )

            OiOvalChip(
                isSelected = false,
                oiChipIcon = OiChipIcon.Daily,
                textStringRes = R.string.daily
            )


            OiOvalChip(
                isSelected = false,
                oiChipIcon = OiChipIcon.Business,
                textStringRes = R.string.business
            )

            OiOvalChip(
                isSelected = false,
                oiChipIcon = OiChipIcon.Etc,
                textStringRes = R.string.etc
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(OiChipDimens.Oval.componentMargin),
        ) {
            OiOvalChip(
                isSelected = true,
                oiChipIcon = OiChipIcon.Travel,
                textStringRes = R.string.travel
            )

            OiOvalChip(
                isSelected = true,
                oiChipIcon = OiChipIcon.Date,
                textStringRes = R.string.date
            )

            OiOvalChip(
                isSelected = true,
                oiChipIcon = OiChipIcon.Daily,
                textStringRes = R.string.daily
            )

            OiOvalChip(
                isSelected = true,
                oiChipIcon = OiChipIcon.Business,
                textStringRes = R.string.business
            )

            OiOvalChip(
                isSelected = true,
                oiChipIcon = OiChipIcon.Etc,
                textStringRes = R.string.etc
            )
        }
    }
}