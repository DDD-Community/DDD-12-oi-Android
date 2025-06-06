package com.ddd.oi.data.sample

import com.ddd.oi.data.sample.local.SampleLocalDataSource
import com.ddd.oi.data.sample.remote.SampleRemoteDataSource
import com.ddd.oi.domain.model.Sample
import com.ddd.oi.domain.repository.SampleRepository
import kotlinx.coroutines.flow.Flow

class SampleRepositoryImpl(
    private val sampleLocalDataSource: SampleLocalDataSource,
    private val sampleRemoteDataSource: SampleRemoteDataSource,
) : SampleRepository {
    override suspend fun createSample(sample: Sample) {
        TODO("Not yet implemented")
    }

    override suspend fun readSample(): Flow<Sample> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSample(sample: Sample) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSample(sample: Sample) {
        TODO("Not yet implemented")
    }
}