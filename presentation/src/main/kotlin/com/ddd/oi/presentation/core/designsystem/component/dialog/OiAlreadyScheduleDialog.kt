package com.ddd.oi.presentation.core.designsystem.component.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.Dimens

@Composable
fun OiAlreadyScheduleDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    OiRegisterDialog(
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        content = {
            Text(
                modifier = Modifier.padding(vertical = Dimens.paddingLarge),
                text = stringResource(R.string.dialog_already_exist_schedule),
                style = OiTheme.typography.headLineSmallSemibold,
                color = OiTheme.colors.textPrimary,
                textAlign = TextAlign.Center
            )
        }
    )
}