package com.github.gustavobarbosab.imovies.presentation.screen.home

import com.github.gustavobarbosab.imovies.core.presentation.arch.CoreViewModel
import com.github.gustavobarbosab.imovies.domain.movies.upcoming.UpcomingMoviesUseCase
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    reducer: HomeReducer,
    sideEffectProcessor: HomeSideEffectProcessor,
    private val upcomingMoviesUseCase: UpcomingMoviesUseCase
) : CoreViewModel<HomeScreenState, HomeIntent, HomeResult, HomeSideEffect>(
    reducer,
    sideEffectProcessor
) {
    override fun handleIntent(intent: HomeIntent): Flow<HomeResult> =
        when (intent) {
            HomeIntent.Init -> initScreen()
            is HomeIntent.MovieClicked -> flowOf(
                HomeResult.DoNothing(internalReason = "Event handled by the pre processor")
            )
        }

    private fun initScreen(): Flow<HomeResult> = flow {
        val mockedMovies = mutableListOf<HomeMovieModel>()
        repeat(10) { index ->
            mockedMovies.add(
                HomeMovieModel(
                    id = index.toLong(),
                    title = "Movie $index",
                    posterUrl = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
                )
            )
        }
        emit(
            HomeResult.ShowTopBannerMovies(
                mockedMovies.map {
                    it.copy(posterUrl = "https://image.tmdb.org/t/p/w1280/7h6TqPB3ESmjuVbxCxAeB1c9OB1.jpg")
                }
            )
        )
        emit(HomeResult.ShowPopularMovies(mockedMovies))
        emit(HomeResult.ShowUpcomingMovies(mockedMovies))
        emit(HomeResult.ShowTopRatedMovies(mockedMovies))
    }
}
