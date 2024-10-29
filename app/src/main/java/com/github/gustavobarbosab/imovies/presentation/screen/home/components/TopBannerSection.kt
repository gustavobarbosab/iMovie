package com.github.gustavobarbosab.imovies.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.github.gustavobarbosab.imovies.R
import com.github.gustavobarbosab.imovies.common.presentation.UiStateList
import com.github.gustavobarbosab.imovies.common.presentation.compose.RecurrentTaskLaunchEffect
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.FeedbackContainer
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.MovieCard
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.MovieCardDefaults
import com.github.gustavobarbosab.imovies.common.presentation.compose.extension.shimmerEffect
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

@Composable
fun TopBannerSection(
    modifier: Modifier = Modifier,
    sectionState: UiStateList<HomeMovieModel>,
    pagerSize: Int,
    onRetry: () -> Unit,
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

            is UiStateList.Failure -> FeedbackContainer(
                modifier = Modifier.matchParentSize(),
                onRetry = onRetry,
                message = stringResource(R.string.home_section_top_banner_failure)
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
        Box {
            MovieCard(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(MaterialTheme.spacing.MovieCardDefaults.backDropRatio),
                imagePath = movie.backdropPath,
                onClick = { onMovieClicked(movie) }
            )
            Surface(
                Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.spacing.medium,
                            vertical = MaterialTheme.spacing.small,
                        ),
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}


@Composable
fun TopBannerLoading(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .shimmerEffect(),
    )
}