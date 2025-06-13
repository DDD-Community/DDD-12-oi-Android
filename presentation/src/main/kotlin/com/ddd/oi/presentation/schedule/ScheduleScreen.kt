package com.ddd.oi.presentation.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme

@Composable
fun ScheduleScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier
            .weight(1f)
            .fillMaxSize()) {
            Text("캘린더 영역")
        }
        Column(
            modifier = modifier
                .weight(1f)
                .fillMaxSize()
                .background(OiTheme.colors.backgroundContents)
        ) {
            Text("일정 리스트 영역")
        }
    }
}