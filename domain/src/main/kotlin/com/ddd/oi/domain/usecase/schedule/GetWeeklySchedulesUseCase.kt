package com.ddd.oi.domain.usecase.schedule

import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.repository.ScheduleRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.DateTimeUnit
import javax.inject.Inject

class GetWeeklySchedulesUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(baseDate: LocalDate): Result<Map<LocalDate, List<Schedule>>> {
        return try {
            val weeklySchedules = mutableMapOf<LocalDate, List<Schedule>>()
            val startOfWeek = getStartOfWeek(baseDate)
            
            // 일주일간의 데이터를 각각 API 호출로 가져옴
            for (i in 0..6) {
                val targetDate = startOfWeek.plus(i, DateTimeUnit.DAY)
                val targetDayString = targetDate.toString() // yyyy-MM-dd 형식
                
                scheduleRepository.getSchedulesByTargetDay(targetDayString)
                    .onSuccess { schedules ->
                        weeklySchedules[targetDate] = schedules
                    }
                    .onFailure {
                        weeklySchedules[targetDate] = emptyList()
                    }
            }
            
            Result.success(weeklySchedules)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun getStartOfWeek(date: LocalDate): LocalDate {
        val dayOfWeek = date.dayOfWeek.ordinal // 월요일부터 0
        return date.minus(dayOfWeek, DateTimeUnit.DAY)
    }
}