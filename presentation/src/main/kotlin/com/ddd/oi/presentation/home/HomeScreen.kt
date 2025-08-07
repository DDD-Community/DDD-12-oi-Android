package com.ddd.oi.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ddd.oi.domain.model.Content
import com.ddd.oi.domain.model.Spot
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiDotList
import com.ddd.oi.presentation.core.designsystem.component.common.OiRoundRectChip
import com.ddd.oi.presentation.core.designsystem.component.common.OiScheduleCard
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.OiWeeklyCalendar
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.OiCardDimens
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToRecommendedList: () -> Unit = {},
    onNavigateToRecommendedDetail: (Long) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getContents()
    }
    HomeContent(
        modifier = modifier,
        onNavigateToRecommendedList = onNavigateToRecommendedList,
        onNavigateToRecommendedDetail = onNavigateToRecommendedDetail,
        contentsList = uiState.contents,
        weeklySchedules = uiState.weeklySchedules
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onNavigateToRecommendedList: () -> Unit = {},
    onNavigateToRecommendedDetail: (Long) -> Unit = {},
    contentsList: List<Content> = emptyList(),
    weeklySchedules: Map<LocalDate, List<Schedule>> = emptyMap(),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeHeader()

        HomeWeeklySchedule(
            weeklySchedules = weeklySchedules
        )

        HomeRecommendedCourse(
            onNavigateToRecommendedList = onNavigateToRecommendedList,
            onNavigateToRecommendedDetail = onNavigateToRecommendedDetail,
            contentsList = contentsList
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
    }
}

@Composable
private fun HomeHeader(
    modifier: Modifier = Modifier,
    onLogoClick: () -> Unit = {},
    onSettingClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .clickable(onClick = onLogoClick),
            painter = painterResource(R.drawable.ic_text_icon),
            tint = Color.Unspecified,
            contentDescription = "",
        )

        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .clickable(onClick = onSettingClick),
            painter = painterResource(R.drawable.ic_setting_filled),
            tint = Color.Unspecified,
            contentDescription = ""
        )
    }
}

@Composable
private fun HomeWeeklySchedule(
    modifier: Modifier = Modifier,
    weeklySchedules: Map<LocalDate, List<Schedule>>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        val totalScheduleCount = weeklySchedules.values.sumOf { it.size }
        
        WeeklyScheduleTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            scheduleCount = totalScheduleCount,
            onRightArrowClick = {}
        )

        WeeklyScheduleContent(
            modifier = Modifier.padding(horizontal = 16.dp),
            weeklySchedules = weeklySchedules
        )
    }
}

@Composable
private fun WeeklyScheduleTitle(
    modifier: Modifier = Modifier,
    scheduleCount: Int,
    onRightArrowClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "이번주 내 일정",
            style = OiTheme.typography.headlineSmallBold,
            color = OiTheme.colors.textPrimary,
        )

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "$scheduleCount",
            style = OiTheme.typography.headlineSmallBold,
            color = OiTheme.colors.textBrand,
        )

        Spacer(modifier = Modifier.weight(1F))

        Icon(
            modifier = Modifier.clickable(onClick = onRightArrowClick),
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = "",
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun WeeklyScheduleContent(
    modifier: Modifier = Modifier,
    weeklySchedules: Map<LocalDate, List<Schedule>>
) {
    val currentDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
        
    // 이번 주의 시작일 계산 (월요일부터)
    val startOfWeek = currentDate.minus(currentDate.dayOfWeek.ordinal, DateTimeUnit.DAY)
    
    // 7일간의 dot 리스트 생성
    val dotList = (0..6).map { dayOffset ->
        val targetDate = startOfWeek.plus(dayOffset, DateTimeUnit.DAY)
        val schedulesForDay = weeklySchedules[targetDate] ?: emptyList()
        // 스케줄 개수에 따라 색상 점 생성 (최대 3개까지 표시)
        schedulesForDay.take(3).map { Color.Red } // 임시로 빨간색으로 설정
    }
    
    Column {
        OiWeeklyCalendar(
            modifier = modifier,
            today = currentDate,
            selectedDate = currentDate.plus(1, DateTimeUnit.DAY)
        )

        OiDotList(
            modifier = modifier,
            dotList = dotList
        )

        // 이번 주 스케줄 카드들 표시
        val allWeeklySchedules = weeklySchedules.values.flatten().take(3) // 최대 3개까지만 표시
        
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(allWeeklySchedules) { schedule ->
                OiScheduleCard(
                    categoryText = schedule.category.name,
                    categoryTextColor = Color(0xFFF98247),
                    dayOffset = 0, // TODO: 실제 날짜 차이 계산
                    titleText = schedule.title,
                    partnerList = schedule.partySet.map { it.name },
                    date = "${schedule.startedAt} - ${schedule.endedAt}"
                )
            }
        }
    }
}

