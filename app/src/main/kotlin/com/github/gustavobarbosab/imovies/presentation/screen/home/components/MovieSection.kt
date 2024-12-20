package com.github.gustavobarbosab.imovies.presentation.screen.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gustavobarbosab.imovies.R
import com.github.gustavobarbosab.imovies.common.ui.UiStateList
import com.github.gustavobarbosab.imovies.common.ui.compose.component.FeedbackContainer
import com.github.gustavobarbosab.imovies.common.ui.compose.component.MovieCard
import com.github.gustavobarbosab.imovies.common.ui.compose.component.SkeletonItem
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeScreenState
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieSectionType
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

private val MOVIE_CARD_HEIGHT = 240.dp
private val MOVIE_CARD_WIDTH = 165.dp
private const val SKELETON_COUNT = 7

@Composable
fun MovieSection(
    modifier: Modifier = Modifier,
    sectionState: HomeScreenState.MovieSectionState,
    onRetry: () -> Unit,
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    if (sectionState.uiState is UiStateList.EmptyList) {
        // To simplify the implementation we are not handling these states
        return
    }
    val context = LocalContext.current

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium.copy(
            topEnd = ZeroCornerSize,
            bottomEnd = ZeroCornerSize
        ),
    ) {
        Column(
            Modifier
                .padding(MaterialTheme.spacing.medium)
                .testTag(sectionState.sectionType.title)
        ) {
            Text(
                modifier = Modifier.semantics {
                    contentDescription = context.getString(
                        R.string.home_movie_section_title_content_description,
                        sectionState.sectionType.title
                    )
                },
                text = stringResource(sectionState.title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacing.medium)
            )
            LazyRow(
                modifier = Modifier.semantics {
                    contentDescription = context.getString(
                        R.string.home_movie_section_list_content_description,
                        sectionState.sectionType.title
                    )
                },
                contentPadding = PaddingValues(start = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                when (val uiState = sectionState.uiState) {
                    UiStateList.Loading -> SkeletonItems()

                    is UiStateList.Success -> MovieListItems(
                        sectionType = sectionState.sectionType,
                        movies = uiState.data,
                        onMovieClicked = onMovieClicked
                    )

                    is UiStateList.Failure -> ErrorItem(
                        Modifier.semantics {
                            contentDescription = context.getString(
                                R.string.home_movie_section_error_content_description,
                                sectionState.sectionType.title
                            )
                        },
                        onRetry
                    )

                    else -> Unit
                }
            }
        }
    }
}

private fun LazyListScope.MovieListItems(
    sectionType: HomeMovieSectionType,
    movies: List<HomeMovieModel>,
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    items(items = movies, key = { movie -> movie.id }) { movie ->
        val context = LocalContext.current
        MovieCard(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .sizeIn(minHeight = MOVIE_CARD_HEIGHT, minWidth = MOVIE_CARD_WIDTH)
                .semantics {
                    contentDescription = context.getString(
                        R.string.home_movie_card_content_description,
                        movie.title,
                        sectionType.title
                    )
                },
            imagePath = movie.posterPath,
            onClick = { onMovieClicked(movie) }
        )
    }
}


private fun LazyListScope.SkeletonItems() {
    items(count = SKELETON_COUNT, key = { index -> index }) {
        SkeletonItem(
            modifier = Modifier
                .size(height = MOVIE_CARD_HEIGHT, width = MOVIE_CARD_WIDTH)
                .clip(MaterialTheme.shapes.medium)
        )
    }
}

private fun LazyListScope.ErrorItem(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    item("error") {
        FeedbackContainer(
            modifier = modifier
                .padding(MaterialTheme.spacing.medium)
                .semantics {
                    contentDescription = "Movie Section Error"
                }
                .fillMaxWidth(),
            onRetry = onRetry,
            message = stringResource(R.string.home_section_failure)
        )
    }
}

@Preview
@Composable
private fun previewMovieSection() {
    val list = mutableListOf<HomeMovieModel>()
    repeat(10) {
        list.add(
            HomeMovieModel(
                id = it.toLong(),
                title = "Movie Title",
                posterPath = "https://image.tmdb.org/t/p/w500/8bRIfStfRw6dVF2E7q5L5qRf4GJ.jpg",
                backdropPath = "https://image.tmdb.org/t/p/w500/8bRIfStfRw6dVF2E7q5L5qRf4GJ.jpg"
            )
        )
    }

    IMoviesTheme {
        MovieSection(
//            sectionState = HomeScreenState.MovieSectionState(
//                title = R.string.home_popular_section_title,
//                uiState = UiStateList.Success(data = list)
//            ),
//            sectionState = HomeScreenState.MovieSectionState(
//                title = R.string.home_popular_section_title,
//                uiState = UiStateList.Loading
//            ),
            sectionState = HomeScreenState.MovieSectionState(
                sectionType = HomeMovieSectionType.POPULAR,
                title = R.string.home_popular_section_title,
                uiState = UiStateList.Failure("Error"),
            ),

            onMovieClicked = {},
            onRetry = {}
        )
    }
}