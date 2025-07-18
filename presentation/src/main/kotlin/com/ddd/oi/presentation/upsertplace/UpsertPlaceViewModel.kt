package com.ddd.oi.presentation.upsertplace

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.Place
import com.ddd.oi.domain.repository.PlaceRepository
import com.ddd.oi.domain.repository.ScheduleDetailRepository
import com.ddd.oi.domain.usecase.place.QueryPlaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpsertPlaceViewModel @Inject constructor(
    private val queryPlaceUseCase: QueryPlaceUseCase,
    private val placeRepository: PlaceRepository,
    private val scheduleDetailRepository: ScheduleDetailRepository,
) : ViewModel() {
    private var scheduleId: Long = 0L

    private val _error = MutableSharedFlow<Unit>()
    val error = _error.asSharedFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val selectedPlace: MutableList<Place> = mutableStateListOf()

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
                selectedPlaceList = selectedPlace
            )
        )
    val uiState: StateFlow<SearchPlaceUiState> = _uiState.asStateFlow()

    fun setScheduleId(scheduleId: Long) {
        this.scheduleId = scheduleId
    }

    fun query(query: String) {
        _query.update { query }

        placeList.onEach { (query, placeList) ->
            val result: SearchPlaceUiState = SearchPlaceUiState.Typing(
                query = query,
                placeList = placeList.ifEmpty { throw IllegalArgumentException("List is empty") },
                selectedPlaceList = selectedPlace
            )

            _uiState.update { result }
        }.catch { exception ->
            if (exception is IllegalArgumentException && exception.message == "List is empty") {
                _uiState.update {
                    SearchPlaceUiState.ResultEmpty(
                        selectedPlaceList = selectedPlace
                    )
                }
            }
            if (exception is IllegalArgumentException && exception.message == "Query is empty") {
                _uiState.update {
                    SearchPlaceUiState.QueryEmpty(
                        selectedPlaceList = selectedPlace
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
                    selectedPlaceList = selectedPlace
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
                            selectedPlaceList = selectedPlace
                        )
                    }
                }
                if (exception is IllegalArgumentException && exception.message == "Query is empty") {
                    _uiState.update {
                        SearchPlaceUiState.QueryEmpty(
                            selectedPlaceList = selectedPlace
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
                    selectedPlaceList = selectedPlace
                )

                _uiState.update { result }
            }.catch { exception ->
                if (exception is IllegalArgumentException && exception.message == "List is empty") {
                    _uiState.update {
                        SearchPlaceUiState.ResultEmpty(
                            selectedPlaceList = selectedPlace
                        )
                    }
                }
                if (exception is IllegalArgumentException && exception.message == "Query is empty") {
                    _uiState.update {
                        SearchPlaceUiState.QueryEmpty(
                            selectedPlaceList = selectedPlace
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun selectPlace(place: Place) {
        if (selectedPlace.contains(place)) {
            selectedPlace.remove(place)
        } else if (selectedPlace.size > 0) {
            viewModelScope.launch { _error.emit(Unit) }
        } else {
            selectedPlace.add(place)
        }

        _uiState.update {
            when (it) {
                is SearchPlaceUiState.QueryEmpty -> it.copy(
                    selectedPlaceList = selectedPlace
                )

                is SearchPlaceUiState.ResultEmpty -> it.copy(
                    selectedPlaceList = selectedPlace
                )

                is SearchPlaceUiState.Typing -> it.copy(
                    selectedPlaceList = selectedPlace
                )
            }
        }
    }

    fun removePlace(place: Place) {
        selectedPlace.remove(place)

        _uiState.update {
            when (it) {
                is SearchPlaceUiState.QueryEmpty -> it.copy(
                    selectedPlaceList = selectedPlace
                )

                is SearchPlaceUiState.ResultEmpty -> it.copy(
                    selectedPlaceList = selectedPlace
                )

                is SearchPlaceUiState.Typing -> it.copy(
                    selectedPlaceList = selectedPlace
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
        // todo set api
    }
}

sealed class SearchPlaceUiState(
    open val selectedPlaceList: List<Place>,
) {
    data class QueryEmpty(
        override val selectedPlaceList: List<Place>,
    ) : SearchPlaceUiState(selectedPlaceList)

    data class Typing(
        val query: String,
        val placeList: List<Place>,
        override val selectedPlaceList: List<Place>,
    ) : SearchPlaceUiState(selectedPlaceList)

    data class ResultEmpty(
        override val selectedPlaceList: List<Place>,
    ) : SearchPlaceUiState(selectedPlaceList)
}