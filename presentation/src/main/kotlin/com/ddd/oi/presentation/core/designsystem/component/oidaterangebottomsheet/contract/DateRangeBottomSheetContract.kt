package com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet.contract

import androidx.compose.runtime.Immutable
import com.ddd.oi.domain.model.schedule.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

@Immutable
data class DateRangeBottomSheetState(
    val calendarMode: CalendarMode = CalendarMode.Range,
    val isLoading: Boolean = false,
    val displayedMonth: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).let { 
        LocalDate(it.year, it.month, 1) 
    },
    val selectedStartDate: LocalDate? = null,
    val selectedEndDate: LocalDate? = null,
    val scheduleCache: ImmutableMap<String, ImmutableMap<LocalDate, ImmutableList<Category>>> = persistentMapOf(),
    val isSnackbarDismissed: Boolean = false,
) {
    val isMonthSelectorEnabled: Boolean
        get() = !isLoading && calendarMode == CalendarMode.Range
    
    val currentMonthSchedules: ImmutableMap<LocalDate, ImmutableList<Category>>
        get() = scheduleCache[getMonthKey(displayedMonth)] ?: persistentMapOf()


    private val hasScheduleLimitExceeded: Boolean
        get() {
            val startDate = selectedStartDate ?: return false
            val endDate = selectedEndDate ?: return false
            var currentDate = startDate
            while (currentDate <= endDate) {
                val monthKey = getMonthKey(currentDate)
                val monthSchedules = scheduleCache[monthKey] ?: return false
                val categoriesCount = monthSchedules[currentDate]?.size ?: 0
                if (categoriesCount >= 3) return true
                currentDate = currentDate.plus(1, kotlinx.datetime.DateTimeUnit.DAY)
            }
            return false
        }

    val isSnackbarVisible: Boolean
        get() = !isLoading && !isSnackbarDismissed && hasScheduleLimitExceeded

    val hasSchedulesInSelectedRange: Boolean
        get() {
            val startDate = selectedStartDate ?: return false
            val endDate = selectedEndDate ?: return false
            var currentDate = startDate
            while (currentDate <= endDate) {
                val monthKey = getMonthKey(currentDate)
                val monthSchedules = scheduleCache[monthKey] ?: return false
                val categoriesCount = monthSchedules[currentDate]?.size ?: 0
                if (categoriesCount > 0) return true
                currentDate = currentDate.plus(1, kotlinx.datetime.DateTimeUnit.DAY)
            }
            return false
        }

    val isButtonEnabled: Boolean
        get() {
            val startDate = selectedStartDate ?: return false
            return !isLoading && !hasScheduleLimitExceeded
        }

    private fun getMonthKey(date: LocalDate): String = "${date.year}-${date.monthNumber}"

}

sealed interface DateRangeBottomSheetSideEffect {
    data class Toast(val message: String) : DateRangeBottomSheetSideEffect
}

@Immutable
enum class CalendarMode {
    Range, MonthGrid
}