package com.ddd.oi.presentation.upsertschedule.contract

sealed interface UpsertScheduleSideEffect {
    data class Toast(val message: String) : UpsertScheduleSideEffect
}