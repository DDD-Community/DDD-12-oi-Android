package com.ddd.oi.presentation.scheduledetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.usecase.scheduledetail.GetScheduleDetailsUseCase
import com.ddd.oi.presentation.core.navigation.Route
import com.ddd.oi.presentation.core.navigation.ScheduleNavType
import com.ddd.oi.presentation.scheduledetail.contract.PlaceUiState
import com.ddd.oi.presentation.scheduledetail.contract.ScheduleDetailSideEffect
import com.ddd.oi.presentation.scheduledetail.contract.ScheduleDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class ScheduleDetailViewModel @Inject constructor(
    private val getScheduleDetailsUseCase: GetScheduleDetailsUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<ScheduleDetailState, ScheduleDetailSideEffect>, ViewModel() {

    val schedule = savedStateHandle.toRoute<Route.ScheduleDetail>(
        mapOf(typeOf<Schedule>() to ScheduleNavType)
    )

    override val container =
        container<ScheduleDetailState, ScheduleDetailSideEffect>(ScheduleDetailState(schedule = schedule.schedule))

    init {
        getSchedulePlaces(schedule.schedule.id)
    }

    fun getSchedulePlaces(id: Long) = intent {
        getScheduleDetailsUseCase(id)
            .onSuccess { places->
                Log.d("places", places.toString())
                reduce { state.copy(placeUiState = PlaceUiState.Success(places)) }
            }
            .onFailure {
                Log.d("places", it.toString())
                reduce { state.copy(placeUiState = PlaceUiState.Error()) }
            }
    }

    fun editPlaceTime() = intent {

    }
}