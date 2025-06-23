package com.ddd.oi.presentation.upsertschedule

import androidx.lifecycle.ViewModel
import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Party
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.model.schedule.Transportation
import com.ddd.oi.domain.usecase.schedule.CreateScheduleUseCase
import com.ddd.oi.presentation.upsertschedule.contract.UpsertScheduleSideEffect
import com.ddd.oi.presentation.upsertschedule.contract.UpsertScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class UpsertScheduleViewModel @Inject constructor(
    private val createScheduleUseCase: CreateScheduleUseCase
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

    fun upsertSchedule() = intent {
        state.category?.let {
            val zone = TimeZone.currentSystemDefault()
            val schedule = Schedule(
                id = 0,
                title = state.title,
                category = it,
                startedAt = Instant.fromEpochMilliseconds(state.startDate).toLocalDateTime(zone).date,
                endedAt = Instant.fromEpochMilliseconds(state.endDate).toLocalDateTime(zone).date,
                transportation = state.transportation!!,
                partySet = state.party,
                placeList = emptyList()
            )
            createScheduleUseCase(schedule)
                .onSuccess {  postSideEffect(UpsertScheduleSideEffect.PopBackStack) }
                .onFailure { postSideEffect(UpsertScheduleSideEffect.Toast("일정 생성 실패")) }
        }
    }
}