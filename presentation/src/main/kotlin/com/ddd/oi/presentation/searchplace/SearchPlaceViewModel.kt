package com.ddd.oi.presentation.searchplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.Place
import com.ddd.oi.domain.usecase.place.QueryPlaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchPlaceViewModel @Inject constructor(
    private val queryPlaceUseCase: QueryPlaceUseCase
) : ViewModel() {
    private var scheduleId: Long = 0L

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val selectedPlace: HashSet<Place> = hashSetOf()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val placeList: Flow<Pair<String, List<Place>>> = _query
        .debounce(300L)
        .map { query -> query.ifEmpty { throw IllegalArgumentException("Query is empty") } }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            // todo replace api
            queryPlaceUseCase(query).map { placeList ->
                query to placeList
            }
        }

    private val _uiState: MutableStateFlow<SearchPlaceUiState> =
        MutableStateFlow(SearchPlaceUiState.QueryEmpty(selectedPlaceList = selectedPlace.toList()))
    val uiState: StateFlow<SearchPlaceUiState> = _uiState.asStateFlow()

    fun setScheduleId(scheduleId: Long) {
        this.scheduleId = scheduleId
    }

    fun search(query: String) {
        _query.update { query }

        placeList.onEach { (query, placeList) ->
            val result: SearchPlaceUiState = SearchPlaceUiState.Typing(
                query = query,
                placeList = placeList.ifEmpty { throw IllegalArgumentException("List is empty") },
                selectedPlaceList = selectedPlace.toList()
            )

            _uiState.update { result }
        }.catch {
            if (it is IllegalArgumentException && it.message == "List is empty") {
                _uiState.update {
                    SearchPlaceUiState.ResultEmpty(
                        selectedPlaceList = selectedPlace.toList()
                    )
                }
            }
            if (it is IllegalArgumentException && it.message == "Query is empty") {
                _uiState.update {
                    SearchPlaceUiState.QueryEmpty(
                        selectedPlaceList = selectedPlace.toList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun selectPlace(place: Place) {
        if (selectedPlace.contains(place)) selectedPlace.remove(place)
        else selectedPlace.add(place)

        _uiState.update {
            when(it) {
                is SearchPlaceUiState.QueryEmpty -> it.copy(selectedPlaceList = selectedPlace.toList())
                is SearchPlaceUiState.ResultEmpty -> it.copy(selectedPlaceList = selectedPlace.toList())
                is SearchPlaceUiState.Typing -> it.copy(selectedPlaceList = selectedPlace.toList())
            }
        }
    }
}

sealed class SearchPlaceUiState(
    open val selectedPlaceList: List<Place>
) {
    data class QueryEmpty(
        override val selectedPlaceList: List<Place>
    ) : SearchPlaceUiState(selectedPlaceList)

    data class Typing(
        val query: String,
        val placeList: List<Place>,
        override val selectedPlaceList: List<Place>
    ) : SearchPlaceUiState(selectedPlaceList)

    data class ResultEmpty(
        override val selectedPlaceList: List<Place>
    ) : SearchPlaceUiState(selectedPlaceList)
}