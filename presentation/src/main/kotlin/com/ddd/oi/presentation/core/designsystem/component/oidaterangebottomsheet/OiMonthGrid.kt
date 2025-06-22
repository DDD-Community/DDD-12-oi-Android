package com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.getCurrentLocale
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.designsystem.util.MonthGridDimens
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import java.text.DateFormatSymbols
import java.util.Locale

@Composable
fun OiMonthGrid(
    modifier: Modifier = Modifier,
    displayedMonth: LocalDate,
    onMonthSelected: (LocalDate) -> Unit
) {
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

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.background(white),
        contentPadding = PaddingValues(Dimens.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.paddingMediumSmall)
    ) {
        items(months) { monthItem ->
            val monthDate = LocalDate(displayedMonth.year, monthItem.monthNumber, 1)
            MonthCell(
                monthText = monthItem.displayName,
                isSelected = monthDate == displayedMonth,
                onClick = {
                    onMonthSelected(monthDate)
                }
            )
        }
    }
}

@Composable
private fun MonthCell(
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
            modifier = Modifier.padding(
                horizontal = MonthGridDimens.horizontalPadding,
                vertical = MonthGridDimens.verticalPadding
            ),
            textAlign = TextAlign.Center
        )
    }
}

private data class MonthItem(
    val month: Month,
    val displayName: String,
    val monthNumber: Int
)

@Preview(showBackground = true)
@Composable
private fun OiMonthGridPreview() {
    OiTheme {
        OiMonthGrid(
            displayedMonth = LocalDate(2025, 6, 1),
            onMonthSelected = { }
        )
    }
}