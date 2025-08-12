package com.ddd.oi.presentation.login.contract

sealed interface LoginSideEffect {
    data object LoginSuccess : LoginSideEffect
    data class LoginFailure(val throwable: Throwable) : LoginSideEffect
}