package com.ddd.oi.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.Content
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.usecase.content.GetContentsUseCase
import com.ddd.oi.domain.usecase.schedule.GetWeeklySchedulesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
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
                    contents = contents.toPersistentList(),
                    filteredContents = contents.toPersistentList(),
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    contents = persistentListOf(),
                    filteredContents = persistentListOf(),
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
                            weeklySchedules = weeklySchedules.mapValues { it.value.toPersistentList() }.toPersistentMap()
                        )
                        updateSelectedDateSchedules()
                    }
                    .onFailure {
                        _uiState.value = _uiState.value.copy(
                            weeklySchedules = persistentMapOf()
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    weeklySchedules = persistentMapOf()
                )
            }
        }
    }

    fun selectDate(date: LocalDate) {
        val selectedSchedules = _uiState.value.weeklySchedules[date] ?: persistentListOf()
        _uiState.value = _uiState.value.copy(
            selectedDate = date,
            selectedDateSchedules = selectedSchedules
        )
    }

    private fun updateSelectedDateSchedules() {
        val selectedSchedules = _uiState.value.weeklySchedules[_uiState.value.selectedDate] ?: persistentListOf()
        _uiState.value = _uiState.value.copy(
            selectedDateSchedules = selectedSchedules
        )
    }

    fun selectCategory(category: RecommendedCategory) {
        val filteredContents = if (category == RecommendedCategory.ALL) {
            _uiState.value.contents
        } else {
            _uiState.value.contents.filter { content ->
                content.contentsTag.contains(category.name, ignoreCase = true)
            }
        }
        
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            filteredContents = filteredContents.toPersistentList()
        )
    }

    fun refreshContents() {
        getContents()
        getWeeklySchedules()
    }
}

data class HomeUiState(
    val contents: PersistentList<Content> = persistentListOf(),
    val filteredContents: PersistentList<Content> = persistentListOf(),
    val selectedCategory: RecommendedCategory = RecommendedCategory.ALL,
    val weeklySchedules: PersistentMap<LocalDate, PersistentList<Schedule>> = persistentMapOf(),
    val selectedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val selectedDateSchedules: PersistentList<Schedule> = persistentListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)