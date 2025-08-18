package com.ddd.oi.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getAnnouncementReadAt(): Flow<Long>
    suspend fun setAnnouncementReadAt(timestamp: Long)
}