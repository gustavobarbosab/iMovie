package com.github.gustavobarbosab.imovies.common.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@Composable
fun RecurrentTaskLaunchEffect(
    key: Any?,
    delayInMillis: Long,
    task: suspend () -> Unit
) {
    LaunchedEffect(key) {
        while (true) {
            yield()  // Allow other tasks to run
            delay(delayInMillis)  // Adjust delay for the desired speed (3 seconds here)
            task()
        }
    }
}