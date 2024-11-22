package com.github.gustavobarbosab.imovies.common.ui

import coil3.compose.AsyncImagePainter

sealed class AsyncImageState {
    data object Empty : AsyncImageState()
    data object Loading : AsyncImageState()
    data object Success : AsyncImageState()
    data object Error : AsyncImageState()
}

fun AsyncImagePainter.State.toAsyncState(): AsyncImageState =
    when (this) {
        AsyncImagePainter.State.Empty -> AsyncImageState.Empty
        is AsyncImagePainter.State.Loading -> AsyncImageState.Loading
        is AsyncImagePainter.State.Success -> AsyncImageState.Success
        is AsyncImagePainter.State.Error -> AsyncImageState.Error
    }