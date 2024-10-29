package com.github.gustavobarbosab.imovies.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.gustavobarbosab.imovies.core.presentation.arch.CoreViewModel
import com.github.gustavobarbosab.imovies.core.presentation.arch.HandledByProcessor
import com.github.gustavobarbosab.imovies.domain.movies.detail.GetMovieDetailUseCase
import com.github.gustavobarbosab.imovies.presentation.screen.detail.model.DetailScreenMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    reducerFactory: DetailScreenReducer.Factory,
    private val mapper: DetailScreenMapper,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : CoreViewModel<DetailScreenState, DetailScreenIntent, DetailScreenActionResult, Nothing>(
    reducerFactory.create(savedStateHandle["DETAIL_MOVIE_ID"])
) {

    override fun handleIntent(
        intent: DetailScreenIntent,
        currentState: DetailScreenState
    ) {
        when (intent) {
            DetailScreenIntent.Init,
            DetailScreenIntent.RetryLoad -> initScreen(currentState)

            DetailScreenIntent.BackPressed -> HandledByProcessor
        }
    }

    private fun initScreen(currentState: DetailScreenState) {
        viewModelScope.launch {
            reduce(DetailScreenActionResult.Loading)
            getMovieDetailUseCase.getMovieDetail(currentState.movieId).fold(
                onSuccess = { movie ->
                    reduce(DetailScreenActionResult.Success(mapper.mapToModel(movie)))
                },
                onFailure = { reduce(DetailScreenActionResult.Failure) }
            )
        }
    }
}