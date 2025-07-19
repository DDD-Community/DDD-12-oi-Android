package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    tag: String = "",
    isSelected: Boolean = false,
    oiChipIcon: OiChipIcon = OiChipIcon.None,
    @StringRes textStringRes: Int,
    onItemClick: (String) -> Unit = {},
) {
    Row(
        modifier = modifier
            .height(OiChipDimens.RoundRect.height)
            .clip(RoundedCornerShape(OiChipDimens.RoundRect.ovalRadius))
            .background(color = getRoundRectBackgroundColor(isSelected))
            .clickable { onItemClick(tag) }
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
    tag: String = "",
    isSelected: Boolean = false,
    oiChipIcon: OiChipIcon = OiChipIcon.None,
    @StringRes textStringRes: Int,
    onItemClick: (String) -> Unit = {},
) {
    check(oiChipIcon != OiChipIcon.None)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(OiChipDimens.Oval.componentMargin)
    ) {
        Box(
            modifier = modifier
                .size(OiChipDimens.Oval.size)
                .clip(RoundedCornerShape(OiChipDimens.RoundRect.ovalRadius))
                .background(getOvalBackgroundColor(isSelected))
                .border(
                    width = OiChipDimens.Oval.borderStroke,
                    color = getOvalBorderColor(isSelected),
                    shape = RoundedCornerShape(OiChipDimens.RoundRect.ovalRadius)
                )
                .clickable {
                    onItemClick(tag)
                }
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
fun OiChoiceChip(
    modifier: Modifier = Modifier,
    tag: String = "",
    isSelected: Boolean = false,
    @StringRes textStringRes: Int,
    onItemClick: (String) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .height(height = OiChipDimens.Choice.height)
                .clip(RoundedCornerShape(OiChipDimens.RoundRect.ovalRadius))
                .background(color = getOvalBackgroundColor(isSelected))
                .border(
                    width = OiChipDimens.Oval.borderStroke,
                    color = getOvalBorderColor(isSelected),
                    shape = RoundedCornerShape(OiChipDimens.RoundRect.ovalRadius)
                )
                .clickable { onItemClick(tag) }
                .padding(
                    horizontal = OiChipDimens.Choice.horizontalPadding,
                    vertical = OiChipDimens.Choice.verticalPadding
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(textStringRes),
                textAlign = TextAlign.Center,
                style = getChoiceTextStyle(isSelected),
                color = getChipTextColor(isSelected)
            )
        }
    }
}

@Composable
fun OiSearchChip(
    modifier: Modifier = Modifier,
    text: String,
    onItemClick: (String) -> Unit = {},
    onIconClick: (String) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .height(height = OiChipDimens.Search.height)
            .clip(RoundedCornerShape(OiChipDimens.Search.rectRadius))
            .background(color = Color.Transparent)
            .border(
                width = OiChipDimens.Oval.borderStroke,
                color = OiTheme.colors.borderPrimary,
                shape = RoundedCornerShape(OiChipDimens.Search.rectRadius)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onItemClick(text)
            }
            .padding(
                horizontal = OiChipDimens.Search.horizontalPadding,
                vertical = OiChipDimens.Search.verticalPadding
            ),
        horizontalArrangement = Arrangement.spacedBy(OiChipDimens.Search.componentMargin),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Start,
            style = OiTheme.typography.bodySmallSemibold,
            color = OiTheme.colors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(
            modifier = Modifier.size(OiChipDimens.Search.iconSize),
            onClick = {
                onIconClick(text)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_x),
                tint = OiTheme.colors.iconTertiary,
                contentDescription = "Chip icon"
            )
        }
    }
}

@Composable
fun OiRecentSearchChip(
    modifier: Modifier = Modifier,
    text: String,
    onItemClick: (String) -> Unit = {},
    onIconClick: (String) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .height(height = OiChipDimens.RecentSearch.height)
            .clip(RoundedCornerShape(OiChipDimens.RecentSearch.radius))
            .background(color = OiTheme.colors.backgroundUnselected)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onItemClick(text)
            }
            .padding(
                horizontal = OiChipDimens.RecentSearch.horizontalPadding,
                vertical = OiChipDimens.RecentSearch.verticalPadding
            ),
        horizontalArrangement = Arrangement.spacedBy(OiChipDimens.RecentSearch.componentMargin),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f, fill = false),
            text = text,
            textAlign = TextAlign.Start,
            style = OiTheme.typography.bodySmallSemibold,
            color = OiTheme.colors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(
            modifier = Modifier.size(OiChipDimens.RecentSearch.iconSize),
            onClick = {
                onIconClick(text)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_x),
                tint = OiTheme.colors.borderFocus,
                contentDescription = "Chip icon"
            )
        }
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
private fun getChoiceTextStyle(isSelected: Boolean): TextStyle {
    return if (isSelected) {
        OiTheme.typography.bodyMediumSemibold
    } else {
        OiTheme.typography.bodyMediumRegular
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
            OiTheme.colors.backgroundDisabled
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

@Preview
@Composable
private fun OiChoiceChipPreview() {
    OiChoiceChip(textStringRes = R.string.transportation_car)
}

@Preview
@Composable
private fun OiSearchChipPlace() {
    OiSearchChip(text = "애슐리 서울대입구점")
}

@Preview
@Composable
private fun OiRecentSearchChipPlace() {
    OiRecentSearchChip(text = "복초밥")
}