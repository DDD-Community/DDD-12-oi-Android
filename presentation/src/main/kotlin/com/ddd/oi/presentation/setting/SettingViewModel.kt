package com.ddd.oi.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.SystemInfo
import com.ddd.oi.domain.model.User
import com.ddd.oi.domain.repository.SettingRepository
import com.ddd.oi.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val settingRepository: SettingRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()
    
    init {
        loadUserInfo()
        loadSystemInfo()
        observeAnnouncementBadge()
    }
    
    private fun loadUserInfo() {
        viewModelScope.launch {
            try {
                val userInfo = userRepository.getUserInfo()
                _uiState.value = _uiState.value.copy(userInfo = userInfo)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    private fun loadSystemInfo() {
        viewModelScope.launch {
            try {
                val systemInfo = userRepository.getSystemInfo()
                _uiState.value = _uiState.value.copy(systemInfo = systemInfo)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    private fun observeAnnouncementBadge() {
        viewModelScope.launch {
            combine(
                settingRepository.getAnnouncementReadAt(),
                _uiState
            ) { readAt, currentState ->
                val systemInfo = currentState.systemInfo
                val hasNewAnnouncement = systemInfo?.let { 
                    it.updatedAt > readAt 
                } ?: false
                
                currentState.copy(hasNewAnnouncement = hasNewAnnouncement)
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }
    
    fun markAnnouncementAsRead() {
        viewModelScope.launch {
            val currentTime = System.currentTimeMillis()
            settingRepository.setAnnouncementReadAt(currentTime)
        }
    }
}

data class SettingUiState(
    val userInfo: User? = null,
    val systemInfo: SystemInfo? = null,
    val hasNewAnnouncement: Boolean = false,
    val isLoading: Boolean = false
)