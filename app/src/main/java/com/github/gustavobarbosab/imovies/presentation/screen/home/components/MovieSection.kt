package com.github.gustavobarbosab.imovies.presentation.screen.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gustavobarbosab.imovies.common.presentation.UiStateList
import com.github.gustavobarbosab.imovies.common.presentation.compose.extension.shimmerEffect
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeScreenState
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

private val MOVIE_CARD_HEIGHT = 200.dp
private const val SKELETON_ITEMS = 7

@Composable
fun MovieSection(
    modifier: Modifier = Modifier,
    sectionState: HomeScreenState.MovieSectionState,
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    if (sectionState.uiState is UiStateList.EmptyList) {
        // I chose to not show the section if it is empty
        return
    }

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium.copy(
            topEnd = ZeroCornerSize,
            bottomEnd = ZeroCornerSize
        ),
    ) {
        Column(Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                text = stringResource(sectionState.title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacing.medium)
            )

            when (val uiState = sectionState.uiState) {
                UiStateList.Loading -> MovieSectionSkeleton()

                is UiStateList.Success -> MovieList(
                    movies = uiState.data,
                    onMovieClicked = onMovieClicked
                )

                // TODO implement the error...
                else -> Unit
            }
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
                    .sizeIn(maxHeight = MOVIE_CARD_HEIGHT)
                    .clip(
                        MaterialTheme.shapes.medium
                    ),
                imagePath = movie.posterPath,
                onClick = { onMovieClicked(movie) }
            )
        }
    }
}


@Composable
fun MovieSectionSkeleton() {
    LazyRow(
        contentPadding = PaddingValues(start = MaterialTheme.spacing.small),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        items(count = SKELETON_ITEMS, key = { index -> index }) {
            Spacer(
                modifier = Modifier
                    .size(height = MOVIE_CARD_HEIGHT, width = 140.dp)
                    .shimmerEffect()
                    .padding(MaterialTheme.spacing.small)
            )
        }
    }
}

@Preview
@Composable
private fun previewMovieSection() {

}