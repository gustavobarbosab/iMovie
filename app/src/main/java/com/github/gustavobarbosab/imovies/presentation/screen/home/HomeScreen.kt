package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.gustavobarbosab.imovies.core.presentation.arch.OnSideEffect

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel(HomeIntent.Init)
    }

    OnSideEffect(viewModel.sideEffect) { homeSideEffect ->
        when (homeSideEffect) {
            is HomeSideEffect.LoadMovieFailure -> Unit // TODO show snack bar
            is HomeSideEffect.NavigateToMovieDetail -> Unit // Call details screen
        }
    }

    HomeScreenContent(
        screenState = screenState,
        onMovieClicked = { viewModel(HomeIntent.MovieClicked(it)) }
    )
}
