package com.ddd.oi.data.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDto(
    val name: String,
    val email: String,
    val providerInfo: String
)

@Serializable
data class SystemInfoDto(
    @SerialName("updateAt")
    val updatedAt: String,
    val version: String
)

@Serializable
data class UserResponseDto(
    val userInfo: UserInfoDto,
    val systemInfo: SystemInfoDto
)