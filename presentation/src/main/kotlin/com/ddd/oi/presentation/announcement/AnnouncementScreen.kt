package com.ddd.oi.presentation.announcement

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.rememberThrottledNavigation

data class AnnouncementItem(
    val id: Long,
    val title: String,
    val date: String,
    val content: String
)

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
    val announcements = listOf(
        AnnouncementItem(
            1L,
            "서비스 업데이트 안내",
            "2024.01.15",
            "안녕하세요. OI 앱의 새로운 업데이트가 출시되었습니다. 이번 업데이트에서는 사용자 편의성 개선과 버그 수정이 포함되어 있습니다."
        ),
        AnnouncementItem(
            2L,
            "개인정보 처리방침 개정 안내",
            "2024.01.10",
            "개인정보 처리방침이 일부 개정되었습니다. 변경된 내용을 확인하시고 동의해 주시기 바랍니다."
        ),
        AnnouncementItem(
            3L,
            "신규 기능 출시 알림",
            "2024.01.05",
            "새로운 일정 관리 기능이 추가되었습니다. 더욱 편리한 일정 관리를 경험해보세요."
        ),
        AnnouncementItem(
            4L,
            "시스템 점검 안내",
            "2024.01.01",
            "서비스 안정성 향상을 위한 시스템 점검이 예정되어 있습니다. 점검 시간 동안 일시적으로 서비스 이용이 제한될 수 있습니다."
        ),
        AnnouncementItem(
            5L,
            "앱 버전 업데이트 안내",
            "2023.12.28",
            "최신 버전의 앱으로 업데이트하시면 더 나은 성능과 새로운 기능을 이용하실 수 있습니다."
        )
    )

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp),
                    text = "총 ${announcements.size}건",
                    style = OiTheme.typography.bodyMediumMedium,
                    color = OiTheme.colors.textBrand
                )
            }

            items(announcements) { announcement ->
                AnnouncementItemView(announcement = announcement)
            }
        }
    }
}

@Composable
private fun AnnouncementItemView(
    modifier: Modifier = Modifier,
    announcement: AnnouncementItem
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = announcement.title,
                    style = OiTheme.typography.bodyLargeMedium,
                    color = OiTheme.colors.textPrimary
                )

                Text(
                    text = announcement.date,
                    style = OiTheme.typography.bodyMediumRegular,
                    color = OiTheme.colors.textTertiary
                )
            }

            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(
                    if (isExpanded) {
                        R.drawable.ic_chevron_up
                    } else {
                        R.drawable.ic_chevron_down
                    }
                ),
                contentDescription = if (isExpanded) "접기" else "펼치기",
                tint = OiTheme.colors.iconSecondary
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Text(
                modifier = Modifier
                    .background(color = OiTheme.colors.backgroundContents)
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                text = announcement.content,
                style = OiTheme.typography.bodyMediumRegular,
                color = OiTheme.colors.textSecondary
            )
        }
    }
}

@Preview
@Composable
private fun AnnouncementScreenPreview() {
    AnnouncementContent()
}