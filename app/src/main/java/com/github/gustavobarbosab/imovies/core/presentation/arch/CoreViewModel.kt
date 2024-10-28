package com.github.gustavobarbosab.imovies.core.presentation.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

typealias HandledByProcessor = Unit

abstract class CoreViewModel<STATE, INTENT, RESULT, SIDE_EFFECT>(
    private val reducer: Reducer<STATE, RESULT>,
    private val processor: SideEffectProcessor<STATE, INTENT, RESULT, SIDE_EFFECT>? = null
) : ViewModel() {

    val screenState: StateFlow<STATE>
        get() = reducer.screenState

    val sideEffect: Flow<SIDE_EFFECT>
        get() = processor?.sideEffect
            ?: throw IllegalStateException("Side effect processor not implemented")

    private val resultChannel = Channel<RESULT>()

    init {
        // This approach was created to control the concurrency of the results
        viewModelScope.launch {
            resultChannel.receiveAsFlow().collect { result ->
                reducer(result)
                processor?.postProcessing(state = screenState.value, result = result)
            }
        }
    }

    operator fun invoke(intent: INTENT) {
        val currentState = reducer.screenState.value
        processor?.preProcessing(state = currentState, intent = intent)
        handleIntent(intent)
    }

    protected fun reduce(result: RESULT) {
        viewModelScope.launch {
            resultChannel.send(result)
        }
    }

    abstract fun handleIntent(intent: INTENT)
}