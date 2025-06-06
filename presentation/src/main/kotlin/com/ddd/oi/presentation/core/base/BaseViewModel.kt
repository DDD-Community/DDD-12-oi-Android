package com.ddd.oi.presentation.core.base

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

abstract class BaseViewModel <UI_STATE: UiState, SIDE_EFFECT: SideEffect>(
    initialState: UI_STATE,
): ContainerHost<UI_STATE, SIDE_EFFECT>, ViewModel() {

    override val container = container<UI_STATE, SIDE_EFFECT>(initialState)

    val currentState: UI_STATE
        get() = container.stateFlow.value

    protected fun updateState(reducer: UI_STATE.() -> UI_STATE) = intent {
        reduce { state.reducer() }
    }

    protected fun emitSideEffect(vararg sideEffect: SIDE_EFFECT) = intent {
        sideEffect.forEach { sideEffect -> postSideEffect(sideEffect) }
    }
}