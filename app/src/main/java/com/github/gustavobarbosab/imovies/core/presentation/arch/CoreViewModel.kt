package com.github.gustavobarbosab.imovies.core.presentation.arch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex

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

    private val concurrencyMutex = Mutex()

    operator fun invoke(intent: INTENT) {
        val currentState = reducer.screenState.value
        processor?.preProcessing(state = currentState, intent = intent)
        handleIntent(intent)
    }

    protected fun reduce(result: RESULT) {
        if (concurrencyMutex.tryLock().not()) {
            // TODO here we can include a log
            return
        }

        try {
            reducer(result)
            processor?.postProcessing(state = screenState.value, result = result)
        } finally {
            concurrencyMutex.unlock()
        }
    }

    abstract fun handleIntent(intent: INTENT)
}