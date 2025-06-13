package com.ddd.oi.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier
            .weight(1f)
            .fillMaxSize()) {
            Text("홈 영역")
        }
        Column(
            modifier = modifier
                .weight(1f)
                .fillMaxSize()
                .background(OiTheme.colors.backgroundContents)
        ) {
            Text("홈 영역2")
        }
    }
}