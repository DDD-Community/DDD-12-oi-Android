package com.ddd.oi.data.schedule

import com.ddd.oi.data.schedule.remote.ScheduleRemoteDataSource
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.repository.ScheduleRepository
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleRemoteDataSource: ScheduleRemoteDataSource
) : ScheduleRepository {
    override suspend fun getScheduleList(year: Int, month: Int): Result<List<Schedule>> {
        return scheduleRemoteDataSource.getScheduleList(year, month)
            .mapCatching { dtoList ->
                dtoList.map { it.toDomain() }
            }
    }

    override suspend fun deleteSchedule(scheduleId: Long): Result<Unit> {
        return scheduleRemoteDataSource.deleteSchedule(scheduleId)
    }
}