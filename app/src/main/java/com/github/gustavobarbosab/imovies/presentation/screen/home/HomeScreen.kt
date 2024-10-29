package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.github.gustavobarbosab.imovies.core.presentation.arch.OnSideEffect
import com.github.gustavobarbosab.imovies.core.presentation.routes.IMovieRoute
import com.github.gustavobarbosab.imovies.presentation.screen.detail.DetailRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : IMovieRoute

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navController: NavController
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel(HomeIntent.Init)
    }

    OnSideEffect(viewModel.sideEffect) { homeSideEffect ->
        when (homeSideEffect) {
            is HomeSideEffect.LoadMovieFailure -> Unit // TODO show snack bar
            is HomeSideEffect.NavigateToMovieDetail -> {
                navController.navigate(DetailRoute(homeSideEffect.movieId))
            }
        }
    }

    HomeScreenContent(
        screenState = screenState,
        onMovieClicked = { viewModel(HomeIntent.MovieClicked(it)) }
    )
}
