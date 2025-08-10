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
import androidx.compose.ui.res.stringResource
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
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.common.OiDotList
import com.ddd.oi.presentation.core.designsystem.component.common.OiRoundRectChip
import com.ddd.oi.presentation.core.designsystem.component.common.OiScheduleCard
import com.ddd.oi.presentation.core.designsystem.component.mapper.getCategoryName
import com.ddd.oi.presentation.core.designsystem.component.mapper.toStringResource
import com.ddd.oi.presentation.core.designsystem.component.mapper.toUi
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.OiWeeklyCalendar
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.OiCardDimens
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
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
    onNavigateToScheduleCreate: () -> Unit = {},
    onNavigateToScheduleTab: () -> Unit = {},
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
        onNavigateToScheduleCreate = onNavigateToScheduleCreate,
        onNavigateToScheduleTab = onNavigateToScheduleTab,
        contentsList = uiState.filteredContents,
        selectedCategory = uiState.selectedCategory,
        weeklySchedules = uiState.weeklySchedules,
        selectedDate = uiState.selectedDate,
        selectedDateSchedules = uiState.selectedDateSchedules,
        onDateSelected = viewModel::selectDate,
        onCategorySelected = viewModel::selectCategory,
        hasError = uiState.error != null
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onNavigateToRecommendedList: () -> Unit = {},
    onNavigateToRecommendedDetail: (Long) -> Unit = {},
    onNavigateToScheduleCreate: () -> Unit = {},
    onNavigateToScheduleTab: () -> Unit = {},
    contentsList: List<Content> = emptyList(),
    selectedCategory: RecommendedCategory = RecommendedCategory.ALL,
    weeklySchedules: Map<LocalDate, List<Schedule>> = emptyMap(),
    selectedDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    selectedDateSchedules: List<Schedule> = emptyList(),
    onDateSelected: (LocalDate) -> Unit = {},
    onCategorySelected: (RecommendedCategory) -> Unit = {},
    hasError: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeHeader()

        HomeWeeklySchedule(
            weeklySchedules = weeklySchedules,
            selectedDate = selectedDate,
            selectedDateSchedules = selectedDateSchedules,
            onDateSelected = onDateSelected,
            onNavigateToScheduleCreate = onNavigateToScheduleCreate,
            onNavigateToScheduleTab = onNavigateToScheduleTab
        )

        HomeRecommendedCourse(
            onNavigateToRecommendedList = onNavigateToRecommendedList,
            onNavigateToRecommendedDetail = onNavigateToRecommendedDetail,
            contentsList = contentsList,
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected,
            hasError = hasError
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
    weeklySchedules: Map<LocalDate, List<Schedule>>,
    selectedDate: LocalDate,
    selectedDateSchedules: List<Schedule>,
    onDateSelected: (LocalDate) -> Unit,
    onNavigateToScheduleCreate: () -> Unit = {},
    onNavigateToScheduleTab: () -> Unit = {}
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
            onRightArrowClick = onNavigateToScheduleTab
        )

        WeeklyScheduleContent(
            modifier = Modifier.padding(horizontal = 16.dp),
            weeklySchedules = weeklySchedules,
            selectedDate = selectedDate,
            selectedDateSchedules = selectedDateSchedules,
            onDateSelected = onDateSelected,
            onNavigateToScheduleCreate = onNavigateToScheduleCreate
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
    weeklySchedules: Map<LocalDate, List<Schedule>>,
    selectedDate: LocalDate,
    selectedDateSchedules: List<Schedule>,
    onDateSelected: (LocalDate) -> Unit,
    onNavigateToScheduleCreate: () -> Unit = {}
) {
    val currentDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    // 이번 주의 시작일 계산 (일요일부터)
    val sundayOffset =
        if (currentDate.dayOfWeek == DayOfWeek.SUNDAY) 0 else 7 - currentDate.dayOfWeek.ordinal
    val startOfWeek = currentDate.minus(sundayOffset, DateTimeUnit.DAY)

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
            selectedDate = selectedDate,
            onDateSelected = onDateSelected
        )

        OiDotList(
            modifier = modifier,
            dotList = dotList
        )

        if (selectedDateSchedules.isNotEmpty()) {
            // 선택된 날짜의 스케줄 카드들 표시
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selectedDateSchedules) { schedule ->
                    val dayOffset = calculateDayOffset(schedule.startedAt, currentDate)
                    OiScheduleCard(
                        categoryText = stringResource(schedule.category.toUi().getCategoryName()),
                        categoryTextColor = Color(0xFFF98247),
                        dayOffset = dayOffset,
                        titleText = schedule.title,
                        partnerList = schedule.partySet.map { stringResource(it.toStringResource()) },
                        date = "${schedule.startedAt} - ${schedule.endedAt}"
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(125.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "등록된 일정이 없어요",
                    style = OiTheme.typography.bodyLargeSemibold,
                    color = OiTheme.colors.textDisabled,
                )

                OiButton(
                    style = OiButtonStyle.Medium40Rect,
                    leftIconDrawableRes = R.drawable.ic_add_plus,
                    title = "일정 추가",
                    onClick = onNavigateToScheduleCreate
                )
            }
        }
    }
}

private fun calculateDayOffset(startDate: LocalDate, currentDate: LocalDate): Int {
    val startDateInDays = startDate.toEpochDays()
    val currentDateInDays = currentDate.toEpochDays()
    return startDateInDays - currentDateInDays
}

@Composable
private fun HomeRecommendedCourse(
    modifier: Modifier = Modifier,
    onNavigateToRecommendedList: () -> Unit = {},
    onNavigateToRecommendedDetail: (Long) -> Unit = {},
    contentsList: List<Content>,
    selectedCategory: RecommendedCategory = RecommendedCategory.ALL,
    onCategorySelected: (RecommendedCategory) -> Unit = {},
    hasError: Boolean = false,
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
            currentCategory = selectedCategory,
            onCategoryClick = onCategorySelected,
            onNavigateToRecommendedDetail = onNavigateToRecommendedDetail,
            contentsList = contentsList,
            hasError = hasError
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
    hasError: Boolean = false,
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

        if (contentsList.isEmpty() && hasError) {
            Column(
                modifier = modifier.padding(vertical = 24.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "데이터를 불러올 수 없습니다.",
                    style = OiTheme.typography.bodyLargeSemibold,
                    color = OiTheme.colors.textTertiary,
                )

                Icon(
                    painter = painterResource(R.drawable.ic_content_empty),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
        } else if (contentsList.isNotEmpty()) {
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
                        tag = getTagString(content.badge),
                        title = content.title,
                        description = content.displayDescription,
                        imageUrl = content.imageUrl
                    )
                }
            }
        }
    }
}

enum class Badge(val tag: String) {
    POPULAR("인기"), LATEST("최신")
}

private fun getTagString(badge: String): String {
    return runCatching { Badge.valueOf(badge).tag }.getOrNull() ?: ""
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
    ALL("전체"),
    TRAVEL("여행"),
    DATE("데이트"),
    FRIEND("친구랑"),
    FAMILY("가족과")
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
