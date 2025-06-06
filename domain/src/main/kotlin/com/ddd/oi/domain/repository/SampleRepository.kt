package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.Sample
import kotlinx.coroutines.flow.Flow

interface SampleRepository {
    suspend fun createSample(sample: Sample)
    suspend fun readSample(): Flow<Sample>
    suspend fun updateSample(sample: Sample)
    suspend fun deleteSample(sample: Sample)
}