package com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet

import androidx.lifecycle.ViewModel
import com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet.contract.DateRangeBottomSheetSideEffect
import com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet.contract.DateRangeBottomSheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.datetime.LocalDate
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class DateRangeBottomSheetViewModel @Inject constructor(

) : ContainerHost<DateRangeBottomSheetState, DateRangeBottomSheetSideEffect>, ViewModel() {

    override val container = container<DateRangeBottomSheetState, DateRangeBottomSheetSideEffect>(
        DateRangeBottomSheetState()
    )

    fun updateSelectedDate(startDate: LocalDate?, endDate: LocalDate?) = intent {
        reduce { state.copy(selectedStartDate = startDate, selectedEndDate = endDate) }
    }

    fun updateDisplayedMonth(month: LocalDate) = intent {
        reduce { state.copy(displayedMonth = month) }
    }

}