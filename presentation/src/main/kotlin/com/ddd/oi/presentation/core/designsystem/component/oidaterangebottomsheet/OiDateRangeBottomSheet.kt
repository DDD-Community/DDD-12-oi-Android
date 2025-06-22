package com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.core.designsystem.component.oidaterangepicker.OiDateRangePicker
import com.ddd.oi.presentation.core.designsystem.component.sechedule.MonthSelector
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import org.orbitmvi.orbit.compose.collectAsState


@Composable
fun OiDateRangeBottomSheet(
    viewModel: DateRangeBottomSheetViewModel = hiltViewModel()
) {
    var calendarMode by remember { mutableStateOf<CalendarMode>(CalendarMode.Range) }
    val uiState by viewModel.collectAsState()

    Column() {
        MonthSelector(
            selectedDate = uiState.displayedMonth,
            enabled = !uiState.isLoading,
            onDateChange = viewModel::updateDisplayedMonth,
            onDropdownClick = { calendarMode = CalendarMode.MonthGrid }
        )
        Crossfade(calendarMode) { mode ->
            when (mode) {
                CalendarMode.Range -> {
                    OiDateRangePicker(
                        displayedMonth = uiState.displayedMonth,
                        selectedStartDate = uiState.selectedStartDate,
                        selectedEndDate = uiState.selectedEndDate,
                        onDatesSelectionChange = viewModel::updateSelectedDate
                    )
                }
                CalendarMode.MonthGrid -> {
                    OiMonthGrid(
                        displayedMonth = uiState.displayedMonth,
                        onMonthSelected = {
                            viewModel.updateDisplayedMonth(it)
                            calendarMode = CalendarMode.Range
                        }
                    )
                }
            }
        }
    }
}

@Immutable
private enum class CalendarMode {
    Range, MonthGrid
}

@Composable
@Preview(showBackground = true)
private fun OiDateRangeBottomSheetPreview() {
    OiTheme {
        OiDateRangeBottomSheet()
    }
}