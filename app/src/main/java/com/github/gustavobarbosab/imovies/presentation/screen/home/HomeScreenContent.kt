package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.gustavobarbosab.imovies.presentation.screen.home.components.MovieSection
import com.github.gustavobarbosab.imovies.presentation.screen.home.components.HeaderSection
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    screenState: HomeScreenState,
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    Surface {
        LazyColumn {
            stickyHeader(key = "toolbar") {
                Column { }
            }

            item(key = "header") {
                HeaderSection(
                    Modifier
                        .sizeIn(minHeight = 200.dp)
                        .fillMaxWidth()
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