package com.ddd.oi.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.Content
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.usecase.content.GetContentsUseCase
import com.ddd.oi.domain.usecase.schedule.GetWeeklySchedulesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getContentsUseCase: GetContentsUseCase,
    private val getWeeklySchedulesUseCase: GetWeeklySchedulesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getContents()
        getWeeklySchedules()
    }

    fun getContents(userId: Long = 1L) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val contents = getContentsUseCase(userId)
                _uiState.value = _uiState.value.copy(
                    contents = contents,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    contents = emptyList(),
                    isLoading = false,
                    error = "Failed to load contents"
                )
            }
        }
    }

    fun getWeeklySchedules() {
        viewModelScope.launch {
            try {
                val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                getWeeklySchedulesUseCase(today)
                    .onSuccess { weeklySchedules ->
                        _uiState.value = _uiState.value.copy(
                            weeklySchedules = weeklySchedules
                        )
                        updateSelectedDateSchedules()
                    }
                    .onFailure {
                        _uiState.value = _uiState.value.copy(
                            weeklySchedules = emptyMap()
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    weeklySchedules = emptyMap()
                )
            }
        }
    }

    fun selectDate(date: LocalDate) {
        val selectedSchedules = _uiState.value.weeklySchedules[date] ?: emptyList()
        _uiState.value = _uiState.value.copy(
            selectedDate = date,
            selectedDateSchedules = selectedSchedules
        )
    }

    private fun updateSelectedDateSchedules() {
        val selectedSchedules = _uiState.value.weeklySchedules[_uiState.value.selectedDate] ?: emptyList()
        _uiState.value = _uiState.value.copy(
            selectedDateSchedules = selectedSchedules
        )
    }

    fun refreshContents() {
        getContents()
        getWeeklySchedules()
    }
}

data class HomeUiState(
    val contents: List<Content> = emptyList(),
    val weeklySchedules: Map<LocalDate, List<Schedule>> = emptyMap(),
    val selectedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val selectedDateSchedules: List<Schedule> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)