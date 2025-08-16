package com.ddd.oi.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.rememberThrottledNavigation

@Composable
fun SettingScreen(
    onBack: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToAnnouncement: () -> Unit = {},
    onNavigateToContactUs: () -> Unit = {},
    viewModel: SettingViewModel = hiltViewModel()
) {
    val throttledNavigation = rememberThrottledNavigation()
    
    SettingContent(
        onBack = { throttledNavigation(onBack) },
        onNavigateToProfile = { throttledNavigation(onNavigateToProfile) },
        onNavigateToAnnouncement = { throttledNavigation(onNavigateToAnnouncement) },
        onNavigateToContactUs = { throttledNavigation(onNavigateToContactUs) }
    )
}

@Composable
private fun SettingContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToAnnouncement: () -> Unit = {},
    onNavigateToContactUs: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(white),
        containerColor = white,
        topBar = {
            OiHeader(
                onLeftClick = onBack,
                title = "설정",
                isDividerVisible = true
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            SettingMenuItem(
                title = "프로필 관리",
                onClick = onNavigateToProfile
            )
            
            SettingMenuItem(
                title = "공지사항",
                onClick = onNavigateToAnnouncement
            )
            
            SettingMenuItem(
                title = "문의하기",
                onClick = onNavigateToContactUs
            )
        }
    }
}

@Composable
private fun SettingMenuItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = OiTheme.typography.bodyLargeMedium,
            color = OiTheme.colors.textPrimary
        )
        
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingContent()
}