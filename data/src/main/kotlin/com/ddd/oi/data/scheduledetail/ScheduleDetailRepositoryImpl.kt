package com.ddd.oi.data.scheduledetail

import com.ddd.oi.data.scheduledetail.mapper.toDomain
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailRemoteSource
import com.ddd.oi.domain.model.schedule.Place
import com.ddd.oi.domain.repository.ScheduleDetailRepository
import javax.inject.Inject

class ScheduleDetailRepositoryImpl @Inject constructor(
    private val scheduleDetailRemoteSource: ScheduleDetailRemoteSource
) : ScheduleDetailRepository {
    override suspend fun getScheduleDetail(scheduleId: Long): Result<Map<String, List<Place>>> {
        return scheduleDetailRemoteSource.getScheduleDetails(scheduleId).map { dtoList ->
            dtoList.associateBy(
                keySelector = { it.targetDate },
                valueTransform = { placeDto->
                    placeDto.details.map { it.toDomain() }
                }
            )
        }
    }
}

