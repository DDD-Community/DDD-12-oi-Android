package com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState


@Composable
fun OiDateRangeBottomSheet(
    viewModel: DateRangeBottomSheetViewModel = hiltViewModel()
) {
    var calendarMode by remember { mutableStateOf<CalendarMode>(CalendarMode.Range) }
    val uiState by viewModel.collectAsState()
}

@Immutable
private enum class CalendarMode {
    Range, MonthGrid
}