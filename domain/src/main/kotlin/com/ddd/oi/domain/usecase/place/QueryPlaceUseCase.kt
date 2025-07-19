package com.ddd.oi.domain.usecase.place

import com.ddd.oi.domain.model.Place
import com.ddd.oi.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QueryPlaceUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
) {
    operator fun invoke(query: String): Flow<List<Place>> = flow {
        emit(placeRepository.queryPlace(query))
    }
}