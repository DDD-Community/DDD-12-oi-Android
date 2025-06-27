package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Party
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.model.schedule.Transportation
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.mapper.formatDateRange
import com.ddd.oi.presentation.core.designsystem.component.mapper.getColor
import com.ddd.oi.presentation.core.designsystem.component.mapper.getText
import com.ddd.oi.presentation.core.designsystem.component.sechedule.OiLine
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.designsystem.util.OiCardDimens
import kotlinx.datetime.LocalDate

@Composable
fun OiCard(
    modifier: Modifier = Modifier,
    schedule: Schedule,
    onClick: () -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(OiCardDimens.cardHeight)
            .padding(horizontal = Dimens.paddingMedium),
        shape = RoundedCornerShape(OiCardDimens.cornerRadius),
        colors = CardDefaults.cardColors(containerColor = white),
        elevation = CardDefaults.cardElevation(OiCardDimens.elevation),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.paddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OiLine(
                modifier = Modifier.height(OiCardDimens.cardHeight),
                color = schedule.category.getColor()
            )
            Column(
                modifier = Modifier
                    .padding(start = Dimens.paddingMedium)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = schedule.title, style = OiTheme.typography.bodyMediumSemibold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_card_calendar),
                        contentDescription = stringResource(R.string.schedule_duration),
                        tint = OiTheme.colors.iconTertiary
                    )
                    Text(
                        modifier = Modifier.padding(start = Dimens.paddingXSmall),
                        text = formatDateRange(schedule.startedAt, schedule.endedAt),
                        style = OiTheme.typography.bodySmallRegular,
                        color = OiTheme.colors.textTertiary
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .padding(horizontal = Dimens.paddingSmall)
                            .height(OiCardDimens.dividerHeight)
                            ,
                        color = OiTheme.colors.borderSecondary
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_route),
                        contentDescription = stringResource(R.string.route),
                        tint = OiTheme.colors.iconTertiary
                    )
                    Text(
                        modifier = Modifier.padding(start = Dimens.paddingXSmall),
                        text = stringResource(schedule.transportation.getText()),
                        style = OiTheme.typography.bodySmallRegular,
                        color = OiTheme.colors.textTertiary
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            OiIconButton(
                modifier = Modifier.size(OiCardDimens.moreIconSize),
                onClick = onMoreClick
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.more)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun OiCardPreview() {
    OiTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            OiCard(
                schedule = Schedule(
                    id = 0L,
                    title = "2박 3일 부산 여행",
                    category = Category.Daily,
                    startedAt = LocalDate(
                        2025,
                        6,
                        12
                    ),
                    endedAt = LocalDate(2025, 6, 19),
                    transportation = Transportation.Car,
                    partySet = setOf(Party.Friend, Party.Pet),
                    placeList = emptyList()
                )
            )
        }
    }
}