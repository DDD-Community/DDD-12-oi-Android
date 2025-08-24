package com.ddd.oi.presentation.login.social

import android.content.Context
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import javax.inject.Inject

class NaverLoginHandler @Inject constructor() : AuthHandler {
    override suspend fun loginWithSocial(context: Context): SignInResult {
        try {
            // 강제 재인증을 위해 reagreeAuthenticate 사용
            return SignInResult.Success(loginWithNaverReauth(context))
        } catch (e: Exception) {
            return SignInResult.Failure(e)
        }
    }

    override suspend fun logout(): Boolean {
        return try {
            NaverIdLoginSDK.logout()
            true
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun loginWithNaverReauth(context: Context): String = suspendCoroutine { cont ->
        val oauthLoginCallback: OAuthLoginCallback = object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                cont.resumeWithException(Exception(message))
            }

            override fun onFailure(httpStatus: Int, message: String) {
                cont.resumeWithException(Exception(message))
            }

            override fun onSuccess() {
                val accessToken: String? = NaverIdLoginSDK.getAccessToken()
                if (accessToken != null) {
                    cont.resume(accessToken)
                } else {
                    cont.resumeWithException(Exception("네이버 로그인에 실패했습니다"))
                }
            }

        }
        NaverIdLoginSDK.reagreeAuthenticate(context, oauthLoginCallback)
    }
}
