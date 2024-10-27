package com.github.gustavobarbosab.imovies.presentation.screen.home

import com.github.gustavobarbosab.imovies.core.presentation.arch.CoreViewModel
import com.github.gustavobarbosab.imovies.domain.movies.upcoming.UpcomingMoviesUseCase
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    reducer: HomeReducer,
    sideEffectProcessor: HomeSideEffectProcessor,
    private val mapper: HomeModelMapper,
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
        emitAll(getUpcomingMovies())
    }

    private fun getUpcomingMovies(): Flow<HomeResult> = flow {
        emit(HomeResult.LoadingUpcomingMovies)
        when (val result = upcomingMoviesUseCase.getUpcomingMovies(1)) {
            is UpcomingMoviesUseCase.Result.Success -> emit(
                HomeResult.ShowUpcomingMovies(
                    mapper.map(
                        result.moviePage
                    )
                )
            )

            is UpcomingMoviesUseCase.Result.ThereIsNoMovies -> emit(
                HomeResult.ShowUpcomingMovies(
                    emptyList()
                )
            )

            is UpcomingMoviesUseCase.Result.InternalError,
            is UpcomingMoviesUseCase.Result.ExternalError -> emit(HomeResult.UpcomingMoviesLoadFailure)
        }
    }
}
