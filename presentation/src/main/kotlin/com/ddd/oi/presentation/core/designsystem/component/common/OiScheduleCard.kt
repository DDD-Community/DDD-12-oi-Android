package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.OiCardDimens
import kotlin.math.abs

@Composable
fun OiScheduleCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    categoryText: String,
    categoryTextColor: Color,
    dayOffset: Int,
    titleText: String,
    partnerList: List<String>,
    date: String,
) {
    Card(
        modifier = modifier.width(200.dp).height(125.dp),
        shape = RoundedCornerShape(OiCardDimens.cornerRadius),
        colors = CardDefaults.cardColors(containerColor = white),
        elevation = CardDefaults.cardElevation(1.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = categoryText,
                    color = categoryTextColor,
                    style = OiTheme.typography.bodySmallSemibold
                )

                Spacer(
                    modifier = Modifier.weight(1F)
                )

                OiDDayBadge(
                    dayOffset = dayOffset
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                text = titleText,
                color = Color.Black,
                style = OiTheme.typography.bodyLargeSemibold
            )

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(16.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(R.drawable.ic_person),
                    contentDescription = "",
                    tint = Color.Unspecified
                )

                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = partnerList.joinToString(" · "),
                    color = OiTheme.colors.textTertiary,
                    style = OiTheme.typography.bodySmallMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .height(16.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(R.drawable.ic_card_calendar),
                    contentDescription = "",
                    tint = Color.Unspecified
                )

                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = date,
                    color = OiTheme.colors.textTertiary,
                    style = OiTheme.typography.bodySmallMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun OiDDayBadge(
    modifier: Modifier = Modifier,
    dayOffset: Int,
) {
    Box(
        modifier = modifier
            .padding(start = 8.dp)
            .background(
                color = getDDayBBadgeBackgroundColor(dayOffset),
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.5.dp),
            text = getDayOffsetText(dayOffset),
            color = getDDayBBadgeTextColor(dayOffset),
            style = OiTheme.typography.bodyXSmallMedium
        )
    }
}

private fun getDayOffsetText(dayOffset: Int): String {
    return when {
        dayOffset > 0 -> "D-${abs(dayOffset)} "
        dayOffset < 0 -> "D+${abs(dayOffset)}"
        else -> "D-Day"
    }
}

@Composable
private fun getDDayBBadgeBackgroundColor(dayOffset: Int): Color {
    return when {
        dayOffset > 0 -> Color(0xFFFEF2F2)
        dayOffset < 0 -> Color(0xFFF3FEF7)
        else -> Color(0xFFFFFBEB)
    }
}

@Composable
private fun getDDayBBadgeTextColor(dayOffset: Int): Color {
    return when {
        dayOffset > 0 -> Color(0xFFFF6467)
        dayOffset < 0 -> Color(0xFF00C950)
        else -> Color(0xFFFE9A00)
    }
}

@Composable
@Preview
private fun OiScheduleCardPreview() {
    OiScheduleCard(
        categoryText = "데이트",
        categoryTextColor = Color(0xFFF98247),
        dayOffset = 4,
        titleText = "남자친구와 성수동 데이트",
        partnerList = listOf("친구", "반려동물", "연인", "연인"),
        date = "25.06.06 - 25.06.08"
    )
}