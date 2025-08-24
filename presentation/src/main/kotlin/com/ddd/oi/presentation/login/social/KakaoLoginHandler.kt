package com.ddd.oi.presentation.login.social

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import javax.inject.Inject

class KakaoLoginHandler @Inject constructor(): AuthHandler {
    /**
     * 카카오 로그인 수행 후 accessToken 반환
     */
    override suspend fun loginWithSocial(context: Context): SignInResult {
        try {
            val oauthToken: OAuthToken =
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                    loginWithKakaoTalk(context)
                } else {
                    loginWithKakaoAccount(context)
                }
            return SignInResult.Success(oauthToken.accessToken)
        } catch (e: Exception) {
            return SignInResult.Failure(e)
        }
    }

    override suspend fun logout(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    continuation.resume(false)
                } else {
                    continuation.resume(true)
                }
            }
        }
    }

    private suspend fun loginWithKakaoTalk(context: Context): OAuthToken =
        suspendCoroutine { cont ->
            UserApiClient.instance.loginWithKakaoTalk(context) { oauthToken, error ->
                if (error != null) {
                    cont.resumeWithException(error)
                } else if (oauthToken != null) {
                    cont.resume(oauthToken)
                } else {
                    cont.resumeWithException(Exception("카카오 로그인에 실패했습니다"))
                }
            }
        }

    private suspend fun loginWithKakaoAccount(context: Context): OAuthToken =
        suspendCoroutine { cont ->
            UserApiClient.instance.loginWithKakaoAccount(context) { oauthToken, error ->
                if (error != null) {
                    cont.resumeWithException(error)
                } else if (oauthToken != null) {
                    cont.resume(oauthToken)
                } else {
                    cont.resumeWithException(Exception("카카오 로그인에 실패했습니다"))
                }
            }
        }
}