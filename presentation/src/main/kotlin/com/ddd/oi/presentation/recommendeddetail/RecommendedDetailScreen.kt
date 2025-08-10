package com.ddd.oi.presentation.recommendeddetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ddd.oi.domain.model.Content
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.component.common.OiSpotCard
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme

@Composable
fun RecommendedDetailScreen(
    modifier: Modifier = Modifier,
    contentId: Long,
    onNavigateBack: () -> Unit = {},
    viewModel: RecommendedDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(contentId) {
        viewModel.getContentById(contentId)
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        OiHeader(
            onLeftClick = onNavigateBack,
            title = uiState.content.title,
        )

        if (uiState.isLoading) {
            // Loading state - you can add a loading indicator here
        } else if (uiState.error != null) {
            // Error state - you can add error UI here
        } else {
            RecommendedDetailContent(content = uiState.content)
            RecommendedDetailPlaceContent(content = uiState.content)
        }
    }
}

@Composable
private fun RecommendedDetailContent(content: Content) {
    Column {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2F),
            model = ImageRequest.Builder(LocalContext.current)
                .data(content.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        RecommendedDetailContentText(content = content)
    }
}

@Composable
private fun RecommendedDetailContentText(
    modifier: Modifier = Modifier,
    content: Content
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp, bottom = 24.dp)
            .padding(horizontal = 18.dp)
    ) {
        Text(
            text = "코스 소개",
            color = OiTheme.colors.textPrimary,
            style = OiTheme.typography.bodySmallSemibold,
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = content.shortDescription,
            color = OiTheme.colors.textSecondary,
            style = OiTheme.typography.bodySmallMedium,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RecommendedDetailContentTag(
                modifier = Modifier.weight(1F),
                title = "추천일정",
                content = content.recommendedSchedule
            )

            RecommendedDetailContentTag(
                modifier = Modifier.weight(1F),
                title = "소요시간",
                content = "약 ${content.duration.div(60)}시간"
            )

            RecommendedDetailContentTag(
                modifier = Modifier.weight(1F),
                title = "예상경비",
                content = "${content.cost.div(10_000)}만원"
            )

            RecommendedDetailContentTag(
                modifier = Modifier.weight(1F),
                title = "방문장소",
                content = "${content.spots.size}개"
            )
        }
    }
}

@Composable
private fun RecommendedDetailContentTag(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = OiTheme.colors.backgroundContents),
        elevation = CardDefaults.cardElevation(1.dp),
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 7.5.dp,
                vertical = 8.dp
            )
        ) {
            Text(
                text = title,
                style = OiTheme.typography.bodyXSmallMedium,
                color = OiTheme.colors.textTertiary,
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = content,
                style = OiTheme.typography.bodyXSmallSemibold,
                color = OiTheme.colors.textPrimary,
            )
        }
    }
}

@Composable
private fun RecommendedDetailPlaceContent(content: Content? = null) {
    val spots = content?.spots ?: emptyList()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        itemsIndexed(spots) { index, spot ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (index > 0) {
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .width(48.dp)
                            .height(28.dp)
                            .align(Alignment.TopStart)
                            .zIndex(1f)
                            .offset(y = -14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFFFFF)
                        ),
                        border = BorderStroke(1.dp, OiTheme.colors.borderPrimary),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(
                                2.dp,
                                Alignment.CenterHorizontally
                            )
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(R.drawable.ic_route),
                                contentDescription = "",
                                tint = Color.Unspecified
                            )

                            Icon(
                                modifier = Modifier.size(8.dp),
                                painter = painterResource(R.drawable.ic_chevron_right),
                                contentDescription = "",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFAFAFA))
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(48.dp)
                                .fillMaxHeight(),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.Center)
                                    .background(
                                        color = Color(0XFFA052F0),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index.inc()}",
                                    style = OiTheme.typography.bodyXSmallSemibold,
                                    color = Color.White
                                )
                            }
                        }

                        OiSpotCard(
                            placeName = spot.name,
                            category = "관광지",
                            address = spot.address,
                            imageUrl = spot.imageUrl ?: "https://picsum.photos/64/64"
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun RecommendedDetailScreenPreview() {
    RecommendedDetailScreen(contentId = 1L)
}