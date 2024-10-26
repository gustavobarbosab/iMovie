package com.github.gustavobarbosab.imovies.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@Composable
fun RecurrentTaskLaunchEffect(
    key: Any?,
    delay: Long,
    task: suspend () -> Unit
) {
    LaunchedEffect(key) {
        while (true) {
            yield()  // Allow other tasks to run
            delay(delay)  // Adjust delay for the desired speed (3 seconds here)
            task()
        }
    }
}