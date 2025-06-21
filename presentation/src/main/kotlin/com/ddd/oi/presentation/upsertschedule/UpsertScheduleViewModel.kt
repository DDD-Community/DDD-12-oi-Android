package com.ddd.oi.presentation.upsertschedule

import androidx.lifecycle.ViewModel
import com.ddd.oi.domain.model.Category
import com.ddd.oi.domain.model.Party
import com.ddd.oi.domain.model.Schedule
import com.ddd.oi.domain.model.Transportation
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