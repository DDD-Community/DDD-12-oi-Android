package com.ddd.oi.presentation.searchplace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.core.designsystem.component.common.OiTextField

@Composable
fun SearchPlaceScreen(
    scheduleId: Long,
    viewModel: SearchPlaceViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsState()
    val searchQueryState by viewModel.searchQuery.collectAsState()
    Column {
        OiTextField(
            modifier = Modifier.fillMaxWidth(),
            text = query,
            onTextChanged = {
                viewModel.search(it)
            }
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(searchQueryState) {
                Text(it.toString())
            }
        }
    }
}