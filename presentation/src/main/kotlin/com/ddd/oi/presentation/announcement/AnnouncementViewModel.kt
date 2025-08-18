package com.ddd.oi.presentation.announcement

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.Announcement
import com.ddd.oi.domain.usecase.announcement.GetAnnouncementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val getAnnouncementsUseCase: GetAnnouncementsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AnnouncementUiState())
    val uiState: StateFlow<AnnouncementUiState> = _uiState.asStateFlow()
    
    private val _announcements = MutableStateFlow<List<Announcement>>(emptyList())
    val announcements: StateFlow<List<Announcement>> = _announcements.asStateFlow()
    
    private var currentPage = 0
    private val pageSize = 100
    private var hasMorePages = true
    
    init {
        loadAnnouncements()
    }
    
    private fun loadAnnouncements() {
        if (_uiState.value.isLoading || !hasMorePages) return
        
        Log.d("AnnouncementViewModel", "loadAnnouncements called - page: $currentPage, hasMorePages: $hasMorePages")
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getAnnouncementsUseCase(currentPage, pageSize)
                .onSuccess { announcementPage ->
                    Log.d("AnnouncementViewModel", "ViewModel success: ${announcementPage.announcements.size} announcements loaded")
                    Log.d("AnnouncementViewModel", "Total elements: ${announcementPage.totalElements}, hasNext: ${announcementPage.hasNext}")
                    
                    val newAnnouncements = if (currentPage == 0) {
                        announcementPage.announcements
                    } else {
                        _announcements.value + announcementPage.announcements
                    }
                    
                    _announcements.value = newAnnouncements
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        totalCount = announcementPage.totalElements,
                        hasMorePages = announcementPage.hasNext
                    )
                    
                    hasMorePages = announcementPage.hasNext
                    currentPage++
                    
                    Log.d("AnnouncementViewModel", "Updated state - total announcements: ${newAnnouncements.size}, nextPage: $currentPage")
                }
                .onFailure { exception ->
                    Log.e("AnnouncementViewModel", "Failed to load announcements", exception)
                    Log.e("AnnouncementViewModel", "Exception type: ${exception::class.java.simpleName}")
                    Log.e("AnnouncementViewModel", "Exception message: ${exception.message}")
                    Log.e("AnnouncementViewModel", "Page: $currentPage, Size: $pageSize")
                    
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message,
                        totalCount = 0,
                        hasMorePages = false
                    )
                    
                    hasMorePages = false
                }
        }
    }

    fun refresh() {
        currentPage = 0
        hasMorePages = true
        _announcements.value = emptyList()
        loadAnnouncements()
    }
    
    fun loadMoreAnnouncements() {
        loadAnnouncements()
    }
}

data class AnnouncementUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val totalCount: Int = 0,
    val hasMorePages: Boolean = true
)