package com.github.gustavobarbosab.imovies.common.presentation.compose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gustavobarbosab.imovies.common.presentation.compose.extension.shimmerEffect
import com.github.gustavobarbosab.imovies.presentation.theme.spacing

@Composable
fun SkeletonItem(
    modifier: Modifier,
    shape: Shape? = MaterialTheme.shapes.medium
) {
    Box(modifier) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .apply { if (shape != null) clip(shape) }
                .shimmerEffect()
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SkeletonItemPreview() {
    SkeletonItem(
        modifier = Modifier
            .padding(MaterialTheme.spacing.medium)
            .fillMaxWidth()
            .height(height = 100.dp)
    )
}