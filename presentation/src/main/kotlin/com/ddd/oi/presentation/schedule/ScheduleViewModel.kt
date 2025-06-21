package com.ddd.oi.presentation.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ddd.oi.domain.usecase.schedule.DeleteScheduleUseCase
import com.ddd.oi.domain.usecase.schedule.GetSchedulesUseCase
import com.ddd.oi.presentation.schedule.contract.CategoryFilter
import com.ddd.oi.presentation.schedule.contract.ScheduleSideEffect
import com.ddd.oi.presentation.schedule.contract.ScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.datetime.LocalDate
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getSchedulesUseCase: GetSchedulesUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase
) : ContainerHost<ScheduleState, ScheduleSideEffect>, ViewModel() {

    override val container = container<ScheduleState, ScheduleSideEffect>(ScheduleState())

    init {
        loadSchedule()
    }


    fun updateDate(localDate: LocalDate) = intent {
        reduce { state.copy(selectedDate = localDate) }
        if (localDate.month != state.selectedDate.month) loadSchedule()
    }

    fun updateSelectedCategory(categoryFilter: CategoryFilter) = intent {
        reduce { state.copy(selectedCategoryFilter = categoryFilter) }
    }

    fun refresh() = loadSchedule()

    fun deleteSchedule(scheduleId: Long) = intent {
        reduce { state.copy(isLoading = true) }
        deleteScheduleUseCase(scheduleId)
            .onSuccess { loadSchedule() }
            .onFailure { exception ->
                Log.e("ScheduleViewModel", "deleteSchedule: $exception")
                errorHandling(exception.message ?: "에러 발생")
            }
    }

    private fun loadSchedule() = intent {
        reduce { state.copy(isLoading = true) }
        getSchedulesUseCase(state.selectedDate.year, state.selectedDate.monthNumber)
            .onSuccess { scheduleResult ->
                reduce {
                    state.copy(
                        isLoading = false,
                        schedules = scheduleResult.schedules.mapValues { it.value.toPersistentList() }
                            .toPersistentMap(),
                        usedCategories = scheduleResult.availableCategory.toPersistentList()
                    )
                }
            }
            .onFailure { exception ->
                errorHandling(exception.message ?: "에러 발생")
            }
    }

    private fun errorHandling(msg: String) = intent {
        reduce { state.copy(isLoading = false) }
        postSideEffect(ScheduleSideEffect.Toast(msg))
    }
}