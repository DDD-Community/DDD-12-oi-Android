package com.ddd.oi.data.setting

import com.ddd.oi.data.core.datastore.SettingDataStore
import com.ddd.oi.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingDataStore: SettingDataStore
) : SettingRepository {
    
    override fun getAnnouncementReadAt(): Flow<Long> {
        return settingDataStore.getAnnouncementReadAt()
    }
    
    override suspend fun setAnnouncementReadAt(timestamp: Long) {
        settingDataStore.setAnnouncementReadAt(timestamp)
    }
}