package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ddd.oi.presentation.core.designsystem.theme.neutral100
import com.ddd.oi.presentation.core.designsystem.theme.neutral800
import com.ddd.oi.presentation.core.designsystem.util.OiChipDimens

@Composable
fun OiChip(
    modifier: Modifier = Modifier,
    selected: Boolean = true,
    @DrawableRes iconDrawableRes: Int? = null,
    @StringRes textStringRes: Int,
) {
    Row(
        modifier = modifier
            .background(
                color = getChipBackgroundColor(selected),
                shape = RoundedCornerShape(OiChipDimens.ovalRadius)
            )
            .padding(
                horizontal = OiChipDimens.horizontalPadding,
                vertical = OiChipDimens.verticalPadding
            ),
        horizontalArrangement = Arrangement.spacedBy(OiChipDimens.componentMargin),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconDrawableRes != null) {
            Icon(
                modifier = Modifier.size(OiChipDimens.iconSize),
                painter = painterResource(iconDrawableRes),
                contentDescription = "Chip icon"
            )
        }
        Text(
            text = stringResource(textStringRes),
            style = getChipTextStyle(selected),
            color = getChipTextColor(selected)
        )
    }
}


@Composable
internal fun getChipTextStyle(selected: Boolean): TextStyle {
    return if (selected) {
        OiTheme.typography.bodyMediumBold
    } else {
        OiTheme.typography.bodyMediumRegular
    }
}

internal fun getChipBackgroundColor(selected: Boolean): Color {
    return if (selected) {
        neutral800
    } else {
        neutral100
    }
}

internal fun getChipTextColor(selected: Boolean): Color {
    return if (selected) {
        TextOnPrimary
    } else {
        TextSecondary
    }
}

@Preview
@Composable
private fun OiChipPreview() {
    OiChip(
        textStringRes = R.string.button
    )
}