package com.ddd.oi.presentation.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.usecase.GetSchedulesUseCase
import com.ddd.oi.presentation.schedule.contract.CategoryFilter
import com.ddd.oi.presentation.schedule.contract.ScheduleSideEffect
import com.ddd.oi.presentation.schedule.contract.ScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getSchedulesUseCase: GetSchedulesUseCase
) : ContainerHost<ScheduleState, ScheduleSideEffect>, ViewModel() {

    override val container = container<ScheduleState, ScheduleSideEffect>(ScheduleState())

    fun updateDate(localDate: LocalDate) = intent {
        reduce { state.copy(selectedDate = localDate) }
    }

    fun updateSelectedCategory(categoryFilter: CategoryFilter) = intent {
        reduce { state.copy(selectedCategoryFilter = categoryFilter) }
    }

    fun refresh() = intent {
        reduce { state.copy(isLoading = true) }
    }
}