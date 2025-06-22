package com.ddd.oi.presentation.schedule.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.common.OiIconButton
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.getCurrentLocale
import com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet.MonthCell
import com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet.MonthItem
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import java.text.DateFormatSymbols

@Composable
fun OiMonthGridBottomSheet(
    modifier: Modifier = Modifier,
    selectedYear: Int,
    selectedMonth: Int,
    onMonthSelected: (LocalDate) -> Unit
) {
    var year by remember { mutableIntStateOf(selectedYear) }
    var month by remember { mutableIntStateOf(selectedMonth) }
    val locale = getCurrentLocale()
    val monthNames = remember(locale) {
        DateFormatSymbols(locale).months.take(12)
    }

    val months = remember {
        Month.entries.mapIndexed { index, month ->
            MonthItem(
                month = month,
                displayName = monthNames[index],
                monthNumber = index + 1
            )
        }
    }
    Column(
        modifier = modifier
            .padding(top = Dimens.paddingLarge, bottom = Dimens.paddingSmall)
    ) {
        Row(modifier.padding(horizontal = Dimens.paddingMedium)) {
            OiIconButton(
                enabled = true,
                onClick = { year-- }
            ) {
                Icon(painterResource(R.drawable.ic_calendar_left), null)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier,
                text = "${year}년 ",
                style = OiTheme.typography.headlineSmallBold
            )

            Spacer(modifier = Modifier.weight(1f))
            OiIconButton(
                enabled = true,
                onClick = { year++ }
            ) {
                Icon(painterResource(R.drawable.ic_calendar_right), null)
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(Dimens.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMediumSmall)
        ) {
            items(months) { monthItem ->
                MonthCell(
                    monthText = monthItem.displayName,
                    isSelected = monthItem.monthNumber == month,
                    onClick = { month = monthItem.monthNumber }
                )
            }
        }
        OiButton(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium),
            textStringRes = R.string.confirm,
            style = OiButtonStyle.Large48Oval,
            enabled = true,
            onClick = {
                val date = LocalDate(year, month, 1)
                onMonthSelected(date)
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun OiMonthGridBottomSheetPreview() {
    OiTheme {
        OiMonthGridBottomSheet(
            selectedYear = 2025,
            selectedMonth = 6,
            onMonthSelected = { }
        )
    }
}
