package com.ddd.oi.presentation.login

import androidx.lifecycle.ViewModel
import com.ddd.oi.presentation.login.contract.LoginSideEffect
import com.ddd.oi.presentation.login.contract.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

): ContainerHost<LoginState, LoginSideEffect>, ViewModel() {
    override val container = container<LoginState, LoginSideEffect>(LoginState())
}