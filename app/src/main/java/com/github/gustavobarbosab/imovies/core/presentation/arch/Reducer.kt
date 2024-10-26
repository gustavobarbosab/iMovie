package com.github.gustavobarbosab.imovies.core.presentation.arch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Reducer<STATE, RESULT>(initialState: STATE) {

    private val _screenState = MutableStateFlow(initialState)
    val screenState: StateFlow<STATE> = _screenState.asStateFlow()

    operator fun invoke(result: RESULT) {
        val currentState = _screenState.value
        val newState = reduce(result, currentState)
        _screenState.value = newState
    }

    abstract fun reduce(result: RESULT, currentState: STATE): STATE
}
