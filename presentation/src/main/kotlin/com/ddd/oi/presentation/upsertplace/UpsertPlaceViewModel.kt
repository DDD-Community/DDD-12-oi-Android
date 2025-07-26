package com.ddd.oi.presentation.upsertplace

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ddd.oi.domain.model.Place
import com.ddd.oi.domain.model.schedule.SchedulePlace
import com.ddd.oi.domain.repository.PlaceRepository
import com.ddd.oi.domain.repository.ScheduleDetailRepository
import com.ddd.oi.domain.usecase.place.QueryPlaceUseCase
import com.ddd.oi.domain.usecase.scheduledetail.UpdateScheduleDetailUseCase
import com.ddd.oi.presentation.core.navigation.Route
import com.ddd.oi.presentation.core.navigation.SchedulePlaceNavType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class UpsertPlaceViewModel @Inject constructor(
    private val queryPlaceUseCase: QueryPlaceUseCase,
    private val placeRepository: PlaceRepository,
    private val scheduleDetailRepository: ScheduleDetailRepository,
    private val updateScheduleDetailUseCase: UpdateScheduleDetailUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val schedulePlace = savedStateHandle.toRoute<Route.UpsertPlace>(
        typeMap = mapOf(typeOf<SchedulePlace>() to SchedulePlaceNavType)
    )
    private val schedulePaceInfo = schedulePlace.schedulePlace
    private var scheduleId: Long = schedulePlace.scheduleId


    private val _eventChannel = Channel<SearchPlaceEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

//    private val _error = MutableSharedFlow<SearchPlaceEvent>()
//    val error = _error.asSharedFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private var selectedPlace: Place? = null

    val searchPlace = placeRepository.getRecentSearchPlace()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val placeList: Flow<Pair<String, List<Place>>> = _query
        .debounce(300L)
        .map { query -> query.ifEmpty { throw IllegalArgumentException("Query is empty") } }
        .filter { query -> query.length > 2 }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            queryPlaceUseCase(query).map { placeList ->
                query to placeList
            }
        }

    private val _uiState: MutableStateFlow<SearchPlaceUiState> =
        MutableStateFlow(
            SearchPlaceUiState.QueryEmpty(
                selectedPlace = selectedPlace
            )
        )
    val uiState: StateFlow<SearchPlaceUiState> = _uiState.asStateFlow()

    fun query(query: String) {
        _query.update { query }

        placeList.onEach { (query, placeList) ->
            val result: SearchPlaceUiState = SearchPlaceUiState.Typing(
                query = query,
                placeList = placeList.ifEmpty { throw IllegalArgumentException("List is empty") },
                selectedPlace = selectedPlace
            )

            _uiState.update { result }
        }.catch { exception ->
            if (exception is IllegalArgumentException && exception.message == "List is empty") {
                _uiState.update {
                    SearchPlaceUiState.ResultEmpty(
                        selectedPlace = selectedPlace
                    )
                }
            }
            if (exception is IllegalArgumentException && exception.message == "Query is empty") {
                _uiState.update {
                    SearchPlaceUiState.QueryEmpty(
                        selectedPlace = selectedPlace
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun search(query: String) {
        viewModelScope.launch {
            placeRepository.addRecentSearchPlace(query)
        }

        queryPlaceUseCase(query)
            .map { placeList ->
                query to placeList
            }.onEach { (query, placeList) ->
                val result: SearchPlaceUiState = SearchPlaceUiState.Typing(
                    query = query,
                    placeList = placeList.ifEmpty { throw IllegalArgumentException("List is empty") },
                    selectedPlace = selectedPlace
                )

                _uiState.update {
                    when (it) {
                        is SearchPlaceUiState.QueryEmpty -> it
                        is SearchPlaceUiState.ResultEmpty -> it
                        is SearchPlaceUiState.Typing -> result
                    }
                }
            }.catch { exception ->
                if (exception is IllegalArgumentException && exception.message == "List is empty") {
                    _uiState.update {
                        SearchPlaceUiState.ResultEmpty(
                            selectedPlace = selectedPlace
                        )
                    }
                }
                if (exception is IllegalArgumentException && exception.message == "Query is empty") {
                    _uiState.update {
                        SearchPlaceUiState.QueryEmpty(
                            selectedPlace = selectedPlace
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun searchImmediate(query: String) {
        _query.update { query }

        queryPlaceUseCase(query)
            .map { placeList ->
                query to placeList
            }.onEach { (query, placeList) ->
                val result: SearchPlaceUiState = SearchPlaceUiState.Typing(
                    query = query,
                    placeList = placeList.ifEmpty { throw IllegalArgumentException("List is empty") },
                    selectedPlace = selectedPlace
                )

                _uiState.update { result }
            }.catch { exception ->
                if (exception is IllegalArgumentException && exception.message == "List is empty") {
                    _uiState.update {
                        SearchPlaceUiState.ResultEmpty(
                            selectedPlace = selectedPlace
                        )
                    }
                }
                if (exception is IllegalArgumentException && exception.message == "Query is empty") {
                    _uiState.update {
                        SearchPlaceUiState.QueryEmpty(
                            selectedPlace = selectedPlace
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun selectPlace(place: Place) {
        if (selectedPlace == place) {
            selectedPlace = null
        } else if (selectedPlace != null) {
            viewModelScope.launch { _eventChannel.send(SearchPlaceEvent.ShowErrorMessage("선택 실패")) }
        } else {
            selectedPlace = place

            viewModelScope.launch {
                placeRepository.addRecentSearchPlace(place.title)
            }
        }

        _uiState.update {
            when (it) {
                is SearchPlaceUiState.QueryEmpty -> it.copy(
                    selectedPlace = selectedPlace
                )

                is SearchPlaceUiState.ResultEmpty -> it.copy(
                    selectedPlace = selectedPlace
                )

                is SearchPlaceUiState.Typing -> it.copy(
                    selectedPlace = selectedPlace
                )
            }
        }
    }

    fun removePlace(place: Place) {
        selectedPlace = null

        _uiState.update {
            when (it) {
                is SearchPlaceUiState.QueryEmpty -> it.copy(
                    selectedPlace = selectedPlace
                )

                is SearchPlaceUiState.ResultEmpty -> it.copy(
                    selectedPlace = selectedPlace
                )

                is SearchPlaceUiState.Typing -> it.copy(
                    selectedPlace = selectedPlace
                )
            }
        }
    }

    fun removeQuery(query: String) {
        viewModelScope.launch {
            placeRepository.removeRecentSearchPlace(query)
        }
    }

    fun clearSearchPlace() {
        viewModelScope.launch {
            placeRepository.clearRecentSearchPlace()
        }
    }

    fun updatePlace() {
        selectedPlace?.let {
            val schedulePlace = schedulePaceInfo.copy(
                spotName = it.title,
                latitude = it.latitude,
                longitude = it.longitude,
                category = it.category
            )
            Log.d("selectedPlace", scheduleId.toString())
            Log.d("selectedPlace", schedulePlace.toString())
            viewModelScope.launch {
                updateScheduleDetailUseCase(scheduleId, schedulePlace)
                    .onSuccess {
                        _eventChannel.send(SearchPlaceEvent.CompleteEditPlace)
                    }.onFailure {
                        _eventChannel.send(SearchPlaceEvent.ShowErrorMessage("일정 수정에 실패했습니다."))
                    }
            }
        }
    }
}

sealed class SearchPlaceUiState(
    open val selectedPlace: Place?,
) {
    data class QueryEmpty(
        override val selectedPlace: Place?,
    ) : SearchPlaceUiState(selectedPlace)

    data class Typing(
        val query: String,
        val placeList: List<Place>,
        override val selectedPlace: Place?,
    ) : SearchPlaceUiState(selectedPlace)

    data class ResultEmpty(
        override val selectedPlace: Place?,
    ) : SearchPlaceUiState(selectedPlace)
}

sealed class SearchPlaceEvent {
    data object CompleteEditPlace: SearchPlaceEvent()
    data class ShowErrorMessage(val message: String?): SearchPlaceEvent()
}