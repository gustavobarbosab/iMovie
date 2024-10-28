package com.github.gustavobarbosab.imovies.common.presentation

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data object Loading : UiState<Nothing>
    data class Failure(val error: String? = null) : UiState<Nothing>
}

sealed interface UiStateList<out T> : UiState<List<T>> {
    data class Success<T>(val data: List<T>) : UiStateList<T>
    data object Loading : UiStateList<Nothing>
    data class Failure(val error: String? = null) : UiStateList<Nothing>
    data object EmptyList : UiStateList<Nothing>
}
