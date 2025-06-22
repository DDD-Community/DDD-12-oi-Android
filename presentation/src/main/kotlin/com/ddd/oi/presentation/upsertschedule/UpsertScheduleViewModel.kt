package com.ddd.oi.presentation.upsertschedule

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Party
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.model.schedule.Transportation
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
        UpsertScheduleState.default
    )

    fun setTitle(title: String) = intent {
        reduce { state.copy(title = title) }
    }

    fun setCategory(category: Category) = intent {
        reduce { state.copy(category = category) }
    }

    fun setDate(startDate: Long, endDate: Long) = intent {
        Log.d("test", "startDate: $startDate, endDate: $endDate")
        reduce { state.copy(startDate = startDate, endDate = endDate) }
    }

    fun setTransportation(transportation: Transportation) = intent {
        reduce { state.copy(transportation = transportation) }
    }

    fun setParty(party: Party) = intent {
        val partySet = state.party.toHashSet().apply {
            if(contains(party)) remove(party)
            else add(party)
        }.toSet()
        
        reduce {
            state.copy(party = partySet)
        }
    }

    fun setSchedule(schedule: Schedule) = intent {
        reduce {
            state.copy(
                title = schedule.title,
                category = schedule.category,
                transportation = schedule.transportation,
                party = schedule.partySet
            )
        }
    }

    fun upsertSchedule() {
        // todo call upsertSchedule usecase
    }
}