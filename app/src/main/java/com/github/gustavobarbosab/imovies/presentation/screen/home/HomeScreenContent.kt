package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.AppLogo
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.AppToolbar
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.MovieCardDefaults
import com.github.gustavobarbosab.imovies.presentation.screen.home.components.MovieSection
import com.github.gustavobarbosab.imovies.presentation.screen.home.components.TopBannerSection
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieSectionType
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

@Composable
fun HomeScreenContent(
    screenState: HomeScreenState,
    onRetryLoadSection: (HomeMovieSectionType) -> Unit = {},
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppToolbar {
                AppLogo(Modifier.fillMaxWidth())
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn {
                item(key = "header") {
                    TopBannerSection(
                        Modifier
                            .padding(MaterialTheme.spacing.extraSmall)
                            .defaultMinSize(minHeight = MaterialTheme.spacing.MovieCardDefaults.backDropHeight)
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
                        onMovieClicked = onMovieClicked,
                        onRetry = { onRetryLoadSection(sectionState.sectionType) }
                    )
                }
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
