package com.github.gustavobarbosab.imovies.common.presentation

sealed class UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>()
    data object Loading : UiState<Nothing>()
    data class Failure(val error: String? = null) : UiState<Nothing>()
}