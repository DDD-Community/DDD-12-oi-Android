package com.ddd.oi.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.usecase.auth.GetCurrentSocialTypeUseCase
import com.ddd.oi.presentation.splash.contract.SplashSideEffect
import com.ddd.oi.presentation.splash.contract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentSocialTypeUseCase: GetCurrentSocialTypeUseCase
) : ContainerHost<SplashState, SplashSideEffect>, ViewModel() {
    override val container = container<SplashState, SplashSideEffect>(SplashState())

    init {
        checkAuthenticationState()
    }

    private fun checkAuthenticationState() = intent {
        viewModelScope.launch {
            try {
                delay(SPLASH_DELAY_MS)
                val currentSocialType = getCurrentSocialTypeUseCase.invoke().first()
                reduce { state.copy(isLoading = false) }

                if (currentSocialType.isNullOrBlank()) {
                    postSideEffect(SplashSideEffect.NavigateToLogin)
                } else {
                    postSideEffect(SplashSideEffect.NavigateToMain)
                }
            } catch (e: Exception) {
                reduce { state.copy(isLoading = false) }
                postSideEffect(SplashSideEffect.ShowToast("앱 초기화 중 오류가 발생했습니다."))
                postSideEffect(SplashSideEffect.NavigateToLogin)
            }
        }
    }

    companion object {
        private const val SPLASH_DELAY_MS = 1500L
    }
}