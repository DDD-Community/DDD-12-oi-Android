package com.ddd.oi.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.auth.LoginRequest
import com.ddd.oi.domain.model.social.SocialType
import com.ddd.oi.domain.usecase.auth.GetCurrentSocialTypeUseCase
import com.ddd.oi.domain.usecase.auth.LoginUseCase
import com.ddd.oi.presentation.login.contract.LoginSideEffect
import com.ddd.oi.presentation.login.contract.LoginState
import com.ddd.oi.presentation.login.social.GoogleLoginHandler
import com.ddd.oi.presentation.login.social.KakaoLoginHandler
import com.ddd.oi.presentation.login.social.NaverLoginHandler
import com.ddd.oi.presentation.login.social.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getCurrentSocialTypeUseCase: GetCurrentSocialTypeUseCase
) : ContainerHost<LoginState, LoginSideEffect>, ViewModel() {
    override val container = container<LoginState, LoginSideEffect>(LoginState())

    private val kakaoLoginHandler = KakaoLoginHandler()
    private val naverLoginHandler = NaverLoginHandler()
    private val googleLoginHandler = GoogleLoginHandler()

    val currentSocialType: StateFlow<SocialType?> = getCurrentSocialTypeUseCase.invoke()
        .map { socialType ->
            Log.d("socialType", socialType.toString())
            when (socialType) {
                "KAKAO" -> SocialType.KAKAO
                "NAVER" -> SocialType.NAVER
                "GOOGLE" -> SocialType.GOOGLE
                else -> null
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    fun onLoginClicked(socialType: SocialType, context: Context) = intent {
        reduce { state.copy(isLoading = true) }
        handleSocialLogin(socialType, context)
    }

    private fun handleSocialLogin(socialType: SocialType, context: Context) = intent {
        val loginHandler = getLoginHandler(socialType)
        val socialName = socialType.name.lowercase()
        
        viewModelScope.launch {
            when (val result = loginHandler.loginWithSocial(context)) {
                is SignInResult.Failure -> {
                    Log.d(socialName, result.throwable.message.toString())
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(LoginSideEffect.LoginFailure(result.throwable))
                }
                is SignInResult.Success -> {
                    Log.d(socialName, "Social login success: ${result.accessToken}")
                    performLogin(socialType, result.accessToken, socialName)
                }
            }
        }
    }

    private fun performLogin(socialType: SocialType, accessToken: String, socialName: String) = intent {
        val loginRequest = LoginRequest(accessToken)
        loginUseCase(socialType, loginRequest).fold(
            onSuccess = { loginResponse ->
                Log.d(socialName, "Login success: $loginResponse")
                reduce { state.copy(isLoading = false) }
                postSideEffect(LoginSideEffect.LoginSuccess)
            },
            onFailure = { throwable ->
                Log.d(socialName, "Login failed: ${throwable.message}")
                reduce { state.copy(isLoading = false) }
                postSideEffect(LoginSideEffect.LoginFailure(throwable))
            }
        )
    }

    private fun getLoginHandler(socialType: SocialType) = when (socialType) {
        SocialType.KAKAO -> kakaoLoginHandler
        SocialType.NAVER -> naverLoginHandler
        SocialType.GOOGLE -> googleLoginHandler
    }

}