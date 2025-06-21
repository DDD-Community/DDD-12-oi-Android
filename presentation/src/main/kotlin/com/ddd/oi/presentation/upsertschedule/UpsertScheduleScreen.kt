package com.ddd.oi.presentation.upsertschedule

import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ddd.oi.domain.model.Category
import com.ddd.oi.domain.model.Party
import com.ddd.oi.domain.model.Schedule
import com.ddd.oi.domain.model.Transportation
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.common.OiChipIcon
import com.ddd.oi.presentation.core.designsystem.component.common.OiChoiceChip
import com.ddd.oi.presentation.core.designsystem.component.common.OiDateField
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.component.common.OiOvalChip
import com.ddd.oi.presentation.core.designsystem.component.common.OiTextField
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme

@Composable
fun UpsertScheduleScreen(
    modifier: Modifier = Modifier,
    schedule: Schedule? = null,
    navigatePopBack: () -> Unit = {},
    viewModel: UpsertScheduleViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        schedule?.let(viewModel::setSchedule)
    }

    val uiState = viewModel.container.stateFlow.collectAsStateWithLifecycle().value
    val title = uiState.title
    val category = uiState.category
    val startDate = uiState.startDate
    val endDate = uiState.endDate
    val transportation = uiState.transportation
    val party = uiState.party
    val isButtonEnabled = title.isNotEmpty() &&
            category != null &&
            startDate > 0L &&
            transportation != null &&
            party.isNotEmpty()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = OiTheme.colors.backgroundContents,
        topBar = {
            OiHeader(
                onLeftClick = navigatePopBack,
                titleStringRes = R.string.create_schedule,
                leftButtonDrawableRes = R.drawable.ic_arrow_left
            )
        },
        bottomBar = {
            UpsertScreenBottom(
                onButtonClick = {
                    viewModel.upsertSchedule()
                    navigatePopBack()
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
            transportation = transportation,
            onTransportationClick = viewModel::setTransportation,
            partySet = party,
            onPartyClick = viewModel::setParty
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
                onClickDateField = {
                    /**
                     * todo setDate
                     * todo call [UpsertScheduleViewModel.setDate] after bottomSheet
                     */
                },
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
            titleResId = R.string.party
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
fun UpsertScheduleContentItem(
    modifier: Modifier = Modifier,
    @StringRes titleResId: Int,
    content: @Composable (Modifier) -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = stringResource(titleResId),
            style = OiTheme.typography.bodyMediumSemibold,
            color = OiTheme.colors.textPrimary
        )

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
                .padding(top = 12.dp, bottom = 8.dp),
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