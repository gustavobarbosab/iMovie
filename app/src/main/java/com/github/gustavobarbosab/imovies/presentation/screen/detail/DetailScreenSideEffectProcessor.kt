package com.github.gustavobarbosab.imovies.presentation.screen.detail

import com.github.gustavobarbosab.imovies.core.presentation.arch.SideEffectProcessor

import javax.inject.Inject

class DetailScreenSideEffectProcessor @Inject constructor(
) : SideEffectProcessor<DetailScreenState, DetailScreenIntent, DetailScreenActionResult, DetailScreenSideEffect>() {

    override fun preSideEffect(
        state: DetailScreenState,
        intent: DetailScreenIntent
    ): DetailScreenSideEffect? = when (intent) {
        is DetailScreenIntent.BackPressed -> DetailScreenSideEffect.GoBack
        else -> null
    }
}