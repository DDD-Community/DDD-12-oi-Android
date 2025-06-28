package com.ddd.oi.presentation.upsertschedule

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.domain.model.schedule.Party
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.model.schedule.Transportation
import com.ddd.oi.domain.usecase.schedule.CreateScheduleUseCase
import com.ddd.oi.domain.usecase.schedule.UpdateScheduleUseCase
import com.ddd.oi.presentation.core.navigation.Route
import com.ddd.oi.presentation.core.navigation.UpsertMode
import com.ddd.oi.presentation.schedule.model.ScheduleNavData
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
    private val createScheduleUseCase: CreateScheduleUseCase,
    private val updateScheduleUseCase: UpdateScheduleUseCase,
    private val savedStateHandle: SavedStateHandle
) : ContainerHost<UpsertScheduleState, UpsertScheduleSideEffect>, ViewModel() {

    var upsertMode: UpsertMode = savedStateHandle.toRoute<Route.UpsertSchedule>().mode

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

        if (partySet.size > 5) return@intent
        
        reduce {
            state.copy(party = partySet)
        }
    }

    fun setSchedule(scheduleNavData: ScheduleNavData) = intent {
        reduce {
            state.copy(
                id = scheduleNavData.id ?: state.id,
                title = scheduleNavData.title ?: state.title,
                category = scheduleNavData.category ?: state.category,
                startDate = scheduleNavData.startedAt ?: state.startDate,
                endDate = scheduleNavData.endedAt ?: state.endDate,
                transportation = scheduleNavData.transportation ?: state.transportation,
                party = scheduleNavData.party ?: state.party,
            )
        }
    }

    fun upsertSchedule() = intent {
        state.category?.let {
            val zone = TimeZone.currentSystemDefault()
            val schedule = Schedule(
                id = state.id,
                title = state.title,
                category = it,
                startedAt = Instant.fromEpochMilliseconds(state.startDate).toLocalDateTime(zone).date,
                endedAt = Instant.fromEpochMilliseconds(state.endDate).toLocalDateTime(zone).date,
                transportation = state.transportation!!,
                partySet = state.party,
                placeList = emptyList()
            )

            when (upsertMode) {
                UpsertMode.CREATE, UpsertMode.COPY -> createOrCopySchedule(schedule)
                UpsertMode.EDIT -> updateSchedule(schedule)
            }

        }
    }

    private fun createOrCopySchedule(schedule: Schedule) = intent {
        createScheduleUseCase(schedule)
            .onSuccess {  postSideEffect(UpsertScheduleSideEffect.PopBackStack) }
            .onFailure { postSideEffect(UpsertScheduleSideEffect.Toast("일정 생성 실패")) }
    }

    private fun updateSchedule(schedule: Schedule) = intent {
        updateScheduleUseCase(schedule)
            .onSuccess { postSideEffect(UpsertScheduleSideEffect.PopBackStack) }
            .onFailure { postSideEffect(UpsertScheduleSideEffect.Toast("일정 수정 실패")) }
    }
}