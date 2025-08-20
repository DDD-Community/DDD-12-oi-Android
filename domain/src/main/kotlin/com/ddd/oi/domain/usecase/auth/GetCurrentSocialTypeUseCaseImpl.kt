package com.ddd.oi.domain.usecase.auth

import com.ddd.oi.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentSocialTypeUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
): GetCurrentSocialTypeUseCase {
    override operator fun invoke(): Flow<String?> {
        return authRepository.getCurrentLoginType()
    }
}