package com.ddd.oi.domain.usecase.auth

import kotlinx.coroutines.flow.Flow

interface GetCurrentSocialTypeUseCase {
    operator fun invoke(): Flow<String?>
}