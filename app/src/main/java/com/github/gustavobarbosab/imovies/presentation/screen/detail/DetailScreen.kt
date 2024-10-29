package com.github.gustavobarbosab.imovies.presentation.screen.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailScreen(viewModel: DetailScreenViewModel) {

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel(DetailScreenIntent.Init)
    }

    DetailScreenContent(
        screenState = screenState,
        onBackPressed = { viewModel(DetailScreenIntent.BackPressed) },
        onRetryClicked = { viewModel(DetailScreenIntent.RetryLoad) }
    )
}
