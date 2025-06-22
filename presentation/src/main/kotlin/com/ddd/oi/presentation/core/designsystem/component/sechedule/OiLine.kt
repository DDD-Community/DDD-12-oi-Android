package com.ddd.oi.presentation.core.designsystem.component.sechedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ddd.oi.presentation.core.designsystem.util.OiCardDimens

@Composable
fun OiLine(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .width(OiCardDimens.lineWidth)
            .background(color = color, shape = RoundedCornerShape(2.dp))
    )
}