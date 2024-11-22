package com.github.gustavobarbosab.imovies.core.presentation.arch

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * This class has the responsibility for creating side effects
 * and also it can be used to collect info to analytics purpose.
 */
abstract class SideEffectProcessor<STATE, INTENT, RESULT, SIDE_EFFECT> {

    private val _sideEffect = Channel<SIDE_EFFECT>()
    val sideEffect: Flow<SIDE_EFFECT>
        get() = _sideEffect.receiveAsFlow()

    fun preProcessing(state: STATE, intent: INTENT) {
        val sideEffect = preSideEffect(state, intent) ?: return
        _sideEffect.trySend(sideEffect)
    }

    fun postProcessing(state: STATE, result: RESULT) {
        val sideEffect = postSideEffect(state, result) ?: return
        _sideEffect.trySend(sideEffect)
    }

    protected open fun preSideEffect(state: STATE, intent: INTENT): SIDE_EFFECT? {
        return null
    }

    protected open fun postSideEffect(state: STATE, result: RESULT): SIDE_EFFECT? {
        return null
    }
}