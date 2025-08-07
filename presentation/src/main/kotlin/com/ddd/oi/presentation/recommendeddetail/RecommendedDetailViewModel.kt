package com.ddd.oi.presentation.recommendeddetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.Content
import com.ddd.oi.domain.usecase.content.GetContentByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendedDetailViewModel @Inject constructor(
    private val getContentByIdUseCase: GetContentByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecommendedDetailUiState())
    val uiState: StateFlow<RecommendedDetailUiState> = _uiState.asStateFlow()

    fun getContentById(contentId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            getContentByIdUseCase(contentId)
                .onSuccess { content ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        content = content,
                        error = null
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = throwable.message
                    )
                }
        }
    }
}

data class RecommendedDetailUiState(
    val isLoading: Boolean = false,
    val content: Content = Content(id = -1, title = ""),
    val error: String? = null
)