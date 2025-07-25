package com.ddd.oi.presentation.core.designsystem.component.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonColorType
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.DialogDimens
import com.ddd.oi.presentation.core.designsystem.util.Dimens

@Composable
fun OiDialog(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.paddingMedium),
            shape = RoundedCornerShape(DialogDimens.cornerRadius),
            colors = CardDefaults.cardColors(
                containerColor = white
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    }
}

@Composable
fun OiDeleteDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    @StringRes messageRes: Int = R.string.dialog_delete_message
) {
    OiDialog(onDismiss = onDismiss) {
        Column(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier.padding(vertical = Dimens.paddingLarge),
                text = stringResource(messageRes),
                style = OiTheme.typography.headLineSmallSemibold,
                color = OiTheme.colors.textPrimary
            )
            OiButton(
                modifier = Modifier.fillMaxWidth(),
                style = OiButtonStyle.Large48Oval,
                colorType = OiButtonColorType.Danger,
                title = stringResource(R.string.button_delete),
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            )
            OiButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.paddingMediumSmall,
                        bottom = Dimens.paddingLarge
                    ),
                style = OiButtonStyle.Large48Oval,
                colorType = OiButtonColorType.Secondary,
                title = stringResource(R.string.button_cancel),
                onClick = onDismiss
            )
        }
    }
}

@Composable
fun OiRegisterDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit,
) {
    OiDialog(onDismiss = onDismiss) {
        Column(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content()
            OiButton(
                modifier = Modifier.fillMaxWidth(),
                style = OiButtonStyle.Large48Oval,
                colorType = OiButtonColorType.Primary,
                title = stringResource(R.string.button_register),
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            )
            OiButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.paddingMediumSmall,
                        bottom = Dimens.paddingLarge
                    ),
                style = OiButtonStyle.Large48Oval,
                colorType = OiButtonColorType.Secondary,
                title = stringResource(R.string.button_cancel),
                onClick = onDismiss
            )
        }
    }
}

@Composable
fun ScheduleActionDialog(
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onCopy: () -> Unit,
    onDelete: () -> Unit
) {
    OiDialog(onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.paddingMediumSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(modifier = Modifier.fillMaxWidth(), onClick = onEdit) {
                Text(
                    modifier = Modifier.padding(vertical = Dimens.paddingMediumLarge),
                    text = stringResource(R.string.edit),
                    style = OiTheme.typography.bodyLargeSemibold,
                    color = OiTheme.colors.textPrimary
                )
            }
            TextButton(modifier = Modifier.fillMaxWidth(), onClick = onCopy) {
                Text(
                    modifier = Modifier.padding(vertical = Dimens.paddingMediumLarge),
                    text = stringResource(R.string.copy),
                    style = OiTheme.typography.bodyLargeSemibold,
                    color = OiTheme.colors.textPrimary
                )

            }
            TextButton(modifier = Modifier.fillMaxWidth(), onClick = onDelete) {
                Text(
                    modifier = Modifier.padding(vertical = Dimens.paddingMediumLarge),
                    text = stringResource(R.string.remove_schedule),
                    style = OiTheme.typography.bodyLargeSemibold,
                    color = OiTheme.colors.backgroundError
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun OiDialogPreview() {
    OiTheme {
        var showDeleteDialog by remember { mutableStateOf(false) }
        var showRegisterDialog by remember { mutableStateOf(false) }
        var showActionScheduleDialog by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
        ) {
            OiButton(
                modifier = Modifier.fillMaxWidth(),
                style = OiButtonStyle.Medium40Rect,
                title = stringResource(R.string.button_delete),
                onClick = { showDeleteDialog = true }
            )

            OiButton(
                style = OiButtonStyle.Medium40Rect,
                title = stringResource(R.string.button_register),
                onClick = { showRegisterDialog = true }
            )
            OiButton(
                style = OiButtonStyle.Medium40Rect,
                title = stringResource(R.string.edit_schedule),
                onClick = { showActionScheduleDialog = true }
            )
        }

        if (showDeleteDialog) {
            OiDeleteDialog(
                onDismiss = { showDeleteDialog = false },
                onConfirm = {}
            )
        }
        if (showActionScheduleDialog) {
            ScheduleActionDialog(
                onDismiss = { showActionScheduleDialog = false },
                onEdit = {},
                onCopy = {},
                onDelete = {}
            )
        }

        if (showRegisterDialog) {
            OiRegisterDialog(
                onDismiss = { showRegisterDialog = false },
                onConfirm = {},
                content = {
                    Text(
                        modifier = Modifier.padding(vertical = Dimens.paddingLarge),
                        text = "tets",
                        style = OiTheme.typography.headLineSmallSemibold,
                        color = OiTheme.colors.textPrimary,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    }
}