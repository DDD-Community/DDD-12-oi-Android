package com.ddd.oi.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.common.OiRoundRectChip
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToRecommendedList: () -> Unit = {},
    onNavigateToRecommendedDetail: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeContent(
        modifier = modifier,
        onNavigateToRecommendedList = onNavigateToRecommendedList,
        onNavigateToRecommendedDetail = onNavigateToRecommendedDetail
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onNavigateToRecommendedList: () -> Unit = {},
    onNavigateToRecommendedDetail: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeHeader()

        HomeWeeklySchedule(scheduleList = emptyList())

        HomeRecommendedCourse(
            onNavigateToRecommendedList = onNavigateToRecommendedList,
            onNavigateToRecommendedDetail = onNavigateToRecommendedDetail
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
    scheduleList: List<Schedule>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        WeeklyScheduleTitle(
            scheduleCount = scheduleList.size,
            onRightArrowClick = {}
        )

        WeeklyScheduleContent()
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
    modifier: Modifier = Modifier
) {
    // todo set weeklyCalendar
}

@Composable
private fun HomeRecommendedCourse(
    modifier: Modifier = Modifier,
    onNavigateToRecommendedList: () -> Unit = {},
    onNavigateToRecommendedDetail: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        RecommendedCourseTitle(
            onRightArrowClick = onNavigateToRecommendedList
        )

        RecommendedCourseContent(
            currentCategory = RecommendedCategory.All,
            onCategoryClick = {}
        )
    }

    OiButton(
        title = "Go to Recommended Detail",
        style = OiButtonStyle.Large48Oval,
        onClick = onNavigateToRecommendedDetail,
        modifier = Modifier.padding(top = 8.dp)
    )
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
    currentCategory: RecommendedCategory,
    onCategoryClick: (RecommendedCategory) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier,
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

        LazyRow {

        }
    }
}

@Composable
private fun RecommendedCourseItem() {

}

private enum class RecommendedCategory(
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
