package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.OiHeaderDimens

@Composable
fun OiHeader(
    modifier: Modifier = Modifier,
    onLeftClick: () -> Unit = {},
    @StringRes titleStringRes: Int,
    @DrawableRes leftButtonDrawableRes: Int,
) {
    Box {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(OiHeaderDimens.size),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .size(OiHeaderDimens.size)
                    .padding(OiHeaderDimens.padding),
                onClick = onLeftClick
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize(),
                    imageVector = ImageVector.vectorResource(leftButtonDrawableRes),
                    contentDescription = "Left button"
                )
            }

            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(titleStringRes),
                style = OiTheme.typography.headlineSmallBold,
                overflow = TextOverflow.Ellipsis
            )
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
            thickness = 1.dp,
            color = OiTheme.colors.borderSecondary,
        )
    }
}

@Preview
@Composable
private fun OiHeaderPreview() {
    OiHeader(
        titleStringRes = R.string.create_schedule,
        leftButtonDrawableRes = R.drawable.ic_arrow_left
    )
}