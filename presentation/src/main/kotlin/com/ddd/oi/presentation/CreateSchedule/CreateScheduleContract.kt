package com.ddd.oi.presentation.CreateSchedule

import com.ddd.oi.presentation.core.base.SideEffect
import com.ddd.oi.presentation.core.base.UiState

data class CreateScheduleState(
    val title: String = ""
) : UiState

sealed interface CreateScheduleSideEffect : SideEffect {
    data class Toast(val message: String) : CreateScheduleSideEffect
}