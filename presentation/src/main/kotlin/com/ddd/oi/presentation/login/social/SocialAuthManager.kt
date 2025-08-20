package com.ddd.oi.presentation.login.social

import android.content.Context
import com.ddd.oi.domain.model.auth.LoginRequest
import com.ddd.oi.domain.model.social.SocialType
import com.ddd.oi.domain.usecase.auth.GetCurrentSocialTypeUseCase
import com.ddd.oi.domain.usecase.auth.LoginUseCase
import com.ddd.oi.domain.usecase.auth.LogoutUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

sealed class AuthResult {
    object Success : AuthResult()
    data class Failure(val throwable: Throwable) : AuthResult()
}

@Singleton
class SocialAuthManager @Inject constructor(
    private val getCurrentSocialTypeUseCase: GetCurrentSocialTypeUseCase,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val kakaoLoginHandler: KakaoLoginHandler,
    private val naverLoginHandler: NaverLoginHandler,
    private val googleLoginHandler: GoogleLoginHandler
) {
    
    /**
     * 현재 로그인된 소셜 타입을 반환
     */
    val currentSocialType: Flow<SocialType?> = getCurrentSocialTypeUseCase()
        .map { socialTypeString ->
            when (socialTypeString) {
                "KAKAO" -> SocialType.KAKAO
                "NAVER" -> SocialType.NAVER
                "GOOGLE" -> SocialType.GOOGLE
                else -> null
            }
        }
    
    /**
     * 소셜 로그인 수행
     */
    suspend fun login(socialType: SocialType, context: Context): AuthResult {
        return try {
            val socialResult = performSocialLogin(socialType, context)
            when (socialResult) {
                is SignInResult.Success -> {
                    val loginRequest = LoginRequest(socialResult.accessToken)
                    loginUseCase(socialType, loginRequest).fold(
                        onSuccess = { AuthResult.Success },
                        onFailure = { AuthResult.Failure(it) }
                    )
                }
                is SignInResult.Failure -> AuthResult.Failure(socialResult.throwable)
            }
        } catch (e: Exception) {
            AuthResult.Failure(e)
        }
    }
    
    /**
     * 로그아웃 수행 (소셜 + 서버)
     */
    suspend fun logout(): AuthResult {
        return try {
            // 1. 현재 로그인된 소셜 타입 확인
            val currentType = currentSocialType.first()
            
            // 2. 소셜 플랫폼 로그아웃
            val socialLogoutSuccess = currentType?.let { 
                performSocialLogout(it) 
            } ?: true
            
            // 3. 서버 로그아웃 (토큰 삭제 등)
            logoutUseCase()
            
            if (socialLogoutSuccess) AuthResult.Success 
            else AuthResult.Failure(Exception("소셜 로그아웃 실패"))
            
        } catch (e: Exception) {
            AuthResult.Failure(e)
        }
    }
    
    /**
     * 특정 소셜 타입으로 강제 로그아웃
     */
    suspend fun logoutFromSocial(socialType: SocialType): Boolean {
        return performSocialLogout(socialType)
    }
    
    private suspend fun performSocialLogin(socialType: SocialType, context: Context): SignInResult {
        return getAuthHandler(socialType).loginWithSocial(context)
    }
    
    private suspend fun performSocialLogout(socialType: SocialType): Boolean {
        return getAuthHandler(socialType).logout()
    }
    
    private fun getAuthHandler(socialType: SocialType): AuthHandler {
        return when (socialType) {
            SocialType.KAKAO -> kakaoLoginHandler
            SocialType.NAVER -> naverLoginHandler
            SocialType.GOOGLE -> googleLoginHandler
        }
    }
}