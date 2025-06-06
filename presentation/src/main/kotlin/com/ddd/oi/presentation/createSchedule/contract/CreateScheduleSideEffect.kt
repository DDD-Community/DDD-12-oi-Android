package com.ddd.oi.presentation.createSchedule.contract

sealed interface CreateScheduleSideEffect {
    data class Toast(val message: String) : CreateScheduleSideEffect
}