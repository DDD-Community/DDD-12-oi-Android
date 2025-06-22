package com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet.contract

import androidx.compose.runtime.Immutable
import com.ddd.oi.domain.model.schedule.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

@Immutable
data class DateRangeBottomSheetState(
    val isLoading: Boolean = false,
    val displayedMonth: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).let { 
        LocalDate(it.year, it.month, 1) 
    },
    val selectedStartDate: LocalDate? = null,
    val selectedEndDate: LocalDate? = null,
    val schedules: ImmutableMap<LocalDate, ImmutableList<Category>> = persistentMapOf()
)

sealed interface DateRangeBottomSheetSideEffect {
    data class Toast(val message: String) : DateRangeBottomSheetSideEffect
    data class DateRangeSelected(val startDate: LocalDate, val endDate: LocalDate) : DateRangeBottomSheetSideEffect
}