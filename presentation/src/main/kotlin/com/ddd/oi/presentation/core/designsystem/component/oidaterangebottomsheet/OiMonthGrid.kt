package com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiIconButton
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.getCurrentLocale
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.designsystem.util.MonthGridDimens
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import java.text.DateFormatSymbols

@Composable
fun OiMonthGrid(
    modifier: Modifier = Modifier,
    selectedYear: Int,
    selectedMonth: Int,
    onMonthSelected: (LocalDate) -> Unit
) {
    var year by remember { mutableIntStateOf(selectedYear) }
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
                val isSelected = year == selectedYear && monthItem.monthNumber == selectedMonth
                MonthCell(
                    monthText = monthItem.displayName,
                    isSelected = isSelected,
                    onClick = {
                        val localDate = LocalDate(year, monthItem.monthNumber, 1)
                        onMonthSelected(localDate)
                    }
                )
            }
        }
    }
}

@Composable
internal fun MonthCell(
    monthText: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(Dimens.paddingMedium),
        color = if (isSelected) OiTheme.colors.backgroundSelected else Color.Transparent
    ) {
        Text(
            text = monthText,
            style = if (isSelected) OiTheme.typography.bodyMediumSemibold else OiTheme.typography.bodyMediumRegular,
            color = if (isSelected) OiTheme.colors.textOnPrimary else OiTheme.colors.textPrimary,
            modifier = Modifier.padding(vertical = MonthGridDimens.verticalPadding),
            textAlign = TextAlign.Center
        )
    }
}

internal data class MonthItem(
    val month: Month,
    val displayName: String,
    val monthNumber: Int
)

@Preview(showBackground = true)
@Composable
private fun OiMonthGridPreview() {
    OiTheme {
        OiMonthGrid(
            selectedYear = 2025,
            selectedMonth = 6,
            onMonthSelected = { }
        )
    }
}