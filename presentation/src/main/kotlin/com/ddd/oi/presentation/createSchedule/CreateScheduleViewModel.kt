package com.ddd.oi.presentation.createSchedule

import androidx.lifecycle.ViewModel
import com.ddd.oi.presentation.createSchedule.contract.CreateScheduleSideEffect
import com.ddd.oi.presentation.createSchedule.contract.CreateScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CreateScheduleViewModel @Inject constructor(

) : ContainerHost<CreateScheduleState, CreateScheduleSideEffect>, ViewModel() {

    override val container = container<CreateScheduleState, CreateScheduleSideEffect>(
        CreateScheduleState()
    )
}