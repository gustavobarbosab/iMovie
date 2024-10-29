package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.github.gustavobarbosab.imovies.core.presentation.arch.CoreViewModel
import com.github.gustavobarbosab.imovies.core.presentation.arch.HandledByProcessor
import com.github.gustavobarbosab.imovies.domain.movies.list.GetMoviesListUseCase
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeActionResult.SectionUpdate
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeModelMapper
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    reducer: HomeScreenReducer,
    sideEffectProcessor: HomeScreenSideEffectProcessor,
    private val mapper: HomeModelMapper,
    private val upcomingMoviesUseCase: GetMoviesListUseCase.UpcomingMovies,
    private val topRatedMoviesUseCase: GetMoviesListUseCase.TopRatedMovies,
    private val popularMoviesUseCase: GetMoviesListUseCase.PopularMovies,
    private val nowPlayingMoviesUseCase: GetMoviesListUseCase.NowPlayingMovies
) : CoreViewModel<HomeScreenState, HomeIntent, HomeActionResult, HomeSideEffect>(
    reducer,
    sideEffectProcessor
) {
    private var screenInitializationJob: Job? = null

    override fun handleIntent(intent: HomeIntent, currentState: HomeScreenState) {
        when (intent) {
            HomeIntent.Init -> initScreen()
            is HomeIntent.MovieClicked -> HandledByProcessor
        }
    }

    private fun initScreen() {
        screenInitializationJob?.cancel()
        screenInitializationJob = viewModelScope.launch {
            val topBannerDeferred = async {
                getMovies(
                    request = { nowPlayingMoviesUseCase.getNowPlayingMovies() },
                    onLoading = { HomeActionResult.TopBannerUpdate(SectionUpdate.Loading) },
                    onSuccess = { HomeActionResult.TopBannerUpdate(SectionUpdate.Success(it)) },
                    onEmpty = { HomeActionResult.TopBannerUpdate(SectionUpdate.EmptyList) },
                    onFailure = { HomeActionResult.TopBannerUpdate(SectionUpdate.Failure) },
                )
            }
            val popularDeferred = async {
                getMovies(
                    request = { popularMoviesUseCase.getPopularMovies() },
                    onLoading = { HomeActionResult.UpdatePopularSection(SectionUpdate.Loading) },
                    onSuccess = { HomeActionResult.UpdatePopularSection(SectionUpdate.Success(it)) },
                    onEmpty = { HomeActionResult.UpdatePopularSection(SectionUpdate.EmptyList) },
                    onFailure = { HomeActionResult.UpdatePopularSection(SectionUpdate.Failure) },
                )
            }
            val topRatedDeferred = async {
                getMovies(
                    request = { topRatedMoviesUseCase.getTopRatedMovies() },
                    onLoading = { HomeActionResult.UpdateTopRatedSection(SectionUpdate.Loading) },
                    onSuccess = { HomeActionResult.UpdateTopRatedSection(SectionUpdate.Success(it)) },
                    onEmpty = { HomeActionResult.UpdateTopRatedSection(SectionUpdate.EmptyList) },
                    onFailure = { HomeActionResult.UpdateTopRatedSection(SectionUpdate.Failure) },
                )
            }
            val upcomingDeferred = async {
                getMovies(
                    request = { upcomingMoviesUseCase.getUpcomingMovies() },
                    onLoading = { HomeActionResult.UpdateUpcomingSection(SectionUpdate.Loading) },
                    onSuccess = { HomeActionResult.UpdateUpcomingSection(SectionUpdate.Success(it)) },
                    onEmpty = { HomeActionResult.UpdateUpcomingSection(SectionUpdate.EmptyList) },
                    onFailure = { HomeActionResult.UpdateUpcomingSection(SectionUpdate.Failure) },
                )
            }

            topBannerDeferred.await()
            popularDeferred.await()
            topRatedDeferred.await()
            upcomingDeferred.await()
        }
    }

    // I've created this method because the logic was repeated in the initScreen method
    private suspend fun getMovies(
        request: suspend () -> GetMoviesListUseCase.Result,
        onLoading: () -> HomeActionResult,
        onSuccess: (List<HomeMovieModel>) -> HomeActionResult,
        onEmpty: () -> HomeActionResult,
        onFailure: () -> HomeActionResult,
    ) {
        reduce(onLoading())

        val result = when (val result = request()) {
            is GetMoviesListUseCase.Result.Success -> onSuccess(mapper.map(result.moviePage))
            is GetMoviesListUseCase.Result.ThereIsNoMovies -> onEmpty()
            is GetMoviesListUseCase.Result.Error -> onFailure()
        }
        reduce(result)
    }
}
