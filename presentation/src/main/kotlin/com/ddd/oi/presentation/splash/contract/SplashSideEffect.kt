package com.ddd.oi.presentation.splash.contract

sealed interface SplashSideEffect {
    data object NavigateToLogin : SplashSideEffect
    data object NavigateToMain : SplashSideEffect
    data class ShowToast(val message: String) : SplashSideEffect
}