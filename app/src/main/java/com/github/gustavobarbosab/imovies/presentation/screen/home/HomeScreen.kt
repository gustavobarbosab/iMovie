package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel(HomeIntent.Init)
    }

    HomeScreenContent(
        screenState = screenState,
        onMovieClicked = { viewModel(HomeIntent.Init) }
    )
}
