package com.ddd.oi.presentation.upsertschedule

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Party
import com.ddd.oi.domain.model.schedule.Transportation
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.common.OiChipIcon
import com.ddd.oi.presentation.core.designsystem.component.common.OiChoiceChip
import com.ddd.oi.presentation.core.designsystem.component.common.OiDateField
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.component.common.OiOvalChip
import com.ddd.oi.presentation.core.designsystem.component.dialog.OiPastDateDialog
import com.ddd.oi.presentation.core.designsystem.component.common.OiTextField
import com.ddd.oi.presentation.core.designsystem.component.dialog.OiAlreadyScheduleDialog
import com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet.OiDateRangeBottomSheet
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.navigation.UpsertMode
import com.ddd.oi.presentation.schedule.model.ScheduleNavData
import com.ddd.oi.presentation.upsertschedule.contract.UpsertScheduleSideEffect
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertScheduleScreen(
    modifier: Modifier = Modifier,
    scheduleNavData: ScheduleNavData? = null,
    navigatePopBack: (scheduleCreated: Boolean) -> Unit = {},
    viewModel: UpsertScheduleViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        scheduleNavData?.let(viewModel::setSchedule)
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            UpsertScheduleSideEffect.PopBackStack -> navigatePopBack(true)
            is UpsertScheduleSideEffect.Toast -> {}
        }
    }
    val uiState = viewModel.collectAsState().value
    val title = uiState.title
    val category = uiState.category
    val startDate = uiState.startDate
    val endDate = uiState.endDate
    val transportation = uiState.transportation
    val party = uiState.party
    val isButtonEnabled = uiState.isButtonEnable

    var showDateRangeBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isPastModalVisible by remember { mutableStateOf(false) }
    var isAlreadyScheduledModalVisible by remember { mutableStateOf(false) }
    var hasInRangeSchedule by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
            .background(white)
            .fillMaxSize(),
        containerColor = OiTheme.colors.backgroundContents,
        topBar = {
            OiHeader(
                onLeftClick = { navigatePopBack(false) },
                titleStringRes = when (viewModel.upsertMode) {
                    UpsertMode.CREATE -> R.string.create_schedule
                    UpsertMode.EDIT -> R.string.edit_schedule
                    UpsertMode.COPY -> R.string.copy_schedule
                },
                leftButtonDrawableRes = R.drawable.ic_arrow_left
            )
        },
        bottomBar = {
            UpsertScreenBottom(
                onButtonClick = {
                    if (uiState.isPastDate) {
                        isPastModalVisible = true
                        return@UpsertScreenBottom
                    }
                    if (hasInRangeSchedule) {
                        isAlreadyScheduledModalVisible = true
                        return@UpsertScreenBottom
                    }
                    viewModel.upsertSchedule()
                },
                isButtonEnabled = isButtonEnabled
            )
        }
    ) { defaultPadding ->
        UpsertScreenContent(
            modifier = Modifier.padding(defaultPadding),
            title = title,
            onTitleChange = viewModel::setTitle,
            category = category,
            onCategoryClick = viewModel::setCategory,
            startDate = startDate,
            endDate = endDate,
            onDateFieldClick = { showDateRangeBottomSheet = true },
            transportation = transportation,
            onTransportationClick = viewModel::setTransportation,
            partySet = party,
            onPartyClick = viewModel::setParty
        )
    }

    if (showDateRangeBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showDateRangeBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = white
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.75f)
                    .background(white)
            ) {
                OiDateRangeBottomSheet { start, end, hasSchedules ->
                    showDateRangeBottomSheet = false
                    viewModel.setDate(start, end)
                    hasInRangeSchedule = hasSchedules
                }
            }
        }
    }

    if (isPastModalVisible) {
        OiPastDateDialog(
            onDismiss = { isPastModalVisible = false },
            onConfirm = { viewModel.upsertSchedule() }
        )
    }

    if (isAlreadyScheduledModalVisible) {
        OiAlreadyScheduleDialog(
            onDismiss = { isAlreadyScheduledModalVisible = false },
            onConfirm = { viewModel.upsertSchedule() }
        )
    }
}

