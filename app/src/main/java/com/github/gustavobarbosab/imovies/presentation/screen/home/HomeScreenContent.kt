package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.gustavobarbosab.imovies.presentation.screen.home.components.MovieSection
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

@Composable
fun HomeScreenContent() {
    Surface {
        Column {
            MovieSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.spacing.medium,
                        bottom = MaterialTheme.spacing.medium,
                        start = MaterialTheme.spacing.medium,
                    ),
                title = "Popular Movies",
                movies = listOf(
                    HomeMovieModel(
                        1,
                        "Movie 1",
                        "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
                    ),
                    HomeMovieModel(
                        2,
                        "Movie 2",
                        "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
                    ),
                    HomeMovieModel(
                        3,
                        "Movie 2",
                        "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
                    ),
                    HomeMovieModel(
                        4,
                        "Movie 2",
                        "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
                    ),
                    HomeMovieModel(
                        5,
                        "Movie 2",
                        "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
                    ), HomeMovieModel(
                        6,
                        "Movie 2",
                        "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
                    )
                ),
                onMovieClicked = {}
            )
        }
    }
}