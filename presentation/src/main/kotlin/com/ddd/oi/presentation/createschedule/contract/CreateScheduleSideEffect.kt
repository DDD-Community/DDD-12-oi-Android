package com.ddd.oi.presentation.createschedule.contract

sealed interface CreateScheduleSideEffect {
    data class Toast(val message: String) : CreateScheduleSideEffect
}