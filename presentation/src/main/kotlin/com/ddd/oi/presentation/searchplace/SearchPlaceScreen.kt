package com.ddd.oi.presentation.searchplace

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.R
import com.ddd.oi.domain.model.Place
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
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
        onTextChanged = { viewModel.search(it) },
        uiState = uiState,
        onPlaceClick = { viewModel.selectPlace(it) }
    )
}

@Composable
private fun SearchPlaceScreen(
    modifier: Modifier = Modifier,
    query: String,
    onTextChanged: (String) -> Unit,
    uiState: SearchPlaceUiState,
    onPlaceClick: (Place) -> Unit,
) {
    Scaffold(
        modifier = modifier.background(white),
        containerColor = white,
        topBar = {
            OiHeader(
                leftButtonDrawableRes = R.drawable.ic_arrow_left,
                titleStringRes = R.string.add_place,
                isDividerVisible = false
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
                onSearch = {
                    // todo handle search
                    Log.e("SearchPlaceScreen", it)
                }
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = OiTheme.colors.borderSecondary,
            )

            if (uiState.selectedPlaceList.isNotEmpty()) {
                // todo set selected place ui
                Text(uiState.selectedPlaceList.joinToString("\n") { it.title })
            }

            when (uiState) {
                is SearchPlaceUiState.QueryEmpty -> {
                    // todo set last search ui
                    Text("Query Empty")
                }

                is SearchPlaceUiState.ResultEmpty -> {
                    // todo set result empty ui
                    Text("Result Empty")
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
                                categoryColor = Color(0xFF09B596),
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
        uiState = SearchPlaceUiState.QueryEmpty(emptyList()),
        onPlaceClick = {}
    )
}