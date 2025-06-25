package com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet.contract.CalendarMode
import com.ddd.oi.presentation.core.designsystem.component.oidaterangepicker.OiDateRangePicker
import com.ddd.oi.presentation.core.designsystem.component.sechedule.MonthSelector
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import org.orbitmvi.orbit.compose.collectAsState


@Composable
fun OiDateRangeBottomSheet(
    viewModel: DateRangeBottomSheetViewModel = hiltViewModel(),
    onUpsertScheduleClick: (Long, Long, Boolean) -> Unit
) {
    val uiState by viewModel.collectAsState()

    Box {
        Column {
            MonthSelector(
                selectedDate = uiState.displayedMonth,
                enabled = uiState.isMonthSelectorEnabled,
                onDateChange = viewModel::updateDisplayedMonth,
                onDropdownClick = { viewModel.updateCalendarMode(CalendarMode.MonthGrid) }
            )
            Crossfade(uiState.calendarMode) { mode ->
                when (mode) {
                    CalendarMode.Range -> {
                        Column {
                            OiDateRangePicker(
                                displayedMonth = uiState.displayedMonth,
                                selectedStartDate = uiState.selectedStartDate,
                                selectedEndDate = uiState.selectedEndDate,
                                schedules = uiState.currentMonthSchedules,
                                onDatesSelectionChange = viewModel::updateSelectedDate,
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth().height(Dimens.largeHeight),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AnimatedVisibility(
                                    modifier = Modifier.padding(vertical = Dimens.paddingXSmall),
                                    visible = uiState.isSnackbarVisible
                                ) {
                                    ScheduleLimitSnackbar(
                                        onCloseClick = viewModel::dismissSnackbar
                                    )
                                }
                                Icon(
                                    modifier = Modifier.padding(
                                        start = Dimens.paddingMediumXSmall,
                                        end = Dimens.paddingLargeSmall
                                    ),
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_info),
                                    contentDescription = "info",
                                    tint = OiTheme.colors.borderPrimary
                                )
                            }
                        }
                    }

                    CalendarMode.MonthGrid -> {
                        OiMonthGrid(
                            selectedYear = uiState.displayedMonth.year,
                            selectedMonth = uiState.displayedMonth.monthNumber,
                            onMonthSelected = {
                                viewModel.updateDisplayedMonth(it)
                                viewModel.updateCalendarMode(CalendarMode.Range)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            OiButton(
                modifier = Modifier.padding(horizontal = Dimens.paddingMedium, vertical = Dimens.paddingSmall),
                textStringRes = R.string.confirm,
                style = OiButtonStyle.Large48Oval,
                enabled = uiState.isButtonEnabled,
                onClick = {
                    val startDate = uiState.selectedStartDate ?: return@OiButton
                    val endDate = uiState.selectedEndDate ?: startDate
                    val startLong = startDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
                    val endLong = endDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
                    onUpsertScheduleClick(startLong, endLong, uiState.hasSchedulesInSelectedRange)
                }
            )
        }

        /**
         * todo State(Loading, Success, Error 분리) refactor
         * todo 각 상태에 따라 화면 분기 refactor
         */
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = OiTheme.colors.backgroundPrimary
                )
            }
        }
    }
}

@Composable
private fun ScheduleLimitSnackbar(
    onCloseClick: () -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(Dimens.paddingSmall),
        color = OiTheme.colors.textPrimary.copy(alpha = 0.8f)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = Dimens.paddingSmall)
                    .padding(start = Dimens.paddingMediumLarge, end = Dimens.paddingSmall),
                text = stringResource(R.string.schedule_limit_snackbar),
                style = OiTheme.typography.bodySmallRegular,
                color = OiTheme.colors.textDisabled
            )
            IconButton(onClick = onCloseClick) {
                Icon(
                    modifier = Modifier.padding(end = Dimens.paddingMediumLarge),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_clear),
                    contentDescription = "schedule_limit_3",
                    tint = OiTheme.colors.textDisabled
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun OiDateRangeBottomSheetPreview() {
    OiTheme {
        OiDateRangeBottomSheet(onUpsertScheduleClick = {_, _, _ ->})
    }
}