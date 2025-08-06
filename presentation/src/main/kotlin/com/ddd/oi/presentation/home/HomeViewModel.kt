package com.ddd.oi.presentation.home

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
class HomeViewModel @Inject constructor(
    private val getContentsUseCase: GetContentsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getContents()
    }

    fun getContents(userId: Long = 1L) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val contents = getContentsUseCase(userId)
                _uiState.value = _uiState.value.copy(
                    contents = contents,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    contents = emptyList(),
                    isLoading = false,
                    error = "Failed to load contents"
                )
            }
        }
    }

    fun refreshContents() {
        getContents()
    }
}

data class HomeUiState(
    val contents: List<Content> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)