package com.ddd.oi.presentation.scheduledetail.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.rippleOrFallbackImplementation
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.schedule.OiAddButton

@Composable
internal fun ScheduleDetailDragHandle(
    modifier: Modifier = Modifier,
    navigateToSearchPlace: () -> Unit,
    activeDate: String,
    isMoreDateVisible: Boolean,
    onMoreDateClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier =
                modifier
                    .padding(vertical = 8.dp)
                    .semantics {
                        contentDescription = "dragHandle"
                    },
            color = Color(0xFFD9D9D9),
            shape = RoundedCornerShape(999.dp)
        ) {
            Box(Modifier.size(width = 32.dp, height = 4.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = activeDate,
                style = OiTheme.typography.bodyLargeSemibold
            )
            if (isMoreDateVisible) {
                Box(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable(
                            onClick = onMoreDateClick,
                            role = Role.Button,
                            interactionSource = null,
                            indication =
                                rippleOrFallbackImplementation(
                                    bounded = false,
                                    radius = 12.dp
                                )
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "DateDropDown"
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            OiAddButton(
                title = R.string.add_place,
                onClick = {}
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ScheduleDetailDragHandlePreview() {
    OiTheme {
        ScheduleDetailDragHandle(
            navigateToSearchPlace = {},
            isMoreDateVisible = true,
            activeDate = "Day1 (06.06)",
            onMoreDateClick = {}
        )
    }
}