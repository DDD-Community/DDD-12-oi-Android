package com.ddd.oi.presentation.upsertschedule

import androidx.lifecycle.ViewModel
import com.ddd.oi.presentation.upsertschedule.contract.UpsertScheduleSideEffect
import com.ddd.oi.presentation.upsertschedule.contract.UpsertScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class UpsertScheduleViewModel @Inject constructor(

) : ContainerHost<UpsertScheduleState, UpsertScheduleSideEffect>, ViewModel() {

    override val container = container<UpsertScheduleState, UpsertScheduleSideEffect>(
        UpsertScheduleState()
    )
}