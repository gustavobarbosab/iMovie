package com.github.gustavobarbosab.imovies.presentation.screen.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.gustavobarbosab.imovies.common.ui.RecurrentTaskLaunchEffect
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeScreenState
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel

@Composable
fun TopBannerSection(
    modifier: Modifier = Modifier,
    sectionState: HomeScreenState.MovieSectionState,
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    when (sectionState) {
        HomeScreenState.MovieSectionState.Loading -> Column(modifier) {
            CircularProgressIndicator()
        }

        is HomeScreenState.MovieSectionState.ShowMovies -> AutoScrollableMoviesPager(
            modifier,
            sectionState.movies,
            onMovieClicked = onMovieClicked
        )

        HomeScreenState.MovieSectionState.LoadFailure -> Column {
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
            bannerUrl = movie.posterUrl,
            onClick = { onMovieClicked(movie) }
        )
    }
}
