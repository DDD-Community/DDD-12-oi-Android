package com.ddd.oi.data.auth.mapper

import com.ddd.oi.data.auth.model.LoginRequestDto
import com.ddd.oi.data.auth.model.LoginResponseDto
import com.ddd.oi.domain.model.auth.LoginRequest
import com.ddd.oi.domain.model.auth.LoginResponse

internal fun LoginRequest.toDto(): LoginRequestDto {
    return LoginRequestDto(oauthAccessToken)
}

internal fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(
        id = id,
        nickname = nickname,
        email = email,
        providerInfo = providerInfo,
        role = role,
        accessToken = accessToken,
        refreshToken = refreshToken,
        oauthAccessToken = oauthAccessToken,
        lastReadAt = lastReadAt
    )
}