@Composable
private fun HomeRecommendedCourse(
    modifier: Modifier = Modifier,
    onNavigateToRecommendedList: () -> Unit = {},
    onNavigateToRecommendedDetail: (Long) -> Unit = {},
    contentsList: List<Content>,
    ) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 24.dp + 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        RecommendedCourseTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            onRightArrowClick = onNavigateToRecommendedList
        )

        RecommendedCourseContent(
            modifier = Modifier.padding(horizontal = 16.dp),
            currentCategory = RecommendedCategory.All,
            onCategoryClick = {},
            onNavigateToRecommendedDetail = onNavigateToRecommendedDetail,
            contentsList = contentsList
        )
    }
}

@Composable
private fun RecommendedCourseTitle(
    modifier: Modifier = Modifier,
    onRightArrowClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = getRecommendedCourseTitle())

        Spacer(modifier = Modifier.weight(1F))

        Icon(
            modifier = Modifier.clickable(onClick = onRightArrowClick),
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = "",
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun RecommendedCourseContent(
    modifier: Modifier = Modifier,
    contentsList: List<Content>,
    currentCategory: RecommendedCategory,
    onCategoryClick: (RecommendedCategory) -> Unit,
    onNavigateToRecommendedDetail: (Long) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = modifier,
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

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(contentsList) { content ->
                RecommendedCourseItem(
                    onClick = { onNavigateToRecommendedDetail(content.id) },
                    tag = "인기",
                    title = content.title,
                    description = content.displayDescription,
                    imageUrl = content.imageUrl
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
    imageUrl: String,
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
                    modifier = Modifier.size(148.dp),
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

enum class RecommendedCategory(
    val text: String
) {
    All("전체"),
    Travel("여행"),
    Date("데이트"),
    Friend("친구랑"),
    Partner("연인과")
}

@Composable
private fun getRecommendedCourseTitle(): AnnotatedString {
    return buildAnnotatedString {
        val defaultStyle = SpanStyle(
            color = OiTheme.colors.textPrimary,
            fontWeight = OiTheme.typography.headlineSmallBold.fontWeight,
            fontSize = OiTheme.typography.headlineSmallBold.fontSize,
            letterSpacing = OiTheme.typography.headlineSmallBold.letterSpacing
        )

        val emphasizeStyle = SpanStyle(
            color = OiTheme.colors.textBrand,
            fontWeight = OiTheme.typography.headlineSmallBold.fontWeight,
            fontSize = OiTheme.typography.headlineSmallBold.fontSize,
            letterSpacing = OiTheme.typography.headlineSmallBold.letterSpacing
        )

        withStyle(ParagraphStyle(lineHeight = OiTheme.typography.headlineSmallBold.lineHeight)) {
            withStyle(defaultStyle) {
                append("이번주, ")
            }

            withStyle(emphasizeStyle) {
                append("국내 인기코스 ")
            }

            withStyle(defaultStyle) {
                append("추천드려요")
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeContent()
}