@Composable
private fun UpsertScreenContent(
    modifier: Modifier,
    title: String,
    onTitleChange: (String) -> Unit,
    category: Category?,
    onCategoryClick: (Category) -> Unit,
    startDate: Long,
    endDate: Long,
    onDateFieldClick: () -> Unit,
    transportation: Transportation?,
    onTransportationClick: (Transportation) -> Unit,
    partySet: Set<Party>,
    onPartyClick: (Party) -> Unit,
) {
    val scrollState = rememberScrollState()
    val interactions =
        scrollState.interactionSource.interactions.collectAsState(null)
    val focusManager = LocalFocusManager.current
    if (interactions.value is DragInteraction.Start) {
        focusManager.clearFocus()
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        UpsertScheduleContentItem(
            modifier = Modifier.fillMaxWidth(),
            titleResId = R.string.schedule_name
        ) { modifier ->
            OiTextField(
                modifier = modifier,
                text = title,
                hint = stringResource(R.string.schedule_name_hint),
                onTextChanged = onTitleChange
            )
        }

        UpsertScheduleContentItem(
            modifier = Modifier.fillMaxWidth(),
            titleResId = R.string.schedule_category
        ) { modifier ->

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Category.entries.forEach {
                    OiOvalChip(
                        tag = it.name,
                        isSelected = it == category,
                        oiChipIcon = it.toOiChipIcon(),
                        textStringRes = it.toStringResource(),
                        onItemClick = { tag ->
                            onCategoryClick(Category.valueOf(tag))
                        }
                    )
                }
            }
        }

        UpsertScheduleContentItem(
            modifier = Modifier.fillMaxWidth(),
            titleResId = R.string.schedule_period
        ) { modifier ->
            OiDateField(
                modifier = modifier,
                onClickDateField = onDateFieldClick,
                startDate = startDate,
                endDate = endDate,
                hint = stringResource(R.string.schedule_period_hint),
            )
        }

        UpsertScheduleContentItem(
            modifier = Modifier.fillMaxWidth(),
            titleResId = R.string.transportation
        ) { modifier ->
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Transportation.entries.forEach {
                    OiChoiceChip(
                        tag = it.name,
                        isSelected = it == transportation,
                        textStringRes = it.toStringResource(),
                        onItemClick = { tag ->
                            onTransportationClick(Transportation.valueOf(tag))
                        }
                    )
                }
            }
        }

        UpsertScheduleContentItem(
            modifier = Modifier.fillMaxWidth(),
            titleResId = R.string.party,
            tagText = stringResource(R.string.duplicate_available)
        ) { modifier ->
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Party.entries.take(4).forEach {
                        OiChoiceChip(
                            tag = it.name,
                            isSelected = partySet.contains(it),
                            textStringRes = it.toStringResource(),
                            onItemClick = { tag ->
                                onPartyClick(Party.valueOf(tag))
                            }
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Party.entries.drop(4).forEach {
                        OiChoiceChip(
                            tag = it.name,
                            isSelected = partySet.contains(it),
                            textStringRes = it.toStringResource(),
                            onItemClick = { tag ->
                                onPartyClick(Party.valueOf(tag))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UpsertScheduleContentItem(
    modifier: Modifier = Modifier,
    @StringRes titleResId: Int,
    tagText: String? = null,
    content: @Composable (Modifier) -> Unit,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(top = 32.dp),
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = stringResource(titleResId),
                style = OiTheme.typography.bodyMediumSemibold,
                color = OiTheme.colors.textPrimary,
            )

            tagText?.let {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .background(
                            color = OiTheme.colors.backgroundUnselected,
                            shape = RoundedCornerShape(4.dp)
                        )
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                        text = it,
                        color = OiTheme.colors.textTertiary,
                        style = OiTheme.typography.bodyXSmallMedium
                    )
                }
            }
        }

        content(Modifier.padding(top = 12.dp))
    }
}

@StringRes
private fun Transportation.toStringResource(): Int {
    return when (this) {
        Transportation.Car -> R.string.transportation_car
        Transportation.Public -> R.string.transportation_public
        Transportation.Bicycle -> R.string.transportation_bicycle
        Transportation.Walk -> R.string.transportation_walk
    }
}

@StringRes
private fun Party.toStringResource(): Int {
    return when (this) {
        Party.Alone -> R.string.party_alone
        Party.Friend -> R.string.party_friend
        Party.OtherHalf -> R.string.party_other_half
        Party.Parent -> R.string.party_parent
        Party.Sibling -> R.string.party_sibling
        Party.Children -> R.string.party_children
        Party.Pet -> R.string.party_pet
        Party.Etc -> R.string.party_etc
    }
}

private fun Category.toOiChipIcon(): OiChipIcon {
    return when (this) {
        Category.Travel -> OiChipIcon.Travel
        Category.Date -> OiChipIcon.Date
        Category.Daily -> OiChipIcon.Daily
        Category.Business -> OiChipIcon.Business
        Category.Etc -> OiChipIcon.Etc
    }
}

@StringRes
private fun Category.toStringResource(): Int {
    return when (this) {
        Category.Travel -> R.string.travel
        Category.Date -> R.string.date
        Category.Daily -> R.string.daily
        Category.Business -> R.string.business
        Category.Etc -> R.string.etc
    }
}

@Composable
private fun UpsertScreenBottom(
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean,
    onButtonClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OiButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = (12 + 52).dp, bottom = 8.dp),
            onClick = onButtonClick,
            style = OiButtonStyle.Large48Oval,
            textStringRes = R.string.next,
            enabled = isButtonEnabled
        )
    }
}


@Preview
@Composable
fun UpsertScheduleScreenPreview() {
    UpsertScheduleScreen()
}