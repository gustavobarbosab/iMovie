package com.github.gustavobarbosab.imovies.presentation.screen.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.github.gustavobarbosab.imovies.common.presentation.UiStateList
import com.github.gustavobarbosab.imovies.common.presentation.compose.RecurrentTaskLaunchEffect
import com.github.gustavobarbosab.imovies.common.presentation.compose.extension.shimmerEffect
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

@Composable
fun TopBannerSection(
    modifier: Modifier = Modifier,
    sectionState: UiStateList<HomeMovieModel>,
    pagerSize: Int,
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    Box(modifier) {
        when (sectionState) {
            UiStateList.Loading -> TopBannerLoading(Modifier.matchParentSize())

            is UiStateList.Success -> AutoScrollableMoviesPager(
                Modifier.matchParentSize(),
                sectionState.data,
                pagerSize = pagerSize,
                onMovieClicked = onMovieClicked
            )

            else -> Unit
        }
    }
}

@Composable
fun AutoScrollableMoviesPager(
    modifier: Modifier,
    movies: List<HomeMovieModel>,
    pagerSize: Int = 4,
    onMovieClicked: (HomeMovieModel) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { pagerSize })

    RecurrentTaskLaunchEffect(pagerState, delayInMillis = 2000) {
        val nextPage = (pagerState.currentPage + 1) % pagerSize
        pagerState.animateScrollToPage(nextPage)
    }

    HorizontalPager(
        modifier = modifier,
        state = pagerState
    ) { page ->
        val movie = movies[page]
        MovieCard(
            modifier = Modifier.fillMaxSize(),
            imagePath = movie.backdropPath,
            onClick = { onMovieClicked(movie) }
        )
    }
}


@Composable
fun TopBannerLoading(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .padding(MaterialTheme.spacing.extraSmall)
            .clip(MaterialTheme.shapes.medium)
            .shimmerEffect(),
    )
}