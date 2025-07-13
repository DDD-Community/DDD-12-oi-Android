package com.ddd.oi.presentation.searchplace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.domain.model.Place
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonColorType
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.component.common.OiPlaceCard
import com.ddd.oi.presentation.core.designsystem.component.common.OiSearchField
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white

@Composable
fun SearchPlaceScreen(
    scheduleId: Long,
    onBack: () -> Unit,
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setScheduleId(scheduleId)
    }

    SearchPlaceScreen(
        query = query,
        onTextChanged = { viewModel.query(it) },
        uiState = uiState,
        onPlaceClick = { viewModel.selectPlace(it) },
        onSearch = { viewModel.search(it) },
        onLeftClick = onBack,
    )
}

@Composable
private fun SearchPlaceScreen(
    modifier: Modifier = Modifier,
    query: String,
    onTextChanged: (String) -> Unit,
    uiState: SearchPlaceUiState,
    onPlaceClick: (Place) -> Unit,
    onSearch: (String) -> Unit,
    onLeftClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier.background(white),
        containerColor = white,
        topBar = {
            OiHeader(
                leftButtonDrawableRes = R.drawable.ic_arrow_left,
                titleStringRes = R.string.add_place,
                isDividerVisible = false,
                onLeftClick = onLeftClick
            )
        },
        bottomBar = {
            SearchPlaceBottom(
                modifier = Modifier,
                isButtonEnabled = uiState.selectedPlaceList.isNotEmpty(),
                selectedPlaceCount = uiState.selectedPlaceList.size
            )
        }
    ) { defaultPadding ->
        Column(
            modifier = Modifier
                .padding(defaultPadding)
        ) {
            OiSearchField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp),
                text = query,
                onTextChanged = onTextChanged,
                onSearch = onSearch
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = OiTheme.colors.borderSecondary,
            )

            if (uiState.selectedPlaceList.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(OiTheme.colors.backgroundContents)
                        .height(60.dp)
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.selectedPlaceList) {
                        Button(onClick = {}) {
                            Text(it.title)
                        }
                    }
                }
            }

            when (uiState) {
                is SearchPlaceUiState.QueryEmpty -> {
                    Column(modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterStart),
                                style = OiTheme.typography.bodyMediumSemibold,
                                color = OiTheme.colors.textPrimary,
                                text = stringResource(R.string.recent_search),
                            )

                            // todo remove all
                            Text(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                style = OiTheme.typography.bodySmallSemibold,
                                color = OiTheme.colors.textDisabled,
                                text = stringResource(R.string.delete_all)
                            )
                        }

                        FlowRow(
                            modifier = Modifier.padding(top = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiState.searchPlace.forEach {
                                OiButton(
                                    title = it,
                                    style = OiButtonStyle.Small32Oval,
                                    colorType = OiButtonColorType.Secondary,
                                    rightIconDrawableRes = R.drawable.ic_x,
                                    contentPadding = PaddingValues(
                                        horizontal = 12.dp,
                                        vertical = 0.dp
                                    )
                                )
                            }
                        }
                    }
                }

                is SearchPlaceUiState.ResultEmpty -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F)
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            verticalArrangement = Arrangement.spacedBy(18.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = OiTheme.typography.headlineSmallBold,
                                color = OiTheme.colors.textPrimary,
                                text = stringResource(R.string.no_search_result)
                            )

                            // todo replace character
                            Column(
                                modifier = Modifier
                                    .size(154.dp)
                                    .background(OiTheme.colors.backgroundDisabled)
                                    .align(Alignment.CenterHorizontally)
                            ) {

                            }
                        }
                    }
                }

                is SearchPlaceUiState.Typing -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp)
                    ) {
                        items(uiState.placeList) {
                            OiPlaceCard(
                                place = it.title,
                                category = it.category,
                                categoryColor = Color(it.categoryColor.toColorInt()),
                                address = it.roadAddress,
                                onClick = { onPlaceClick(it) },
                                isFocused = uiState.selectedPlaceList.contains(it)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchPlaceBottom(
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean,
    selectedPlaceCount: Int,
    onButtonClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OiButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 10.dp, bottom = 10.dp),
            onClick = onButtonClick,
            style = OiButtonStyle.Large48Oval,
            leftIconDrawableRes = R.drawable.ic_add_plus,
            title =
                if (selectedPlaceCount == 1) stringResource(R.string.add_selected_place)
                else stringResource(R.string.add_selected_multiple_place, selectedPlaceCount),
            enabled = isButtonEnabled
        )
    }
}

@Preview
@Composable
private fun SearchPlaceScreenPreview() {
    SearchPlaceScreen(
        query = "애슐리",
        onTextChanged = {},
        uiState = SearchPlaceUiState.QueryEmpty(emptyList(), emptyList()),
        onPlaceClick = {},
        onSearch = {},
        onLeftClick = {}
    )
}