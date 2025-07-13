package com.ddd.oi.presentation.scheduledetail.content

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ddd.oi.domain.model.schedule.Party
import com.ddd.oi.domain.model.schedule.Transportation
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.mapper.formatDateRange
import com.ddd.oi.presentation.core.designsystem.component.mapper.getText
import com.ddd.oi.presentation.core.designsystem.component.mapper.toStringResource
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import kotlinx.collections.immutable.PersistentSet
import kotlinx.datetime.LocalDate

@Composable
internal fun ScheduleDetailContent(
    modifier: Modifier = Modifier,
    startDate: LocalDate,
    endDate: LocalDate,
    transportation: Transportation,
    partySet: PersistentSet<Party>
) {
    val resourceContext = LocalContext.current.resources
    Box {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = formatDateRange(startDate, endDate, true),
                style = OiTheme.typography.bodyLargeSemibold,
                color = OiTheme.colors.textSecondary
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ScheduleDetailInfoChip(
                    chipIcon = R.drawable.ic_route,
                    chipTitle = stringResource(transportation.getText())
                )
                ScheduleDetailInfoChip(
                    chipIcon = R.drawable.ic_person,
                    chipTitle = partySet.joinToString(" · ") { resourceContext.getString(it.toStringResource()) }
                )
            }
        }
    }
}

@Composable
private fun ScheduleDetailInfoChip(
    modifier: Modifier = Modifier,
    chipTitle: String,
    @DrawableRes chipIcon: Int
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = OiTheme.colors.backgroundUnselected
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(chipIcon),
                contentDescription = "chipIcon",
                tint = OiTheme.colors.textTertiary
            )
            Text(
                text = chipTitle,
                style = OiTheme.typography.bodySmallSemibold,
                color = OiTheme.colors.textTertiary
            )
        }
    }
}