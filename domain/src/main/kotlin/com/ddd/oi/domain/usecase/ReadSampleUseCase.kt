package com.ddd.oi.domain.usecase

import com.ddd.oi.domain.model.Sample
import com.ddd.oi.domain.repository.SampleRepository
import com.ddd.oi.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat

class ReadSampleUseCase(
    private val userRepository: UserRepository,
    private val sampleRepository: SampleRepository
) : suspend () -> Flow<Sample> {
    override suspend fun invoke(): Flow<Sample> {
        return userRepository.readUser().filter {
            it.token.isNotEmpty()
        }.flatMapConcat {
            sampleRepository.readSample()
        }
    }
}