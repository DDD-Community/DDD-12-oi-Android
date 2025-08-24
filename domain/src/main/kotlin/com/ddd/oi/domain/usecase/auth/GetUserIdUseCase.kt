package com.ddd.oi.domain.usecase.auth

import kotlinx.coroutines.flow.Flow

interface GetUserIdUseCase {
    operator fun invoke(): Flow<Long?>
}