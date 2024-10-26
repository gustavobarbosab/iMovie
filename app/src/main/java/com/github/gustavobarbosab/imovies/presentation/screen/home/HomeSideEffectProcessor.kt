package com.github.gustavobarbosab.imovies.presentation.screen.home

import com.github.gustavobarbosab.imovies.core.presentation.arch.SideEffectProcessor
import javax.inject.Inject

class HomeSideEffectProcessor @Inject constructor() :
    SideEffectProcessor<HomeScreenState, HomeIntent, HomeResult, HomeSideEffect>() {

    override fun preSideEffect(
        state: HomeScreenState,
        intent: HomeIntent
    ): HomeSideEffect? = when (intent) {
        is HomeIntent.MovieClicked -> HomeSideEffect.NavigateToMovieDetail(intent.movie)
        else -> null
    }

    override fun postSideEffect(
        state: HomeScreenState,
        result: HomeResult
    ): HomeSideEffect? = when (result) {
        is HomeResult.TopBannerLoadFailure -> HomeSideEffect.LoadTopBannerFailure
        is HomeResult.PopularMoviesLoadFailure -> HomeSideEffect.LoadPopularFailure
        is HomeResult.TopRatedMoviesLoadFailure -> HomeSideEffect.LoadTopRatedFailure
        is HomeResult.UpcomingMoviesLoadFailure -> HomeSideEffect.LoadUpcomingFailure
        else -> null
    }
}