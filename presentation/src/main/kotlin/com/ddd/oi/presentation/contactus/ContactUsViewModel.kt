package com.ddd.oi.presentation.contactus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.Faq
import com.ddd.oi.domain.usecase.faq.GetFaqsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val getFaqsUseCase: GetFaqsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ContactUsUiState())
    val uiState: StateFlow<ContactUsUiState> = _uiState.asStateFlow()
    
    private val _faqs = MutableStateFlow<List<Faq>>(emptyList())
    val faqs: StateFlow<List<Faq>> = _faqs.asStateFlow()
    
    private var currentPage = 0
    private val pageSize = 100
    private var hasMorePages = true
    
    init {
        loadFaqs()
    }
    
    private fun loadFaqs() {
        if (_uiState.value.isLoading || !hasMorePages) return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getFaqsUseCase(currentPage, pageSize)
                .onSuccess { faqPage ->
                    val newFaqs = if (currentPage == 0) {
                        faqPage.faqs
                    } else {
                        _faqs.value + faqPage.faqs
                    }
                    
                    _faqs.value = newFaqs
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        totalCount = faqPage.totalCount,
                        hasMorePages = faqPage.hasNext
                    )
                    
                    hasMorePages = faqPage.hasNext
                    currentPage++
                }
                .onFailure { _ ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = null,
                        totalCount = 0,
                        hasMorePages = false
                    )
                    
                    hasMorePages = false
                    currentPage++
                }
        }
    }

    fun refresh() {
        currentPage = 0
        hasMorePages = true
        _faqs.value = emptyList()
        loadFaqs()
    }
}

data class ContactUsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val totalCount: Int = 0,
    val hasMorePages: Boolean = true
)