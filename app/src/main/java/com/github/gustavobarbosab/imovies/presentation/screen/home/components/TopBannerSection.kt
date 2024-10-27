package com.github.gustavobarbosab.imovies.presentation.screen.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.gustavobarbosab.imovies.common.presentation.UiState
import com.github.gustavobarbosab.imovies.common.presentation.compose.RecurrentTaskLaunchEffect
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel

@Composable
fun TopBannerSection(
    modifier: Modifier = Modifier,
    sectionState: UiState<List<HomeMovieModel>>,
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    when (sectionState) {
        UiState.Loading -> Column(modifier) {
            CircularProgressIndicator()
        }

        is UiState.Success -> AutoScrollableMoviesPager(
            modifier,
            sectionState.data,
            onMovieClicked = onMovieClicked
        )

        is UiState.Failure -> Column {
            // TODO show a retry button
        }
    }
}

@Composable
fun AutoScrollableMoviesPager(
    modifier: Modifier,
    movies: List<HomeMovieModel>,
    pageCount: Int = 4,
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { pageCount })

    RecurrentTaskLaunchEffect(pagerState, 2000) {
        val nextPage = (pagerState.currentPage + 1) % pageCount
        pagerState.animateScrollToPage(nextPage)
    }

    HorizontalPager(
        modifier = modifier,
        state = pagerState
    ) { page ->
        val movie = movies[page]
        MovieCard(
            Modifier,
            bannerUrl = movie.backdropPath,
            onClick = { onMovieClicked(movie) }
        )
    }
}
