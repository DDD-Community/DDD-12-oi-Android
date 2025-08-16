package com.ddd.oi.presentation.setting

import android.graphics.drawable.shapes.OvalShape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.rememberThrottledNavigation

sealed class SettingMenuItem(
    val title: String,
    val hasNewBadge: Boolean = false,
    val rightText: String? = null,
    val onClick: () -> Unit
) {
    class Announcement(onClick: () -> Unit) : SettingMenuItem("공지사항", true, null, onClick)
    class ContactUs(onClick: () -> Unit) : SettingMenuItem("문의하기", false, null, onClick)
    class TermsOfService(onClick: () -> Unit) : SettingMenuItem("서비스 이용약관", false, null, onClick)
    class PrivacyPolicy(onClick: () -> Unit) : SettingMenuItem("개인정보 처리 방침", false, null, onClick)
    class AppVersion(onClick: () -> Unit) : SettingMenuItem("앱 버전", false, "1.0.0", onClick)
}

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
            modifier = Modifier.padding(padding)
        ) {
            SettingProfileContent(
                onNavigateToProfile = onNavigateToProfile
            )

            SettingListContent(
                onNavigateToAnnouncement = onNavigateToAnnouncement,
                onNavigateToContactUs = onNavigateToContactUs,
            )
        }
    }
}

@Composable
private fun SettingProfileContent(
    modifier: Modifier = Modifier,
    onNavigateToProfile: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 32.dp)
                .size(80.dp)
                .background(
                    shape = CircleShape,
                    color = OiTheme.colors.backgroundContents
                )
                .border(
                    width = 1.dp,
                    color = OiTheme.colors.borderPrimary,
                    shape = CircleShape
                ),
            painter = painterResource(R.drawable.ic_oi_default_profile),
            contentDescription = "",
            tint = Color.Unspecified,
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "오늘의이동오늘의이동오늘의이동",
            style = OiTheme.typography.headlineMediumBold,
            color = Color(0xFF000000),
        )

        Row(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.ic_kakao_small),
                contentDescription = "",
                tint = Color.Unspecified
            )

            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "Avocado@kakao.com",
                style = OiTheme.typography.bodyMediumRegular,
                color = OiTheme.colors.textTertiary,
            )
        }

        OiButton(
            modifier = Modifier.padding(top = 20.dp, bottom = 32.dp),
            onClick = onNavigateToProfile,
            style = OiButtonStyle.Medium40Rect,
            title = "프로필 관리"
        )
    }
}

@Composable
private fun SettingListContent(
    modifier: Modifier = Modifier,
    onNavigateToAnnouncement: () -> Unit = {},
    onNavigateToContactUs: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(
            listOf(
                SettingMenuItem.Announcement(onClick = onNavigateToAnnouncement),
                SettingMenuItem.ContactUs(onClick = onNavigateToContactUs),
                SettingMenuItem.TermsOfService(onClick = { }),
                SettingMenuItem.PrivacyPolicy(onClick = { }),
                SettingMenuItem.AppVersion(onClick = { })
            )
        ) { item ->
            SettingMenuItemView(
                menuItem = item
            )
        }
    }
}

@Composable
private fun SettingMenuItemView(
    modifier: Modifier = Modifier,
    menuItem: SettingMenuItem
) {
    Box {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { menuItem.onClick() }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = menuItem.title,
                    style = OiTheme.typography.bodyLargeMedium,
                    color = OiTheme.colors.textPrimary
                )

                if (menuItem.hasNewBadge) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.ic_n_badge),
                        contentDescription = "새 알림",
                        tint = Color.Unspecified
                    )
                }
            }

            if (menuItem.rightText != null) {
                Text(
                    text = menuItem.rightText,
                    style = OiTheme.typography.bodyLargeMedium,
                    color = OiTheme.colors.textSecondary
                )
            } else {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            thickness = 1.dp,
            color = OiTheme.colors.borderSecondary,
        )
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingContent()
}