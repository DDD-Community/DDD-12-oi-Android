package com.ddd.oi.presentation.recommendedlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ddd.oi.domain.model.Content
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.component.common.OiRoundRectChip
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.OiCardDimens
import com.ddd.oi.presentation.home.Badge
import com.ddd.oi.presentation.home.RecommendedCategory

@Composable
fun RecommendedListScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (Long) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    viewModel: RecommendedListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getContents()
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        OiHeader(
            onLeftClick = onNavigateBack,
            title = "추천 코스 모아보기",
        )

        if (uiState.isLoading) {
            RecommendedLoadingScreen()
        } else if (uiState.error != null) {
            RecommendedErrorScreen()
        } else {
            RecommendedCourseContent(
                currentCategory = uiState.selectedCategory,
                onCategoryClick = viewModel::selectCategory,
                onNavigateToRecommendedDetail = onNavigateToDetail,
                contentsList = uiState.filteredContents
            )
        }
    }
}

@Composable
private fun RecommendedCourseContent(
    modifier: Modifier = Modifier,
    currentCategory: RecommendedCategory,
    onCategoryClick: (RecommendedCategory) -> Unit,
    onNavigateToRecommendedDetail: (Long) -> Unit,
    contentsList: List<Content>
) {
    Column(
        modifier = modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RecommendedCategory.entries.forEach { recommendedCategory ->
                OiRoundRectChip(
                    isSelected = recommendedCategory == currentCategory,
                    tag = recommendedCategory.name,
                    text = recommendedCategory.text,
                    onItemClick = { onCategoryClick.invoke(recommendedCategory) }
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "인기순",
                    style = OiTheme.typography.bodyMediumSemibold,
                    color = OiTheme.colors.textPrimary
                )

                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "",
                    tint = Color.Unspecified,
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(contentsList) { content ->
                RecommendedCourseItem(
                    onClick = { onNavigateToRecommendedDetail(content.id) },
                    tag = getTagString(content.badge),
                    title = content.title,
                    description = content.displayDescription,
                    imageUrl = content.imageUrl
                )
            }
        }
    }
}

private fun getTagString(badge: String): String {
    return runCatching { Badge.valueOf(badge).tag }.getOrNull()?:""
}

@Composable
private fun RecommendedCourseItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tag: String,
    title: String,
    description: String,
    imageUrl: String
) {
    Column {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(OiCardDimens.cornerRadius),
            elevation = CardDefaults.cardElevation(1.dp),
            onClick = onClick
        ) {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1F),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

                Text(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            top = 8.dp
                        )
                        .background(
                            color = Color(0xB3262626),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                    text = tag,
                    color = Color.White,
                )
            }
        }

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = title,
            style = OiTheme.typography.bodyMediumSemibold,
            color = Color.Black
        )

        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = description,
            style = OiTheme.typography.bodySmallRegular,
            color = OiTheme.colors.textTertiary
        )
    }
}

@Composable
private fun RecommendedErrorScreen(
    modifier: Modifier = Modifier,
    onRefreshClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "연결상태가 불안정해요\n다시 시도 해주세요",
            textAlign = TextAlign.Center,
            style = OiTheme.typography.headlineSmallBold,
            color =  OiTheme.colors.textPrimary,
        )

        Icon(
            modifier = Modifier.padding(top = 18.dp),
            painter = painterResource(R.drawable.ic_error),
            contentDescription = "",
            tint = Color.Unspecified,
        )

        OiButton(
            modifier = Modifier.padding(top = 32.dp),
            style = OiButtonStyle.Medium40Rect,
            title = "다시 시도",
            leftIconDrawableRes = R.drawable.ic_refresh,
            onClick = onRefreshClick
        )
    }
}

@Composable
private fun RecommendedLoadingScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_loading_1),
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