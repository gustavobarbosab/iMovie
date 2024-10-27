package com.github.gustavobarbosab.imovies.core.presentation.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class CoreViewModel<STATE, INTENT, RESULT, SIDE_EFFECT>(
    private val reducer: Reducer<STATE, RESULT>,
    private val processor: SideEffectProcessor<STATE, INTENT, RESULT, SIDE_EFFECT>? = null
) : ViewModel() {

    val screenState: StateFlow<STATE>
        get() = reducer.screenState

    val sideEffect: Flow<SIDE_EFFECT>
        get() = processor?.sideEffect
            ?: throw IllegalStateException("Side effect processor not implemented")

    operator fun invoke(intent: INTENT) {
        val currentState = reducer.screenState.value
        processor?.preProcessing(state = currentState, intent = intent)
        handleIntent(intent = intent)
            .onEach { result ->
                reducer(result)
                processor?.postProcessing(state = currentState, result = result)
            }
            .launchIn(viewModelScope)
    }

    abstract fun handleIntent(intent: INTENT): Flow<RESULT>
}