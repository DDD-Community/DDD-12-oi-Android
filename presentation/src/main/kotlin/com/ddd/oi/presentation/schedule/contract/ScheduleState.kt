package com.ddd.oi.presentation.schedule.contract

import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.presentation.core.designsystem.component.mapper.toUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class ScheduleState(
    val selectedDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val isLoading: Boolean = false,
    val selectedCategoryFilter: CategoryFilter = CategoryFilter.All,
    val usedCategories: ImmutableList<Category> = persistentListOf(),
    val schedules: ImmutableMap<LocalDate, ImmutableList<Schedule>> = persistentMapOf()
) {
    val availableCategoryFilters: ImmutableList<CategoryFilter>
        get() {
            return (listOf(CategoryFilter.All) + usedCategories.map { CategoryFilter.Specific(it.toUi()) }
                .sortedBy { it.category.order }).toImmutableList()
        }

    val filteredSchedules: ImmutableMap<LocalDate, ImmutableList<Schedule>>
        get() = when (selectedCategoryFilter) {
            is CategoryFilter.All -> schedules
            is CategoryFilter.Specific -> schedules.mapValues { (_, list) ->
                list.filter { it.category.toUi() == selectedCategoryFilter.category }
                    .toImmutableList()
            }
                .toImmutableMap()
        }

    val calendarCategoryMap: ImmutableMap<LocalDate, ImmutableList<Category>>
        get() = filteredSchedules.mapValues { (_, list) ->
            list.map { it.category }.toImmutableList()
        }.toImmutableMap()

    val isCreateScheduleEnabled: Boolean
        get() = schedules[selectedDate].orEmpty().size < MaxSchedulesPerDay
}

private const val MaxSchedulesPerDay = 3

sealed class CategoryFilter {
    data object All : CategoryFilter()
    data class Specific(val category: CategoryUi) : CategoryFilter()
}

enum class CategoryUi(val order: Int) {
    Travel(1),
    Date(2),
    Daily(3),
    Business(4),
    Etc(5),
}