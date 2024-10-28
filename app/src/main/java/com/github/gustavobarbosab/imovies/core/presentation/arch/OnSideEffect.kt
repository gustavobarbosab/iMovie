package com.github.gustavobarbosab.imovies.core.presentation.arch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> OnSideEffect(
    sideEffectFlow: Flow<T>,
    onSideEffect: (T) -> Unit
) {
    LaunchedEffect(Unit) {
        sideEffectFlow.collect { sideEffectValue ->
            onSideEffect(sideEffectValue)
        }
    }
}