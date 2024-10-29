package com.github.gustavobarbosab.imovies.presentation.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

// TODO extract to a common module
private sealed class MovieCardInternalState {
    data object Empty : MovieCardInternalState()
    data object Loading : MovieCardInternalState()
    data object Success : MovieCardInternalState()
    data object Error : MovieCardInternalState()
}

private fun AsyncImagePainter.State.toInternalState(): MovieCardInternalState =
    when (this) {
        AsyncImagePainter.State.Empty -> MovieCardInternalState.Empty
        is AsyncImagePainter.State.Loading -> MovieCardInternalState.Loading
        is AsyncImagePainter.State.Success -> MovieCardInternalState.Success
        is AsyncImagePainter.State.Error -> MovieCardInternalState.Error
    }


@Composable
fun MovieCard(
    imagePath: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    error: ImageVector = Icons.Filled.Info
) {
    val imagePainter = rememberAsyncImagePainter(model = imagePath)
    val imageState by imagePainter.state.collectAsState()

    MovieCardContent(
        modifier = modifier,
        painter = imagePainter,
        imageState = imageState.toInternalState(),
        onClick = onClick,
        error = error
    )
}


@Composable
private fun MovieCardContent(
    painter: Painter,
    imageState: MovieCardInternalState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    error: ImageVector = Icons.Filled.Info
) {
    Box(
        modifier.clickable(
            interactionSource = null,
            indication = rememberRipple(color = MaterialTheme.colorScheme.primary),
            onClick = { onClick() }
        ),
        contentAlignment = Alignment.Center
    ) {
        when (imageState) {
            MovieCardInternalState.Error -> ImageErrorFeedback(error)
            MovieCardInternalState.Loading -> CircularProgressIndicator()
            MovieCardInternalState.Success -> Image(
                modifier = Modifier.matchParentSize(),
                painter = painter,
                contentDescription = "movie image",
            )

            else -> Unit
        }
    }
}

@Composable
private fun BoxScope.ImageErrorFeedback(
    error: ImageVector
) {
    Column(
        modifier = Modifier
            .matchParentSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = error,
            contentDescription = "movie image",
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.size(MaterialTheme.spacing.medium))
        Text(
            text = "Error loading image",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
@Preview
private fun preview() {
    IMoviesTheme {
        MovieCardContent(
            painter = rememberVectorPainter(Icons.Filled.Info),
            imageState = MovieCardInternalState.Loading,
            onClick = {},
            modifier = Modifier.size(135.dp, 240.dp),
        )
    }
}