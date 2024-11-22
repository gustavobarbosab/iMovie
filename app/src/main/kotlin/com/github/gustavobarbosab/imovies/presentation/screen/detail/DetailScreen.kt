package com.github.gustavobarbosab.imovies.presentation.screen.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.github.gustavobarbosab.imovies.core.presentation.arch.OnSideEffect
import com.github.gustavobarbosab.imovies.core.presentation.routes.IMovieRoute
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailRoute(
    @SerialName("movie_id") val movieId: Long
) : IMovieRoute

@Composable
fun DetailScreen(
    viewModel: DetailScreenViewModel,
    navController: NavController
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    OnSideEffect(viewModel.sideEffect) { detailSideEffect ->
        when (detailSideEffect) {
            DetailScreenSideEffect.GoBack -> {
                navController.popBackStack()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel(DetailScreenIntent.Init)
    }

    DetailScreenContent(
        screenState = screenState,
        onBackPressed = { viewModel(DetailScreenIntent.BackPressed) },
        onRetryClicked = { viewModel(DetailScreenIntent.RetryLoad) }
    )
}
