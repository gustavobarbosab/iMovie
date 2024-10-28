package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.github.gustavobarbosab.imovies.core.presentation.arch.CoreViewModel
import com.github.gustavobarbosab.imovies.core.presentation.arch.HandledByProcessor
import com.github.gustavobarbosab.imovies.domain.movies.GetMoviesUseCase
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeActionResult.SectionUpdate
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
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
    private var screenInitializationJob: Job? = null

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Init -> initScreen()
            is HomeIntent.MovieClicked -> HandledByProcessor
        }
    }

    private fun initScreen() {
        screenInitializationJob?.cancel()
        screenInitializationJob = viewModelScope.launch {
            val topBannerDeferred = async {
                getMovies(HomeActionResult.Section.TopBanner) {
                    nowPlayingMoviesUseCase.getNowPlayingMovies(1)
                }
            }
            val popularDeferred = async {
                getMovies(HomeActionResult.Section.Popular) {
                    popularMoviesUseCase.getPopularMovies(1)
                }
            }
            val topRatedDeferred = async {
                getMovies(HomeActionResult.Section.TopRated) {
                    topRatedMoviesUseCase.getTopRatedMovies(1)
                }
            }
            val upcomingDeferred = async {
                getMovies(HomeActionResult.Section.Upcoming) {
                    upcomingMoviesUseCase.getUpcomingMovies(1)
                }
            }

            topBannerDeferred.await()
            popularDeferred.await()
            topRatedDeferred.await()
            upcomingDeferred.await()
        }
    }

    private suspend fun getMovies(
        section: HomeActionResult.Section,
        request: suspend () -> GetMoviesUseCase.Result
    ) {
        reduce(HomeActionResult.UpdateSection(section, SectionUpdate.Loading))

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
        reduce(result)
    }
}
