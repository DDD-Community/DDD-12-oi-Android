package com.ddd.oi.presentation.schedule.contract

import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Schedule
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
            return (listOf(CategoryFilter.All) + usedCategories.map { CategoryFilter.Specific(it) }).toImmutableList()
        }

    val filteredSchedules: ImmutableMap<LocalDate, ImmutableList<Schedule>>
        get() = when (selectedCategoryFilter) {
            is CategoryFilter.All -> schedules
            is CategoryFilter.Specific -> schedules.mapValues { (_, list) ->
                list.filter { it.category == selectedCategoryFilter.category }.toImmutableList()
            }
                .toImmutableMap()
        }

    val calendarCategoryMap: ImmutableMap<LocalDate, ImmutableList<Category>>
        get() = filteredSchedules.mapValues { (_, list) ->
            list.map { it.category }.distinct().toImmutableList()
        }.toImmutableMap()

}

sealed class CategoryFilter {
    data object All : CategoryFilter()
    data class Specific(val category: Category) : CategoryFilter()
}
