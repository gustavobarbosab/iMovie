package com.github.gustavobarbosab.imovies.presentation.screen.home

import com.github.gustavobarbosab.imovies.core.presentation.arch.SideEffectProcessor
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieSectionType
import javax.inject.Inject

class HomeScreenSideEffectProcessor @Inject constructor() :
    SideEffectProcessor<HomeScreenState, HomeIntent, HomeActionResult, HomeSideEffect>() {

    override fun preSideEffect(
        state: HomeScreenState,
        intent: HomeIntent
    ): HomeSideEffect? = when (intent) {
        is HomeIntent.MovieClicked -> HomeSideEffect.NavigateToMovieDetail(intent.movie.id)
        else -> null
    }

    override fun postSideEffect(
        state: HomeScreenState,
        result: HomeActionResult
    ): HomeSideEffect? = when (result) {
        is HomeActionResult.UpdatePopularSection -> onFailureToGetMovies(
            result.update,
            HomeMovieSectionType.POPULAR
        )

        is HomeActionResult.UpdateTopRatedSection -> onFailureToGetMovies(
            result.update,
            HomeMovieSectionType.TOP_RATED
        )

        is HomeActionResult.UpdateUpcomingSection -> onFailureToGetMovies(
            result.update,
            HomeMovieSectionType.UPCOMING
        )

        else -> null
    }

    private fun onFailureToGetMovies(
        update: HomeActionResult.SectionUpdate,
        sectionType: HomeMovieSectionType
    ) = if (update is HomeActionResult.SectionUpdate.Failure) {
        HomeSideEffect.LoadMovieFailure(sectionType)
    } else {
        null
    }
}