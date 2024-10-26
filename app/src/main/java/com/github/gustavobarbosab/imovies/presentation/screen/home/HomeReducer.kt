package com.github.gustavobarbosab.imovies.presentation.screen.home

import com.github.gustavobarbosab.imovies.core.presentation.arch.Reducer
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeScreenState.MovieSectionState
import javax.inject.Inject

class HomeReducer @Inject constructor() : Reducer<HomeScreenState, HomeResult>(
    HomeScreenState.initialState()
) {
    override fun reduce(result: HomeResult, currentState: HomeScreenState): HomeScreenState =
        when (result) {
            is HomeResult.DoNothing -> currentState

            // region Top Banner
            HomeResult.LoadingTopBanner -> currentState.copy(
                topBannerMovies = MovieSectionState.Loading
            )

            is HomeResult.ShowTopBannerMovies -> currentState.copy(
                topBannerMovies = MovieSectionState.ShowMovies(result.movies)
            )

            HomeResult.TopBannerLoadFailure -> currentState.copy(
                topBannerMovies = MovieSectionState.LoadFailure
            )
            // endregion

            // region Top Rated
            HomeResult.LoadingTopRatedMovies -> currentState.copy(
                topRatedMovies = MovieSectionState.Loading
            )

            is HomeResult.ShowTopRatedMovies -> currentState.copy(
                topRatedMovies = MovieSectionState.ShowMovies(result.movies)
            )

            HomeResult.TopRatedMoviesLoadFailure -> currentState.copy(
                topRatedMovies = MovieSectionState.LoadFailure
            )
            // endregion

            // region Popular
            HomeResult.LoadingPopularMovies -> currentState.copy(
                popularMovies = MovieSectionState.Loading
            )

            is HomeResult.ShowPopularMovies -> currentState.copy(
                popularMovies = MovieSectionState.ShowMovies(result.movies)
            )

            HomeResult.PopularMoviesLoadFailure -> currentState.copy(
                popularMovies = MovieSectionState.LoadFailure
            )
            // endregion

            // region Upcoming
            HomeResult.LoadingUpcomingMovies -> currentState.copy(
                upcomingMovies = MovieSectionState.Loading
            )

            is HomeResult.ShowUpcomingMovies -> currentState.copy(
                upcomingMovies = MovieSectionState.ShowMovies(result.movies)
            )

            HomeResult.UpcomingMoviesLoadFailure -> currentState.copy(
                upcomingMovies = MovieSectionState.LoadFailure
            )
            // endregion
        }
}