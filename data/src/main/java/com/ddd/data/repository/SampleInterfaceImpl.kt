package com.ddd.data.repository

import com.ddd.data.source.remote.SampleDataSource
import com.ddd.domain.repository.SampleInterface
import javax.inject.Inject

class SampleInterfaceImpl @Inject constructor(
    private val sampleDataSource: SampleDataSource
): SampleInterface {
    override suspend fun sample() {
        TODO("Not yet implemented")
    }
}