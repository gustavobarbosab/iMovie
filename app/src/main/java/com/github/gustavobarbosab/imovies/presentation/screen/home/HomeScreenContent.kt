package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gustavobarbosab.imovies.R
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.AppLogo
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.AppToolbar
import com.github.gustavobarbosab.imovies.presentation.screen.home.components.MovieSection
import com.github.gustavobarbosab.imovies.presentation.screen.home.components.TopBannerSection
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

@OptIn(ExperimentalFoundationApi::class)
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
            // These items could be created dynamically based on the screenState
            // but for simplicity, we are creating them statically
            // It's a good idea to create an approach similar to a server driven UI
            // TODO remove this toolbar from here

            item(key = "header") {
                TopBannerSection(
                    Modifier
                        .sizeIn(minHeight = 200.dp)
                        .fillMaxWidth(),
                    screenState.topBannerMovies,
                    onMovieClicked = onMovieClicked
                )
            }

            item(key = "popular") {
                MovieSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = MaterialTheme.spacing.medium,
                            bottom = MaterialTheme.spacing.medium,
                            start = MaterialTheme.spacing.medium,
                        ),
                    title = "Popular",
                    sectionState = screenState.popularMovies,
                    onMovieClicked = onMovieClicked
                )
            }

            item(key = "top-rated") {
                MovieSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = MaterialTheme.spacing.medium,
                            bottom = MaterialTheme.spacing.medium,
                            start = MaterialTheme.spacing.medium,
                        ),
                    title = "Top Rated",
                    sectionState = screenState.topRatedMovies,
                    onMovieClicked = onMovieClicked
                )
            }

            item(key = "upcoming") {
                MovieSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = MaterialTheme.spacing.medium,
                            bottom = MaterialTheme.spacing.medium,
                            start = MaterialTheme.spacing.medium,
                        ),
                    title = "Upcoming",
                    sectionState = screenState.upcomingMovies,
                    onMovieClicked = onMovieClicked
                )
            }
        }
    }
}


@Preview
@Composable
private fun previewHomeScreenContent() {
    HomeScreenContent(
        screenState = HomeScreenState.initialState(),
        onMovieClicked = {}
    )
}
