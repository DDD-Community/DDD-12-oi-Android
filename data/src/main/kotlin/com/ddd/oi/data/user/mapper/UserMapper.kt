package com.ddd.oi.data.user.mapper

import com.ddd.oi.data.user.model.SystemInfoDto
import com.ddd.oi.data.user.model.UserInfoDto
import com.ddd.oi.data.user.model.UserResponseDto
import com.ddd.oi.domain.model.SystemInfo
import com.ddd.oi.domain.model.User
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun UserInfoDto.toDomain(): User {
    return User(
        id = 0L,
        token = "",
        name = name,
        email = email,
        providerInfo = providerInfo
    )
}

fun SystemInfoDto.toDomain(): SystemInfo {
    val updatedAtMillis = try {
        ZonedDateTime.parse(updatedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .toInstant().toEpochMilli()
    } catch (e: Exception) {
        0L
    }
    
    return SystemInfo(
        version = version,
        updatedAt = updatedAtMillis
    )
}

data class UserSystemInfoPair(
    val user: User,
    val systemInfo: SystemInfo
)

fun UserResponseDto.toDomain(): UserSystemInfoPair {
    return UserSystemInfoPair(
        user = userInfo.toDomain(),
        systemInfo = systemInfo.toDomain()
    )
}