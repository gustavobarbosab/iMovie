package com.github.gustavobarbosab.imovies.common.presentation.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.github.gustavobarbosab.imovies.common.presentation.AsyncImageState
import com.github.gustavobarbosab.imovies.common.presentation.toAsyncState
import com.github.gustavobarbosab.imovies.presentation.theme.Error
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme
import com.github.gustavobarbosab.imovies.presentation.theme.Spacing
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

val Spacing.MovieCardDefaults
    get() = MovieCardDefaultParams

object MovieCardDefaultParams {
    val backDropHeight: Dp = 220.dp
    val backDropRatio: Float = 16 / 9f
    val posterHeight: Dp = 220.dp
}

@Composable
fun MovieCard(
    imagePath: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    shape: CornerBasedShape? = MaterialTheme.shapes.medium,
    error: ImageVector = Icons.Error
) {
    val imagePainter = rememberAsyncImagePainter(
        model = imagePath
    )
    val imageState by imagePainter.state.collectAsState()

    MovieCardContent(
        modifier = modifier,
        painter = imagePainter,
        imageState = imageState.toAsyncState(),
        onClick = onClick,
        error = error,
        shape = shape
    )
}


@Composable
private fun MovieCardContent(
    painter: Painter,
    imageState: AsyncImageState,
    onClick: () -> Unit,
    shape: Shape?,
    modifier: Modifier = Modifier,
    error: ImageVector = Icons.Filled.Info
) {
    Box(
        modifier.clickable(onClick = { onClick() }),
        contentAlignment = Alignment.Center
    ) {
        val childModifier = Modifier
            .matchParentSize()
            .apply { if (shape != null) clip(shape) }
            .background(MaterialTheme.colorScheme.background)

        when (imageState) {
            AsyncImageState.Error -> ImageErrorFeedback(childModifier, error)
            AsyncImageState.Loading -> ImageLoading(childModifier)
            AsyncImageState.Success -> Image(
                modifier = childModifier,
                painter = painter,
                contentDescription = "movie image",
            )

            else -> Unit
        }
    }
}

@Composable
private fun ImageLoading(modifier: Modifier = Modifier) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ImageErrorFeedback(
    modifier: Modifier = Modifier,
    error: ImageVector
) {
    Column(
        modifier = modifier,
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
            imageState = AsyncImageState.Error,
            onClick = {},
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.size(135.dp, 240.dp),
        )
    }
}