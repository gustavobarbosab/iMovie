package com.github.gustavobarbosab.imovies.core.presentation.arch

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class SideEffectProcessor<STATE, INTENT, RESULT, SIDE_EFFECT> {

    private val _sideEffect = MutableSharedFlow<SIDE_EFFECT>()
    val sideEffect: SharedFlow<SIDE_EFFECT>
        get() = _sideEffect

    fun preProcessing(state: STATE, intent: INTENT) {
        val sideEffect = preSideEffect(state, intent) ?: return
        _sideEffect.tryEmit(sideEffect)
    }

    fun postProcessing(state: STATE, result: RESULT) {
        val sideEffect = postSideEffect(state, result) ?: return
        _sideEffect.tryEmit(sideEffect)
    }

    protected open fun preSideEffect(state: STATE, intent: INTENT): SIDE_EFFECT? {
        return null
    }

    protected open fun postSideEffect(state: STATE, result: RESULT): SIDE_EFFECT? {
        return null
    }
}