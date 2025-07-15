package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme

@Composable
fun OiBadge(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        modifier = modifier
            .padding(start = 8.dp)
            .background(
                color = OiTheme.colors.backgroundUnselected,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            text = text,
            color = OiTheme.colors.textTertiary,
            style = OiTheme.typography.bodyXSmallMedium
        )
    }
}