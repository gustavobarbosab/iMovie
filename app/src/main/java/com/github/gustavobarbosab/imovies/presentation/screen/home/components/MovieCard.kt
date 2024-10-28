package com.github.gustavobarbosab.imovies.presentation.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.rememberAsyncImagePainter
import com.github.gustavobarbosab.imovies.R
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme

@Composable
fun MovieCard(
    imagePath: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fallback: ImageVector = Icons.Filled.Info
) {
    Box(
        modifier
            .clickable(
                interactionSource = null,
                indication = rememberRipple(color = MaterialTheme.colorScheme.primary),
                onClick = { onClick() }
            )
    ) {
        val errorPainter: Painter = rememberVectorPainter(fallback)

        Image(
            painter = rememberAsyncImagePainter(
                model = imagePath,
                fallback = errorPainter
            ),
            contentDescription = stringResource(R.string.home_movie_image_content_description),
        )
    }
}

@Composable
@Preview
private fun preview() {
    IMoviesTheme {
        MovieCard(
            imagePath = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg",
            onClick = {}
        )
    }
}