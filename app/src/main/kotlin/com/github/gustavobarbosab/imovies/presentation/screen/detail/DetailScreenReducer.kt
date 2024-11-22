package com.github.gustavobarbosab.imovies.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.github.gustavobarbosab.imovies.common.ui.UiState
import com.github.gustavobarbosab.imovies.core.presentation.arch.Reducer
import javax.inject.Inject

class DetailScreenReducer @Inject constructor(
    initialState: DetailScreenState
) : Reducer<DetailScreenState, DetailScreenActionResult>(initialState) {

    override fun reduce(
        result: DetailScreenActionResult,
        currentState: DetailScreenState
    ): DetailScreenState = when (result) {
        is DetailScreenActionResult.Loading -> currentState.copy(
            uiState = UiState.Loading
        )

        is DetailScreenActionResult.Success -> currentState.copy(
            uiState = UiState.Success(result.model)
        )

        is DetailScreenActionResult.Failure -> currentState.copy(
            uiState = UiState.Failure()
        )
    }


    class Factory @Inject constructor() {
        fun create(stateHandle: SavedStateHandle): DetailScreenReducer {
            val route = stateHandle.toRoute<DetailRoute>()
            checkNotNull(route) { "route must not be null" }
            checkNotNull(route.movieId) { "movieId must not be null" }
            val initialState = DetailScreenState.initialState(route.movieId)
            return DetailScreenReducer(initialState)
        }
    }
}