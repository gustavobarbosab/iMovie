package com.github.gustavobarbosab.imovies.presentation.screen.home

import com.github.gustavobarbosab.imovies.common.ui.UiStateList
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieSectionType

object HomeScreenStubFactory {

    fun generateStubMovieList(size: Int = 10): List<HomeMovieModel> {
        val stubMovies = mutableListOf<HomeMovieModel>()
        repeat(size) { position ->
            stubMovies.add(
                HomeMovieModel(
                    id = position.toLong(),
                    title = "Movie $position",
                    backdropPath = "https://image.tmdb.org/t/p/w1280/3V4kLQg0kSqPLctI5ziYWabAZYF.jpg",
                    posterPath = "https://image.tmdb.org/t/p/w500/aosm8NMQ3UyoBVpSxyimorCQykC.jpg"
                )
            )
        }
        return stubMovies
    }

    fun generateSuccessScreenState(
        movies: List<HomeMovieModel>,
        sections: Map<HomeMovieSectionType, Int>,
    ) = HomeScreenState.initialState().copy(
        topBannerMovies = UiStateList.Success(movies),
        movieSectionMap = sections.map { (type, title) ->
            type to HomeScreenState.MovieSectionState(
                sectionType = type,
                title = title,
                uiState = UiStateList.Success(movies)
            )
        }.toMap()
    )

    fun generateFailureScreenState(
        sections: Map<HomeMovieSectionType, Int>,
    ) = HomeScreenState.initialState().copy(
        topBannerMovies = UiStateList.Failure(),
        movieSectionMap = sections.map { (type, title) ->
            type to HomeScreenState.MovieSectionState(
                sectionType = type,
                title = title,
                uiState = UiStateList.Failure()
            )
        }.toMap()
    )
}