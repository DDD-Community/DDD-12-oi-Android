package com.ddd.oi.presentation.CreateSchedule

import androidx.lifecycle.ViewModel
import com.ddd.oi.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateScheduleViewModel @Inject constructor(

) : BaseViewModel<CreateScheduleState, CreateScheduleSideEffect>(CreateScheduleState()) {

}