package com.ddd.oi.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.User
import com.ddd.oi.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    
    init {
        loadUserInfo()
    }
    
    private fun loadUserInfo() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val userInfo = userRepository.getUserInfo()
                _uiState.value = _uiState.value.copy(
                    userInfo = userInfo,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun updateNickname(nickname: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null, successMessage = null)
                val updatedUser = userRepository.updateNickname(nickname)
                _uiState.value = _uiState.value.copy(
                    userInfo = updatedUser,
                    isLoading = false,
                    successMessage = "닉네임이 변경되었어요!"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "네트워크가 불안정해요. 잠시 후 다시 시도해주세요!"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }
    
    fun logout() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null, successMessage = null)
                userRepository.logout()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "로그아웃되었습니다."
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "로그아웃에 실패했습니다. 다시 시도해주세요."
                )
            }
        }
    }
}

data class ProfileUiState(
    val userInfo: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)