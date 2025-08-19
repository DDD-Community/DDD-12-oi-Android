package com.ddd.oi.presentation.scheduledetail.contract

sealed interface ScheduleDetailSideEffect {
    data class ErrorToast(val message: String): ScheduleDetailSideEffect
    data class RemoveSuccessToast(val message: String): ScheduleDetailSideEffect
}
