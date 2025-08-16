package com.ddd.oi.presentation.announcement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.rememberThrottledNavigation

@Composable
fun AnnouncementScreen(
    onBack: () -> Unit = {},
    viewModel: AnnouncementViewModel = hiltViewModel()
) {
    val throttledNavigation = rememberThrottledNavigation()
    
    AnnouncementContent(
        onBack = { throttledNavigation(onBack) }
    )
}

@Composable
private fun AnnouncementContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(white),
        containerColor = white,
        topBar = {
            OiHeader(
                onLeftClick = onBack,
                title = "공지사항",
                isDividerVisible = true
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "공지사항",
                    style = OiTheme.typography.headlineMediumBold,
                    color = OiTheme.colors.textPrimary
                )
                
                Text(
                    text = "추후 구현 예정입니다.",
                    style = OiTheme.typography.bodyLargeMedium,
                    color = OiTheme.colors.textSecondary
                )
            }
        }
    }
}

@Preview
@Composable
private fun AnnouncementScreenPreview() {
    AnnouncementContent()
}