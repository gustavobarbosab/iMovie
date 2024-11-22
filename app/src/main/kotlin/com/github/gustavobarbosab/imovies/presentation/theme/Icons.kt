package com.github.gustavobarbosab.imovies.presentation.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

typealias AppFilledIcon = Icons.AutoMirrored.Filled

val Icons.LeftToolbarIcon: ImageVector
    get() = AppFilledIcon.ArrowBack

val Icons.Error: ImageVector
    get() = Icons.Filled.Info

