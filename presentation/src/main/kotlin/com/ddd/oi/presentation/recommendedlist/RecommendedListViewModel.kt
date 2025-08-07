package com.ddd.oi.presentation.recommendedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.Content
import com.ddd.oi.domain.usecase.content.GetContentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendedListViewModel @Inject constructor(
    private val getContentsUseCase: GetContentsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecommendedListUiState())
    val uiState: StateFlow<RecommendedListUiState> = _uiState.asStateFlow()

    fun getContents() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                val contents = getContentsUseCase()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    contents = contents,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}

data class RecommendedListUiState(
    val isLoading: Boolean = false,
    val contents: List<Content> = emptyList(),
    val error: String? = null
)