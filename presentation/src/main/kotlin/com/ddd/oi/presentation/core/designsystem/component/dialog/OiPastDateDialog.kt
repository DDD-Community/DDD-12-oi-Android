package com.ddd.oi.presentation.core.designsystem.component.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.Dimens

@Composable
fun OiPastDateDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    OiRegisterDialog(
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        content = {
            Text(
                modifier = Modifier.padding(vertical = Dimens.paddingLarge),
                text = buildAnnotatedString {
                    append(stringResource(R.string.dialog_past_date_message1))
                    withStyle(style = SpanStyle(color = OiTheme.colors.textBrand)) {
                        append(stringResource(R.string.dialog_past_date_message2))
                    }
                    append(stringResource(R.string.dialog_past_date_message3))
                },
                style = OiTheme.typography.headLineSmallSemibold,
                color = OiTheme.colors.textPrimary,
                textAlign = TextAlign.Center
            )
        }
    )
}

@Preview
@Composable
private fun OiPastDateDialogPreview() {
    OiTheme {
        OiPastDateDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}