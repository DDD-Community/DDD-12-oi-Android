package com.ddd.oi.presentation.core.designsystem.component.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.snackbarBackground
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.designsystem.util.OiSnackBarDimen

@Composable
internal fun SuccessSnackbar(
    snackbar: SnackbarVariant.Success,
) {
    BaseSnackbar(
        message = snackbar.message,
        icon = R.drawable.ic_check_circle,
    )
}

@Composable
internal fun WarningSnackbar(
    snackbar: SnackbarVariant.Warning,
) {
    BaseSnackbar(
        message = snackbar.message,
        icon = R.drawable.ic_warning,
    )
}


@Composable
fun ActionSnackbarContent(
    snackbar: SnackbarVariant.ActionSnackbar,
    backgroundColor: Color = snackbarBackground,
    contentColor: Color = white,
    shape: Shape = RoundedCornerShape(OiSnackBarDimen.cornerRadius),
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(OiSnackBarDimen.snackbarHeight)
            .padding(horizontal = Dimens.paddingMedium)
            .clip(shape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            snackbar.icon?.let { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = OiTheme.colors.iconBrand,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }

            Text(
                text = snackbar.message,
                style = OiTheme.typography.bodyMediumMedium,
                maxLines = 1,
                color = contentColor,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {
            // 주 액션
            TextButton(
                onClick = {
                    snackbar.primaryAction()
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = Color.Red
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = snackbar.primaryActionLabel,
                    style = OiTheme.typography.bodyMediumRegular
                )
            }
            snackbar.secondaryActionLabel?.let { label ->
                TextButton(
                    onClick = {
                        snackbar.secondaryAction?.invoke()
                        onDismiss()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = OiTheme.colors.textSecondary
                    )
                ) {
                    Text(
                        text = label,
                        style = OiTheme.typography.bodyMediumRegular
                    )
                }
            }
        }

    }
}

/**
 * 기본 스낵바 템플릿
 */
@Composable
fun BaseSnackbar(
    message: String,
    @DrawableRes icon: Int,
    backgroundColor: Color = snackbarBackground,
    contentColor: Color = white,
    shape: Shape = RoundedCornerShape(OiSnackBarDimen.cornerRadius),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(OiSnackBarDimen.snackbarHeight)
            .padding(horizontal = Dimens.paddingMedium)
            .clip(shape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message,
                style = OiTheme.typography.bodyMediumMedium,
                modifier = Modifier.weight(1f),
                maxLines = 2,
                color = contentColor,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}