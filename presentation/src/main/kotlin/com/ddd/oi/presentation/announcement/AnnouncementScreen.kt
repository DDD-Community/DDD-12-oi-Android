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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ddd.oi.domain.model.Announcement
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.rememberThrottledNavigation
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class AnnouncementItem(
    val id: Long,
    val title: String,
    val date: String,
    val content: String
)

private fun formatDate(dateString: String): String {
    return try {
        val isoDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        isoDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    } catch (e: Exception) {
        dateString // 파싱 실패 시 원본 반환
    }
}

@Composable
fun AnnouncementScreen(
    onBack: () -> Unit = {},
    viewModel: AnnouncementViewModel = hiltViewModel()
) {
    val throttledNavigation = rememberThrottledNavigation()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val announcements by viewModel.announcements.collectAsStateWithLifecycle()

    if (uiState.isLoading && announcements.isEmpty()) {
        AnnouncementLoadingScreen(onBack = { throttledNavigation(onBack) })
    } else {
        AnnouncementContent(
            onBack = { throttledNavigation(onBack) },
            uiState = uiState,
            announcements = announcements,
            onRefresh = viewModel::refresh,
            onLoadMore = viewModel::loadMoreAnnouncements
        )
    }
}

@Composable
private fun AnnouncementContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    uiState: AnnouncementUiState = AnnouncementUiState(),
    announcements: List<Announcement> = emptyList(),
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleIndex >= announcements.size - 3 && uiState.hasMorePages && !uiState.isLoading
        }
    }
    
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onLoadMore()
        }
    }

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
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp),
                    text = "총 ${uiState.totalCount}건",
                    style = OiTheme.typography.bodyMediumMedium,
                    color = OiTheme.colors.textBrand
                )
            }

            items(announcements.size) { index ->
                val announcement = announcements[index]
                AnnouncementItemView(
                    announcement = AnnouncementItem(
                        id = announcement.id,
                        title = announcement.title,
                        date = formatDate(announcement.createdAt),
                        content = announcement.content
                    )
                )
            }
            
            if (uiState.isLoading && announcements.isNotEmpty()) {
                item {
                    AnnouncementLoadingIndicator()
                }
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

@Composable
private fun AnnouncementLoadingScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    var currentFrame by remember { mutableIntStateOf(1) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(100)
            currentFrame = if (currentFrame == 8) 1 else currentFrame + 1
        }
    }

    val loadingIcons = listOf(
        R.drawable.ic_loading_1,
        R.drawable.ic_loading_2,
        R.drawable.ic_loading_3,
        R.drawable.ic_loading_4,
        R.drawable.ic_loading_5,
        R.drawable.ic_loading_6,
        R.drawable.ic_loading_7,
        R.drawable.ic_loading_8
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        ) {
            Icon(
                painter = painterResource(loadingIcons[currentFrame - 1]),
                contentDescription = "",
                tint = Color.Unspecified,
            )

            Text(
                text = "화면을 불러오고 있어요",
                style = OiTheme.typography.headlineSmallBold,
                color = OiTheme.colors.textPrimary,
            )
        }
    }
}

@Composable
private fun AnnouncementLoadingIndicator(
    modifier: Modifier = Modifier
) {
    var currentFrame by remember { mutableIntStateOf(1) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(100)
            currentFrame = if (currentFrame == 8) 1 else currentFrame + 1
        }
    }

    val loadingIcons = listOf(
        R.drawable.ic_loading_1,
        R.drawable.ic_loading_2,
        R.drawable.ic_loading_3,
        R.drawable.ic_loading_4,
        R.drawable.ic_loading_5,
        R.drawable.ic_loading_6,
        R.drawable.ic_loading_7,
        R.drawable.ic_loading_8
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(loadingIcons[currentFrame - 1]),
            contentDescription = "",
            tint = Color.Unspecified,
        )
        
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "로딩 중...",
            style = OiTheme.typography.bodyMediumRegular,
            color = OiTheme.colors.textSecondary
        )
    }
}

@Preview
@Composable
private fun AnnouncementScreenPreview() {
    AnnouncementContent()
}