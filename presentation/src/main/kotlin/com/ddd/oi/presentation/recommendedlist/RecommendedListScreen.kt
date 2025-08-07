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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.component.common.OiRoundRectChip
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.OiCardDimens
import com.ddd.oi.presentation.home.RecommendedCategory

@Composable
fun RecommendedListScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (Long) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        OiHeader(
            onLeftClick = {},
            title = "추천 코스 모아보기",
        )

        RecommendedCourseContent(
            currentCategory = RecommendedCategory.All,
            onCategoryClick = { _ -> },
            onNavigateToRecommendedDetail = { onNavigateToDetail(1L) }
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Recommended List Screen")

            OiButton(
                title = "Go to Detail",
                style = OiButtonStyle.Large48Oval,
                onClick = { onNavigateToDetail(1L) },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
private fun RecommendedCourseContent(
    modifier: Modifier = Modifier,
    currentCategory: RecommendedCategory,
    onCategoryClick: (RecommendedCategory) -> Unit,
    onNavigateToRecommendedDetail: () -> Unit,
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
            items(20) {
                RecommendedCourseItem(
                    onClick = onNavigateToRecommendedDetail,
                    tag = "인기",
                    title = "한강 드라이브 코스",
                    description = "종로구 · 10만원대"
                )
            }
        }
    }
}

@Composable
private fun RecommendedCourseItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tag: String,
    title: String,
    description: String,
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
                        .data("https://picsum.photos/id/237/200/300")
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