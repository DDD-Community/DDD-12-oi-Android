package com.ddd.oi.presentation.schedule.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleActionBottomSheet(
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onCopy: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        containerColor = white,
        onDismissRequest = onDismiss,
        dragHandle = null,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.paddingMediumSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(modifier = Modifier.fillMaxWidth(), onClick = onEdit) {
                Text(
                    text = stringResource(R.string.edit),
                    style = OiTheme.typography.bodyLargeSemibold,
                    color = OiTheme.colors.textPrimary
                )
            }
            TextButton(modifier = Modifier.fillMaxWidth(), onClick = onCopy) {
                Text(
                    text = stringResource(R.string.copy),
                    style = OiTheme.typography.bodyLargeSemibold,
                    color = OiTheme.colors.textPrimary
                )

            }
            TextButton(modifier = Modifier.fillMaxWidth(), onClick = onDelete) {
                Text(text = stringResource(R.string.remove_schedule),
                    style = OiTheme.typography.bodyLargeSemibold,
                    color = OiTheme.colors.backgroundError)
            }
        }
    }
}


@Preview
@Composable
private fun ScheduleActionBottomSheetPreview() {
    MaterialTheme {
        ScheduleActionBottomSheet(
            onDismiss = {},
            onEdit = {},
            onCopy = {},
            onDelete = {}
        )
    }
}