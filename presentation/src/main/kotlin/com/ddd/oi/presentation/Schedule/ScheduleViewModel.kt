package com.ddd.oi.presentation.Schedule

import androidx.lifecycle.ViewModel
import com.ddd.oi.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(

): BaseViewModel<ScheduleState, ScheduleSideEffect>(ScheduleState()) {
}