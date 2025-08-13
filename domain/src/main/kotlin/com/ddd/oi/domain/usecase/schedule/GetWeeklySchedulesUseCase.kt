package com.ddd.oi.domain.usecase.schedule

import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.repository.ScheduleRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
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
        // 일요일부터 시작하는 주 계산
        val sundayOffset = if (date.dayOfWeek == DayOfWeek.SUNDAY) 0 else 7 - date.dayOfWeek.ordinal
        return date.minus(sundayOffset, DateTimeUnit.DAY)
    }
}