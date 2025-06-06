package com.ddd.oi.presentation.schedule.contract

sealed interface ScheduleSideEffect {
    data class Toast(val message: String): ScheduleSideEffect
}