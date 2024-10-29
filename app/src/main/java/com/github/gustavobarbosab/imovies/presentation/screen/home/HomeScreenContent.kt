package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.AppLogo
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.AppToolbar
import com.github.gustavobarbosab.imovies.presentation.screen.home.components.MovieSection
import com.github.gustavobarbosab.imovies.presentation.screen.home.components.TopBannerSection
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

@Composable
fun HomeScreenContent(
    screenState: HomeScreenState,
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    Column {
        AppToolbar {
            AppLogo(Modifier.fillMaxWidth())
        }
        LazyColumn {
            item(key = "header") {
                TopBannerSection(
                    Modifier
                        .padding(top = MaterialTheme.spacing.medium)
                        .sizeIn(minHeight = 200.dp)
                        .fillMaxWidth(),
                    screenState.topBannerMovies,
                    pagerSize = 6,
                    onMovieClicked = onMovieClicked
                )
            }

            items(screenState.movieSectionList, key = { it.title }) { sectionState ->
                MovieSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = MaterialTheme.spacing.medium,
                            bottom = MaterialTheme.spacing.medium,
                            start = MaterialTheme.spacing.medium,
                        ),
                    sectionState = sectionState,
                    onMovieClicked = onMovieClicked
                )
            }
        }
    }
}


@Preview
@Composable
private fun previewHomeScreenContent() {
    IMoviesTheme {
        HomeScreenContent(
            screenState = HomeScreenState.initialState(),
            onMovieClicked = {}
        )
    }
}
