package com.ddd.oi.presentation.schedule

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiCard
import com.ddd.oi.presentation.core.designsystem.component.common.OiChipIcon
import com.ddd.oi.presentation.core.designsystem.component.dialog.OiDeleteDialog
import com.ddd.oi.presentation.core.designsystem.component.common.OiRoundRectChip
import com.ddd.oi.presentation.core.designsystem.component.dialog.ScheduleActionDialog
import com.ddd.oi.presentation.core.designsystem.component.mapper.formatToScheduleHeaderDate
import com.ddd.oi.presentation.core.designsystem.component.mapper.getCategoryName
import com.ddd.oi.presentation.core.designsystem.component.sechedule.MonthSelector
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.OiCalendar
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.getCurrentLocale
import com.ddd.oi.presentation.core.designsystem.component.sechedule.OiLine
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.designsystem.component.snackbar.SnackbarType
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.navigation.Route
import com.ddd.oi.presentation.core.navigation.UpsertMode
import com.ddd.oi.presentation.schedule.component.OiMonthGridBottomSheet
import com.ddd.oi.presentation.schedule.contract.CategoryFilter
import com.ddd.oi.presentation.schedule.contract.CategoryUi
import com.ddd.oi.presentation.schedule.contract.ScheduleState
import com.ddd.oi.presentation.schedule.model.ScheduleNavData
import com.ddd.oi.presentation.schedule.model.ScheduleNavDataFactory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDate
import org.orbitmvi.orbit.compose.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = hiltViewModel(),
    navigateToCreateSchedule: (ScheduleNavData?, Route.UpsertSchedule) -> Unit,
    onShowSnackbar: (OiSnackbarData) -> Unit = {},
    navigateToScheduleDetail: (Schedule) -> Unit,
    scheduleCreated: Boolean = false
) {
    val uiState by viewModel.collectAsState()
    var selectedSchedule by remember { mutableStateOf<Schedule?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showMonthGridBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(scheduleCreated) {
        if (scheduleCreated) {
            viewModel.refresh()
        }
    }

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
        navigateToCreateSchedule = {
            val scheduleCopy = Route.UpsertSchedule(mode = UpsertMode.COPY)
            navigateToCreateSchedule(
                ScheduleNavDataFactory.createLocalDateCreate(uiState.selectedDate),
                scheduleCopy
            )
        },
        navigateToScheduleDetail = navigateToScheduleDetail,
        onShowSnackbar = onShowSnackbar,
        onDropdownClick = { showMonthGridBottomSheet = true },
    )

    if (showBottomSheet) {
        ScheduleActionDialog(
            onDismiss = {
                showBottomSheet = false
                selectedSchedule = null
            },
            onEdit = {
                showBottomSheet = false
                selectedSchedule?.let { schedule ->
                    val scheduleNavData = ScheduleNavDataFactory.createForEdit(schedule)
                    val scheduleCopy = Route.UpsertSchedule(mode = UpsertMode.EDIT)
                    navigateToCreateSchedule(scheduleNavData, scheduleCopy)
                }
                selectedSchedule = null
            },
            onCopy = {
                showBottomSheet = false
                selectedSchedule?.let { schedule ->
                    val scheduleNavData = ScheduleNavDataFactory.createForCopy(schedule)
                    val scheduleCopy = Route.UpsertSchedule(mode = UpsertMode.COPY)
                    navigateToCreateSchedule(scheduleNavData, scheduleCopy)
                }
                selectedSchedule = null
            },
            onDelete = {
                showBottomSheet = false
                showDeleteDialog = true
            }
        )
    }

    if (showDeleteDialog && selectedSchedule != null) {
        selectedSchedule?.let { schedule ->
            OiDeleteDialog(
                onDismiss = { showDeleteDialog = false },
                onConfirm = { viewModel.deleteSchedule(schedule.id) }
            )
        }
    }

    if (showMonthGridBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showMonthGridBottomSheet = false },
            sheetState = bottomSheetState,
            dragHandle = null,
            containerColor = white
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .background(white)
            ) {
                OiMonthGridBottomSheet(
                    selectedYear = uiState.selectedDate.year,
                    selectedMonth = uiState.selectedDate.monthNumber,
                    onMonthSelected = {
                        viewModel.updateDate(it)
                        showMonthGridBottomSheet = false
                    }
                )
            }
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
    navigateToScheduleDetail: (Schedule) -> Unit,
    onShowSnackbar: (OiSnackbarData) -> Unit,
    onDropdownClick: () -> Unit
) {
    val localContextResource = LocalContext.current.resources
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
                onDateChange = updateDate,
                onDropdownClick = onDropdownClick
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
                    else onShowSnackbar(
                        OiSnackbarData(
                            message = localContextResource.getString(R.string.schedule_limit_snackbar),
                            type = SnackbarType.WARNING
                        )
                    )
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
                        onClick = { navigateToScheduleDetail(schedule) },
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
            modifier = Modifier.padding(start = 8.dp),
            text = formatToScheduleHeaderDate(
                date = selectedDate,
                locale = getCurrentLocale()
            ),
            style = OiTheme.typography.headlineSmallBold
        )
        Spacer(modifier = Modifier.weight(1f))

        OiAddButton(onClick = onCreateSchedule)
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
                val isSelected = category == selectedCategory
                when (category) {
                    CategoryFilter.All -> OiRoundRectChip(
                        modifier = Modifier,
                        isSelected = isSelected,
                        textStringRes = R.string.all,
                        onItemClick = { updateSelectedCategory(category) }
                    )

                    is CategoryFilter.Specific -> {
                        when (category.category) {
                            CategoryUi.Travel -> OiRoundRectChip(
                                modifier = Modifier,
                                isSelected = isSelected,
                                textStringRes = category.category.getCategoryName(),
                                oiChipIcon = OiChipIcon.Travel,
                                onItemClick = { updateSelectedCategory(category) }
                            )

                            CategoryUi.Date -> OiRoundRectChip(
                                modifier = Modifier,
                                isSelected = isSelected,
                                textStringRes = category.category.getCategoryName(),
                                oiChipIcon = OiChipIcon.Date,
                                onItemClick = { updateSelectedCategory(category) }
                            )

                            CategoryUi.Daily -> OiRoundRectChip(
                                modifier = Modifier,
                                isSelected = isSelected,
                                textStringRes = category.category.getCategoryName(),
                                oiChipIcon = OiChipIcon.Daily,
                                onItemClick = { updateSelectedCategory(category) }
                            )

                            CategoryUi.Business -> OiRoundRectChip(
                                modifier = Modifier,
                                isSelected = isSelected,
                                textStringRes = category.category.getCategoryName(),
                                oiChipIcon = OiChipIcon.Business,
                                onItemClick = { updateSelectedCategory(category) }
                            )

                            CategoryUi.Etc -> OiRoundRectChip(
                                modifier = Modifier,
                                isSelected = isSelected,
                                textStringRes = category.category.getCategoryName(),
                                oiChipIcon = OiChipIcon.Etc,
                                onItemClick = { updateSelectedCategory(category) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun OiAddButton(
    modifier: Modifier = Modifier,
    @StringRes title: Int = R.string.add_schedule,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val containerColor = OiTheme.colors.backgroundPrimary
    val contentColor = OiTheme.colors.textOnPrimary
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        shape = RoundedCornerShape(8.dp),
        color = containerColor,
        contentColor = contentColor,
        interactionSource = interactionSource
    ) {
        Row(
            Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(ImageVector.vectorResource(R.drawable.ic_add_plus), null)
            Text(
                text = stringResource(title),
                style = OiTheme.typography.bodySmallSemibold,
                color = OiTheme.colors.textOnPrimary
            )
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
                .background(OiTheme.colors.backgroundContents),
            navigateToCreateSchedule = { _, _ -> },
            navigateToScheduleDetail = {}
        )
    }
}

