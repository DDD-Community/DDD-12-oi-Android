package com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ddd.oi.domain.usecase.schedule.GetSchedulesUseCase
import com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet.contract.CalendarMode
import com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet.contract.DateRangeBottomSheetSideEffect
import com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet.contract.DateRangeBottomSheetState
import com.ddd.oi.presentation.upsertschedule.UpsertScheduleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.collections.immutable.toPersistentHashMap
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class DateRangeBottomSheetViewModel @Inject constructor(
    private val getSchedulesUseCase: GetSchedulesUseCase
) : ContainerHost<DateRangeBottomSheetState, DateRangeBottomSheetSideEffect>, ViewModel() {

    override val container = container<DateRangeBottomSheetState, DateRangeBottomSheetSideEffect>(
        DateRangeBottomSheetState()
    )

    init {
        loadMonthSchedules(container.stateFlow.value.displayedMonth)
    }

    fun updateCalendarMode(mode: CalendarMode) = intent {
        reduce { state.copy(calendarMode = mode) }
    }

    fun updateSelectedDate(startDate: LocalDate?, endDate: LocalDate?) = intent {
        reduce { 
            state.copy(
                selectedStartDate = startDate, 
                selectedEndDate = endDate,
                isSnackbarDismissed = false // 새로운 범위 선택 시 스낵바 다시 보이게
            ) 
        }
    }

    fun dismissSnackbar() = intent {
        reduce { state.copy(isSnackbarDismissed = true) }
    }

    fun updateDisplayedMonth(month: LocalDate) = intent {
        val previousMonthNumber = state.displayedMonth.monthNumber
        reduce { state.copy(displayedMonth = month) }
        if (month.monthNumber != previousMonthNumber) loadMonthSchedules(month)
    }

    private fun loadMonthSchedules(month: LocalDate) = intent {
        val monthKey = getMonthKey(month)
        
        if (state.scheduleCache.containsKey(monthKey)) {
            return@intent
        }
        reduce { state.copy(isLoading = true) }
        getSchedulesUseCase(month.year, month.monthNumber)
            .onSuccess { scheduleResult ->
                val monthSchedules = scheduleResult.schedules.mapValues { entry ->
                    entry.value.map { it.category }.toImmutableList()
                }.toImmutableMap()
                Log.d("loadMonthSchedules", monthSchedules.toString())
                reduce {
                    val mutableMap = state.scheduleCache.toMutableMap()
                    mutableMap[monthKey] = monthSchedules
                    state.copy(
                        scheduleCache = mutableMap.toImmutableMap(),
                        isLoading = false
                    )
                }
            }
            .onFailure {
                /**
                 * todo State(Loading, Success, Error 분리) refactor
                 * todo 각 상태에 따라 화면 분기 refactor
                 */
                Log.e("loadMonthSchedules", it.message.toString())
                reduce { state.copy(isLoading = false) }
            }
    }

    private fun getMonthKey(date: LocalDate): String = "${date.year}-${date.monthNumber}"
}