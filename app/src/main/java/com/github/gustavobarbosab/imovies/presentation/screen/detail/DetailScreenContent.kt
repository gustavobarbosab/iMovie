package com.github.gustavobarbosab.imovies.presentation.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gustavobarbosab.imovies.R
import com.github.gustavobarbosab.imovies.common.presentation.UiState
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.AppToolbar
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.MovieCard
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.MovieCardDefaults
import com.github.gustavobarbosab.imovies.common.presentation.compose.component.SkeletonItem
import com.github.gustavobarbosab.imovies.presentation.theme.Error
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme
import com.github.gustavobarbosab.imovies.presentation.theme.LeftToolbarIcon
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

@Composable
fun DetailScreenContent(
    screenState: DetailScreenState,
    onBackPressed: () -> Unit,
    onRetryClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppToolbar(
                title = stringResource(R.string.detail_toolbar_title),
                leftIcon = Icons.LeftToolbarIcon,
                onBackClick = onBackPressed
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val uiState = screenState.uiState) {
                is UiState.Loading -> DetailScreenSkeleton()

                is UiState.Success -> DetailScreenSuccess(
                    posterPath = uiState.data.backdropPath,
                    title = uiState.data.title,
                    overview = uiState.data.overview
                )

                is UiState.Failure -> DetailScreenFailure(
                    onTryAgain = onRetryClicked,
                    message = stringResource(R.string.detail_load_data_failure)
                )
            }
        }
    }
}

@Composable
private fun DetailScreenSkeleton() {
    Column(
        Modifier
            .padding(MaterialTheme.spacing.medium)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        SkeletonItem(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
        )

        SkeletonItem(
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.medium)
                .height(30.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
        )
        SkeletonItem(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
        )
    }
}

@Composable
private fun DetailScreenSuccess(
    modifier: Modifier = Modifier,
    posterPath: String,
    title: String,
    overview: String,
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        MovieCard(
            modifier = Modifier
                .defaultMinSize(minHeight = MaterialTheme.spacing.MovieCardDefaults.backDropHeight)
                .fillMaxWidth()
                .aspectRatio(MaterialTheme.spacing.MovieCardDefaults.backDropRatio),
            shape = null,
            imagePath = posterPath,
        )

        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
            text = overview,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun BoxScope.DetailScreenFailure(
    message: String,
    onTryAgain: () -> Unit,
) {
    Surface(
        Modifier
            .padding(MaterialTheme.spacing.medium)
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .align(Alignment.Center),
    ) {
        Column(
            Modifier.padding(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Error,
                contentDescription = "Back",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = message,
                textAlign = TextAlign.Center,
            )

            OutlinedButton(onTryAgain) {
                Text("Try again")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DetailScreenContentPreview() {
    IMoviesTheme {
        DetailScreenSuccess(
            posterPath = "",
            title = "Title",
            overview = "Overview"
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun DetailScreenContentFailurePreview() {
    IMoviesTheme {
        Box(Modifier.fillMaxSize()) {
            DetailScreenFailure(
                message = "Error",
                onTryAgain = {}
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DetailScreenContentSkeletonPreview() {
    IMoviesTheme {
        DetailScreenSkeleton()
    }
}

