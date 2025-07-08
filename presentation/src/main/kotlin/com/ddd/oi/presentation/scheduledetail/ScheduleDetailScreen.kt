package com.ddd.oi.presentation.scheduledetail

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScheduleDetailScreen(
    modifier: Modifier = Modifier,
    scheduleId: Long,
    navigateToSearchPlace: () -> Unit
) {
    Button(onClick = navigateToSearchPlace) {
        Text("${scheduleId}장소 추가하기")
    }
}