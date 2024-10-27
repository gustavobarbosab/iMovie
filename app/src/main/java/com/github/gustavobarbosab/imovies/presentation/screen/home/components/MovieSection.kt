package com.github.gustavobarbosab.imovies.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gustavobarbosab.imovies.common.presentation.UiState
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

@Composable
fun MovieSection(
    modifier: Modifier = Modifier,
    title: String,
    sectionState: UiState<List<HomeMovieModel>>,
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacing.medium)
        )

        when (sectionState) {
            is UiState.Failure -> Column {
                // TODO show a retry button
            }

            UiState.Loading -> Column {
                // TODO show a loading skeleton
            }

            is UiState.Success -> MovieList(
                movies = sectionState.data,
                onMovieClicked = onMovieClicked
            )
        }
    }
}

@Composable
fun MovieList(
    movies: List<HomeMovieModel>,
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(start = MaterialTheme.spacing.small),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        items(items = movies, key = { movie -> movie.id }) { movie ->
            MovieCard(
                modifier = Modifier
                    .sizeIn(maxHeight = 200.dp)
                    .clip(RoundedCornerShape(MaterialTheme.spacing.small)),
                bannerUrl = movie.posterPath,
                onClick = { onMovieClicked(movie) }
            )
        }
    }
}

@Preview
@Composable
private fun previewMovieSection() {
    MovieSection(
        title = "Popular Movies",
        sectionState = UiState.Success(
            listOf(
                HomeMovieModel(
                    1,
                    "Movie 1",
                    "https://cdn.watchmode.com/posters/03165490_poster_w185.jpg",
                    "https://cdn.watchmode.com/posters/03165490_poster_w185.jpg",
                ),
                HomeMovieModel(
                    2,
                    "Movie 2",
                    "https://cdn.watchmode.com/posters/03175997_poster_w185.jpg",
                    "https://cdn.watchmode.com/posters/03175997_poster_w185.jpg"
                )
            )
        ),
        onMovieClicked = {}
    )
}