package com.ddd.oi.presentation.scheduledetail.contract

sealed interface ScheduleDetailSideEffect {
    data class Toast(val message: String): ScheduleDetailSideEffect
}
