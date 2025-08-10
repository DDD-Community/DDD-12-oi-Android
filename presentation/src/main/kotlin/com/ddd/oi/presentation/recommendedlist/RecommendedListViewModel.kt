package com.ddd.oi.presentation.recommendedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.Content
import com.ddd.oi.domain.usecase.content.GetContentsUseCase
import com.ddd.oi.presentation.home.RecommendedCategory
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
                    filteredContents = contents,
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

    fun selectCategory(category: RecommendedCategory) {
        val filteredContents = if (category == RecommendedCategory.ALL) {
            _uiState.value.contents
        } else {
            _uiState.value.contents.filter { content ->
                content.contentsTag.contains(category.name, ignoreCase = true)
            }
        }
        
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            filteredContents = filteredContents
        )
    }
    
    fun selectSortOption(sortOption: SortOption) {
        _uiState.value = _uiState.value.copy(
            selectedSortOption = sortOption
        )
        // 실제 정렬은 여기서 구현할 수 있지만, 
        // 현재는 UI 상태만 변경
    }
}

data class RecommendedListUiState(
    val isLoading: Boolean = false,
    val contents: List<Content> = emptyList(),
    val filteredContents: List<Content> = emptyList(),
    val selectedCategory: RecommendedCategory = RecommendedCategory.ALL,
    val selectedSortOption: SortOption = SortOption.POPULAR,
    val error: String? = null
)