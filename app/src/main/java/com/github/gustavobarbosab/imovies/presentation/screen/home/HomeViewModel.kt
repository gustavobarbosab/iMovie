package com.github.gustavobarbosab.imovies.presentation.screen.home

import com.github.gustavobarbosab.imovies.core.presentation.arch.CoreViewModel
import com.github.gustavobarbosab.imovies.domain.movies.GetMoviesUseCaseContract
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
    private val upcomingMoviesUseCase: GetMoviesUseCaseContract.UpcomingMoviesUseCase,
    private val topRatedMoviesUseCase: GetMoviesUseCaseContract.TopRatedMoviesUseCase,
    private val popularMoviesUseCase: GetMoviesUseCaseContract.PopularMoviesUseCase,
    private val nowPlayingMoviesUseCase: GetMoviesUseCaseContract.NowPlayingMoviesUseCase
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
            getMovies(HomeActionResult.Section.TopBanner) { nowPlayingMoviesUseCase.getNowPlayingMovies(1) }
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
        request: suspend () -> GetMoviesUseCaseContract.Result
    ): Flow<HomeActionResult> = flow {
        emit(HomeActionResult.UpdateSection(section, SectionUpdate.Loading))

        val result = when (val result = request()) {
            is GetMoviesUseCaseContract.Result.Success ->
                HomeActionResult.UpdateSection(
                    section,
                    SectionUpdate.Success(mapper.map(result.moviePage))
                )

            is GetMoviesUseCaseContract.Result.ThereIsNoMovies ->
                HomeActionResult.UpdateSection(section, SectionUpdate.Success(emptyList()))

            is GetMoviesUseCaseContract.Result.Error ->
                HomeActionResult.UpdateSection(section, SectionUpdate.Failure)
        }
        emit(result)
    }
}
