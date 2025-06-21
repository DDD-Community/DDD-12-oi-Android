package com.ddd.oi.presentation.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiCard
import com.ddd.oi.presentation.schedule.component.ScheduleActionBottomSheet
import com.ddd.oi.presentation.core.designsystem.component.common.OiChip
import com.ddd.oi.presentation.core.designsystem.component.common.rippleOrFallbackImplementation
import com.ddd.oi.presentation.core.designsystem.component.mapper.formatToScheduleHeaderDate
import com.ddd.oi.presentation.core.designsystem.component.mapper.getCategoryIcon
import com.ddd.oi.presentation.core.designsystem.component.mapper.getCategoryName
import com.ddd.oi.presentation.core.designsystem.component.sechedule.MonthSelector
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.OiCalendar
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.getCurrentLocale
import com.ddd.oi.presentation.core.designsystem.component.sechedule.OiLine
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.schedule.contract.CategoryFilter
import com.ddd.oi.presentation.schedule.contract.ScheduleState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDate
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = hiltViewModel(),
    navigateToCreateSchedule: () -> Unit = {},
    onShowSnackbar: (String) -> Unit = {}
) {
    val uiState by viewModel.collectAsState()
    var selectedSchedule by remember { mutableStateOf<Schedule?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    ScheduleScreen(
        modifier = modifier,
        onRefresh = viewModel::refresh,
        scheduleState = uiState,
        updateDate = viewModel::updateDate,
        updateSelectedCategory = viewModel::updateSelectedCategory,
        onMoreClick = { schedule ->
            selectedSchedule = schedule
            showBottomSheet = true
        },
        navigateToCreateSchedule = navigateToCreateSchedule,
        onShowSnackbar = onShowSnackbar
    )

    if (showBottomSheet && selectedSchedule != null) {
        selectedSchedule?.let { schedule ->
            ScheduleActionBottomSheet(
                onDismiss = {
                    showBottomSheet = false
                    selectedSchedule = null
                },
                onEdit = {
                    showBottomSheet = false
                    selectedSchedule = null
                },
                onCopy = {
                    showBottomSheet = false
                    selectedSchedule = null
                },
                onDelete = {
                    viewModel.deleteSchedule(schedule.id)
                    showBottomSheet = false
                    selectedSchedule = null
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleScreen(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    scheduleState: ScheduleState,
    updateDate: (LocalDate) -> Unit,
    updateSelectedCategory: (CategoryFilter) -> Unit,
    onMoreClick: (Schedule) -> Unit,
    navigateToCreateSchedule: () -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    val todaySchedule =
        scheduleState.filteredSchedules[scheduleState.selectedDate] ?: persistentListOf()
    PullToRefreshBox(
        modifier = modifier,
        onRefresh = onRefresh,
        isRefreshing = scheduleState.isLoading,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            MonthSelector(
                selectedDate = scheduleState.selectedDate,
                enabled = !scheduleState.isLoading,
                onDateChange = updateDate
            )
            ScheduleCategoryFilter(
                selectedCategory = scheduleState.selectedCategoryFilter,
                categories = scheduleState.availableCategoryFilters,
                updateSelectedCategory = updateSelectedCategory
            )
            OiCalendar(
                selectedLocalDate = scheduleState.selectedDate,
                onDateSelectionChange = updateDate,
                schedules = scheduleState.calendarCategoryMap
            )
            ScheduleListHeader(
                selectedDate = scheduleState.selectedDate,
                onCreateSchedule = {
                    if (scheduleState.isCreateScheduleEnabled) navigateToCreateSchedule()
                    else onShowSnackbar("")
                }
            )
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
            ) {
                todaySchedule.forEach { schedule ->
                    OiCard(
                        schedule = schedule,
                        onClick = { },
                        onMoreClick = { onMoreClick(schedule) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduleListHeader(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    onCreateSchedule: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(top = 24.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OiLine(modifier = Modifier.height(20.dp), color = OiTheme.colors.backgroundPrimary)
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = formatToScheduleHeaderDate(
                date = selectedDate,
                locale = getCurrentLocale()
            ),
            style = OiTheme.typography.headlineSmallBold
        )
        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = onCreateSchedule) {
            Text(
                text = "일정 추가",
                style = OiTheme.typography.bodySmallSemibold,
                color = OiTheme.colors.textBrand
            )
        }
    }
}

@Composable
private fun ScheduleCategoryFilter(
    modifier: Modifier = Modifier,
    selectedCategory: CategoryFilter,
    categories: ImmutableList<CategoryFilter>,
    updateSelectedCategory: (CategoryFilter) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(white)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(vertical = 12.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                val selected = category == selectedCategory
                when (category) {
                    CategoryFilter.All -> OiChip(
                        modifier = Modifier
                            .clickable(
                                onClick = { updateSelectedCategory(category) },
                                role = Role.Button,
                                interactionSource = null,
                                indication = rippleOrFallbackImplementation(
                                    bounded = false,
                                    radius = Dimens.paddingMediumSmall
                                )
                            ),
                        selected = selected,
                        textStringRes = R.string.all
                    )

                    is CategoryFilter.Specific -> OiChip(
                        modifier = Modifier.clickable(
                            onClick = { updateSelectedCategory(category) },
                            role = Role.Button,
                            interactionSource = null,
                            indication = rippleOrFallbackImplementation(
                                bounded = false,
                                radius = Dimens.paddingMediumSmall
                            )
                        ),
                        selected = selected,
                        iconDrawableRes = category.category.getCategoryIcon(),
                        textStringRes = category.category.getCategoryName()
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ScheduleScreenPreview() {
    OiTheme {
        ScheduleScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(OiTheme.colors.backgroundContents)
        )
    }
}

