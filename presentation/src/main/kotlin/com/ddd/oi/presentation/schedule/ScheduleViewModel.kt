package com.ddd.oi.presentation.schedule

import androidx.lifecycle.ViewModel
import com.ddd.oi.presentation.schedule.contract.ScheduleSideEffect
import com.ddd.oi.presentation.schedule.contract.ScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(

): ContainerHost<ScheduleState, ScheduleSideEffect>, ViewModel() {

    override val container = container<ScheduleState, ScheduleSideEffect>(ScheduleState())
}