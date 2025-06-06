package com.ddd.oi.presentation.Schedule

import com.ddd.oi.presentation.core.base.SideEffect
import com.ddd.oi.presentation.core.base.UiState

data class ScheduleState(
    val title: String = ""
) : UiState

sealed interface ScheduleSideEffect : SideEffect {
    data class Toast(val message: String): ScheduleSideEffect
}