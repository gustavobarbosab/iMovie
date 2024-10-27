package com.github.gustavobarbosab.imovies.presentation.screen.home

import com.github.gustavobarbosab.imovies.core.presentation.arch.SideEffectProcessor
import javax.inject.Inject

class HomeSideEffectProcessor @Inject constructor() :
    SideEffectProcessor<HomeScreenState, HomeIntent, HomeActionResult, HomeSideEffect>() {

    override fun preSideEffect(
        state: HomeScreenState,
        intent: HomeIntent
    ): HomeSideEffect? = when (intent) {
        is HomeIntent.MovieClicked -> HomeSideEffect.NavigateToMovieDetail(intent.movie)
        else -> null
    }

    override fun postSideEffect(
        state: HomeScreenState,
        result: HomeActionResult
    ): HomeSideEffect? = when (result) {
        is HomeActionResult.UpdateSection -> when (result.section) {
            HomeActionResult.Section.Popular -> HomeSideEffect.LoadPopularFailure
            HomeActionResult.Section.TopBanner -> HomeSideEffect.LoadTopBannerFailure
            HomeActionResult.Section.TopRated -> HomeSideEffect.LoadTopRatedFailure
            HomeActionResult.Section.Upcoming -> HomeSideEffect.LoadUpcomingFailure
        }

        else -> null
    }
}