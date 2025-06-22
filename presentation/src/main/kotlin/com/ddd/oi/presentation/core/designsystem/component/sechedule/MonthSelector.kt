package com.ddd.oi.presentation.core.designsystem.component.sechedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiIconButton
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.MonthSelectorDimens
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@Composable
fun MonthSelector(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    enabled: Boolean = false,
    onDateChange: (LocalDate) -> Unit = {},
    onDropdownClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(white)
            .height(MonthSelectorDimens.height)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = MonthSelectorDimens.horizontalPadding,
                vertical = MonthSelectorDimens.verticalPadding
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OiIconButton(
                enabled = enabled,
                onClick = {
                    val date = selectedDate.minus(1, DateTimeUnit.MONTH).let {
                        LocalDate(it.year, it.monthNumber, 1)
                    }
                    onDateChange(date)
                }) {
                Icon(painterResource(R.drawable.ic_calendar_left), null)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier,
                text = "${selectedDate.year}년 ${selectedDate.monthNumber}월",
                style = OiTheme.typography.headlineSmallBold
            )
            OiIconButton(
                modifier = Modifier.padding(start = MonthSelectorDimens.dropDownStartPadding),
                onClick = onDropdownClick
            ) {
                Icon(painterResource(R.drawable.ic_calendar_dropdown), null)
            }
            Spacer(modifier = Modifier.weight(1f))
            OiIconButton(
                enabled = enabled,
                onClick = {
                    val date = selectedDate.plus(1, DateTimeUnit.MONTH).let {
                        LocalDate(it.year, it.monthNumber, 1)
                    }
                    onDateChange(date)
                }) {
                Icon(painterResource(R.drawable.ic_calendar_right), null)
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun MonthSelectorPreview() {
    OiTheme {
        var selectedDate = LocalDate(2025, 6, 1)
        Column(modifier = Modifier.fillMaxSize()) {
            MonthSelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                selectedDate = selectedDate,
                onDateChange = { selectedDate = it }
            )
        }
    }
}