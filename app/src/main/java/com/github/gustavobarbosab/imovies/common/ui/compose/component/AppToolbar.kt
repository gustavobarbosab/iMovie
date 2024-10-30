package com.github.gustavobarbosab.imovies.common.ui.compose.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppToolbar(
    modifier: Modifier = Modifier,
    title: String,
    leftIcon: ImageVector? = null,
    rightIcon: ImageVector? = null,
    onBackClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {

    Icons.AutoMirrored.Filled.ArrowBack
    AppToolbar(
        modifier = modifier,
        leftIcon = leftIcon,
        rightIcon = rightIcon,
        onLeftIconClick = onBackClick,
        onRightIconClick = onMenuClick
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    modifier: Modifier = Modifier,
    leftIcon: ImageVector? = null,
    onLeftIconClick: () -> Unit = {},
    rightIcon: ImageVector? = null,
    onRightIconClick: () -> Unit = {},
    titleContent: @Composable () -> Unit
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = titleContent,
        navigationIcon = {
            if (leftIcon != null) {
                IconButton(onClick = onLeftIconClick) {
                    Icon(
                        imageVector = leftIcon,
                        contentDescription = "Left toolbar button",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        actions = {
            if (rightIcon != null) {
                IconButton(onClick = onRightIconClick) {
                    Icon(
                        imageVector = rightIcon,
                        contentDescription = "Right toolbar button",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAppToolbar() {
    AppToolbar(
        title = "My Toolbar",
        onBackClick = { /* Handle back click */ },
        onMenuClick = { /* Handle menu click */ }
    )
}