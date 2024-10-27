package com.github.gustavobarbosab.imovies.presentation.screen.home

import com.github.gustavobarbosab.imovies.common.presentation.UiState
import com.github.gustavobarbosab.imovies.core.presentation.arch.Reducer
import javax.inject.Inject

class HomeReducer @Inject constructor() : Reducer<HomeScreenState, HomeActionResult>(
    HomeScreenState.initialState()
) {
    override fun reduce(result: HomeActionResult, currentState: HomeScreenState): HomeScreenState =
        when (result) {
            is HomeActionResult.DoNothing -> currentState
            is HomeActionResult.UpdateSection -> reduceUpdateSection(result, currentState)
        }

    private fun reduceUpdateSection(
        result: HomeActionResult.UpdateSection,
        currentState: HomeScreenState
    ): HomeScreenState {
        return when (result.update) {
            is HomeActionResult.SectionUpdate.Loading -> result.section.reduce(
                onTopBanner = { currentState.copy(topBannerMovies = UiState.Loading) },
                onPopular = { currentState.copy(popularMovies = UiState.Loading) },
                onTopRated = { currentState.copy(topRatedMovies = UiState.Loading) },
                onUpcoming = { currentState.copy(upcomingMovies = UiState.Loading) }
            )

            is HomeActionResult.SectionUpdate.Failure -> result.section.reduce(
                onTopBanner = { currentState.copy(topBannerMovies = UiState.Failure()) },
                onPopular = { currentState.copy(popularMovies = UiState.Failure()) },
                onTopRated = { currentState.copy(topRatedMovies = UiState.Failure()) },
                onUpcoming = { currentState.copy(upcomingMovies = UiState.Failure()) }
            )

            is HomeActionResult.SectionUpdate.Success -> result.section.reduce(
                onTopBanner = { currentState.copy(topBannerMovies = UiState.Success(result.update.movies)) },
                onPopular = { currentState.copy(popularMovies = UiState.Success(result.update.movies)) },
                onTopRated = { currentState.copy(topRatedMovies = UiState.Success(result.update.movies)) },
                onUpcoming = { currentState.copy(upcomingMovies = UiState.Success(result.update.movies)) }
            )
        }
    }

    private fun HomeActionResult.Section.reduce(
        onTopBanner: () -> HomeScreenState,
        onPopular: () -> HomeScreenState,
        onTopRated: () -> HomeScreenState,
        onUpcoming: () -> HomeScreenState
    ) =
        when (this) {
            HomeActionResult.Section.TopBanner -> onTopBanner()
            HomeActionResult.Section.Popular -> onPopular()
            HomeActionResult.Section.TopRated -> onTopRated()
            HomeActionResult.Section.Upcoming -> onUpcoming()
        }
}
