package com.ddd.oi.presentation.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(WithdrawUiState())
    val uiState: StateFlow<WithdrawUiState> = _uiState.asStateFlow()
    
    init {
        loadUserInfo()
    }
    
    private fun loadUserInfo() {
        viewModelScope.launch {
            try {
                val userInfo = userRepository.getUserInfo()
                _uiState.value = _uiState.value.copy(nickname = userInfo.name ?: "오늘의이동")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(nickname = "오늘의이동")
            }
        }
    }
    
    fun selectReason(reason: String) {
        _uiState.value = _uiState.value.copy(selectedReason = reason)
    }
    
    fun setAgreement(isAgreed: Boolean) {
        _uiState.value = _uiState.value.copy(isAgreed = isAgreed)
    }
    
    fun withdrawUser() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null, successMessage = null)
                userRepository.withdrawUser()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "회원탈퇴가 완료되었습니다."
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "회원탈퇴에 실패했습니다. 다시 시도해주세요."
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
}

data class WithdrawUiState(
    val nickname: String = "오늘의이동",
    val selectedReason: String = "",
    val isAgreed: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
) {
    val canWithdraw: Boolean
        get() = selectedReason.isNotEmpty() && isAgreed && !isLoading
}