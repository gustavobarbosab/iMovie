package com.github.gustavobarbosab.imovies.presentation.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    bannerUrl: String,
    onClick: () -> Unit
) {
    Box(
        modifier
            .sizeIn(maxHeight = 200.dp)
            .clickable(
                interactionSource = null,
                indication = rememberRipple(color = MaterialTheme.colorScheme.primary),
                onClick = { onClick() }
            )
            .clip(RoundedCornerShape(MaterialTheme.spacing.small))
    ) {
        Image(
            modifier = Modifier,
            painter = rememberAsyncImagePainter(bannerUrl),
            contentDescription = "movie image",
        )
    }
}

@Composable
@Preview
private fun preview() {
    IMoviesTheme {
        MovieCard(
            bannerUrl = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg",
            onClick = {}
        )
    }
}