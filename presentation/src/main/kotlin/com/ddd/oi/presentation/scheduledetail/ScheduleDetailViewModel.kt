package com.ddd.oi.presentation.scheduledetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.model.schedule.SchedulePlace
import com.ddd.oi.domain.usecase.scheduledetail.DeleteScheduleDetailUseCase
import com.ddd.oi.domain.usecase.scheduledetail.GetScheduleDetailsUseCase
import com.ddd.oi.domain.usecase.scheduledetail.UpdateScheduleDetailUseCase
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
    private val updateScheduleDetailUseCase: UpdateScheduleDetailUseCase,
    private val deleteScheduleDetailUseCase: DeleteScheduleDetailUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<ScheduleDetailState, ScheduleDetailSideEffect>, ViewModel() {

    val schedule = savedStateHandle.toRoute<Route.ScheduleDetail>(
        mapOf(typeOf<Schedule>() to ScheduleNavType)
    )

    override val container =
        container<ScheduleDetailState, ScheduleDetailSideEffect>(ScheduleDetailState(schedule = schedule.schedule))


    fun getSchedulePlaces() = intent {
        getScheduleDetailsUseCase(schedule.schedule.id)
            .onSuccess { places ->
                Log.d("places sucess", places.toString())
                reduce { state.copy(placeUiState = PlaceUiState.Success(places)) }
            }
            .onFailure {
                Log.d("places fail", it.toString())
                reduce { state.copy(placeUiState = PlaceUiState.Error()) }
            }
    }

    fun updateSchedulePlaceTime(
        selectedPlace: SchedulePlace,
        startTime: String,
    ) = intent {
        val scheduleId = schedule.schedule.id
        val place = selectedPlace.copy(startTime = startTime)
        updateScheduleDetailUseCase(scheduleId = scheduleId, scheduleDetail = place)
            .onSuccess { place ->
                getSchedulePlaces()
                Log.d("updateSchedulePlaceTime Success", place.toString())
            }
            .onFailure {
                Log.d("updateSchedulePlaceTime Fail", it.toString())
            }
    }

    fun updateSchedulePlaceMemo(
        selectedPlace: SchedulePlace,
        memo: String,
    ) = intent {
        val scheduleId = schedule.schedule.id
        val place = selectedPlace.copy(memo = memo)
        updateScheduleDetailUseCase(scheduleId = scheduleId, scheduleDetail = place)
            .onSuccess { place ->
                getSchedulePlaces()
                Log.d("updateSchedulePlaceMemo Success", place.toString())
            }
            .onFailure {
                Log.d("updateSchedulePlaceMemo Fail", it.toString())
            }
    }

    fun deleteScheduleDetail(schedulePlace: SchedulePlace) = intent {
        val scheduleId = schedule.schedule.id
        val scheduleDetailId = schedulePlace.id
        deleteScheduleDetailUseCase(scheduleId = scheduleId, scheduleDetailId = scheduleDetailId)
            .onSuccess {
                getSchedulePlaces()
                //TODO: 삭제 토스트 메시지 처리
            }
            .onFailure {
                Log.d("deleteScheduleDetail Fail", it.toString())
                //TODO: 삭제 실패 토스트 처리
            }
    }
}