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
}

data class WithdrawUiState(
    val nickname: String = "오늘의이동",
    val selectedReason: String = "",
    val isAgreed: Boolean = false
) {
    val canWithdraw: Boolean
        get() = selectedReason.isNotEmpty() && isAgreed
}