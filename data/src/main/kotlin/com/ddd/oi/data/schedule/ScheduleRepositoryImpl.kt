package com.ddd.oi.data.schedule

import com.ddd.oi.data.schedule.mapper.toDomain
import com.ddd.oi.data.schedule.mapper.toRequest
import com.ddd.oi.data.schedule.remote.ScheduleRemoteDataSource
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.repository.ScheduleRepository
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleRemoteDataSource: ScheduleRemoteDataSource
) : ScheduleRepository {
    override suspend fun getScheduleList(year: Int, month: Int): Result<List<Schedule>> {
        return scheduleRemoteDataSource.getScheduleList(year, month)
            .map { dtoList ->
                dtoList.map { it.toDomain() }
            }
    }

    override suspend fun deleteSchedule(scheduleId: Long): Result<Boolean> {
        return scheduleRemoteDataSource.deleteSchedule(scheduleId)
    }

    override suspend fun createSchedule(schedule: Schedule): Result<Schedule> {
        return scheduleRemoteDataSource.createSchedule(schedule.toRequest()).map { it.toDomain() }
    }

    override suspend fun updateSchedule(schedule: Schedule): Result<Schedule> {
        return  scheduleRemoteDataSource.updateSchedule(schedule.id, schedule.toRequest()).map { it.toDomain() }
    }
}