package com.github.gustavobarbosab.imovies.presentation.screen.home

import com.github.gustavobarbosab.imovies.core.presentation.arch.CoreViewModel
import com.github.gustavobarbosab.imovies.domain.movies.GetMoviesUseCase
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeActionResult.SectionUpdate
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
    private val upcomingMoviesUseCase: GetMoviesUseCase.UpcomingMovies,
    private val topRatedMoviesUseCase: GetMoviesUseCase.TopRatedMovies,
    private val popularMoviesUseCase: GetMoviesUseCase.PopularMovies,
    private val nowPlayingMoviesUseCase: GetMoviesUseCase.NowPlayingMovies
) : CoreViewModel<HomeScreenState, HomeIntent, HomeActionResult, HomeSideEffect>(
    reducer,
    sideEffectProcessor
) {
    override fun handleIntent(intent: HomeIntent): Flow<HomeActionResult> =
        when (intent) {
            HomeIntent.Init -> initScreen()
            is HomeIntent.MovieClicked -> flowOf(
                HomeActionResult.DoNothing(internalReason = "Event handled by the pre processor")
            )
        }

    private fun initScreen(): Flow<HomeActionResult> = flow {
        emitAll(
            getMovies(HomeActionResult.Section.TopBanner) {
                nowPlayingMoviesUseCase.getNowPlayingMovies(
                    1
                )
            }
        )

        emitAll(
            getMovies(HomeActionResult.Section.Popular) { popularMoviesUseCase.getPopularMovies(1) }
        )

        emitAll(
            getMovies(HomeActionResult.Section.TopRated) { topRatedMoviesUseCase.getTopRatedMovies(1) }
        )

        emitAll(
            getMovies(HomeActionResult.Section.Upcoming) { upcomingMoviesUseCase.getUpcomingMovies(1) }
        )
    }

    private fun getMovies(
        section: HomeActionResult.Section,
        request: suspend () -> GetMoviesUseCase.Result
    ): Flow<HomeActionResult> = flow {
        emit(HomeActionResult.UpdateSection(section, SectionUpdate.Loading))

        val result = when (val result = request()) {
            is GetMoviesUseCase.Result.Success ->
                HomeActionResult.UpdateSection(
                    section,
                    SectionUpdate.Success(mapper.map(result.moviePage))
                )

            is GetMoviesUseCase.Result.ThereIsNoMovies ->
                HomeActionResult.UpdateSection(section, SectionUpdate.Success(emptyList()))

            is GetMoviesUseCase.Result.Error ->
                HomeActionResult.UpdateSection(section, SectionUpdate.Failure)
        }
        emit(result)
    }
}
