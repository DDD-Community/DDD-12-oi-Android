package com.ddd.oi.presentation.schedule.model

import com.ddd.oi.domain.model.Category

enum class UiCategory(
    val domainCategory: Category,
    val displayOrder: Int,
) {
    DAILY(Category.Daily, 1,),
    DATE(Category.Date, 2, ),
    TRAVEL(Category.Travel, 3),
    BUSINESS(Category.Business, 4),
    ETC(Category.Etc, 5);

    companion object {
        fun from(category: Category): UiCategory {
            return entries.first { it.domainCategory == category }
        }

        fun sortedCategories(): List<UiCategory> {
            return entries.sortedBy { it.displayOrder }
        }
    }
}