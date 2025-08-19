package com.ddd.oi.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.oi.domain.model.social.SocialType
import com.ddd.oi.presentation.login.social.AuthResult
import com.ddd.oi.presentation.login.social.SocialAuthManager
import com.ddd.oi.presentation.login.contract.LoginSideEffect
import com.ddd.oi.presentation.login.contract.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val socialAuthManager: SocialAuthManager
) : ContainerHost<LoginState, LoginSideEffect>, ViewModel() {
    override val container = container<LoginState, LoginSideEffect>(LoginState())


    val currentSocialType: StateFlow<SocialType?> = socialAuthManager.currentSocialType
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    fun onLoginClicked(socialType: SocialType, context: Context) = intent {
        reduce { state.copy(isLoading = true) }
        
        viewModelScope.launch {
            val socialName = socialType.name.lowercase()
            Log.d(socialName, "Starting ${socialName} login")
            
            when (val result = socialAuthManager.login(socialType, context)) {
                is AuthResult.Success -> {
                    Log.d(socialName, "Login success")
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(LoginSideEffect.LoginSuccess)
                }
                is AuthResult.Failure -> {
                    Log.d(socialName, "Login failed: ${result.throwable.message}")
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(LoginSideEffect.LoginFailure(result.throwable))
                }
            }
        }
    }

}