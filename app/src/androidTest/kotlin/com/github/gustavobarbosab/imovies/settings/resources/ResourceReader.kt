package com.github.gustavobarbosab.imovies.settings.resources

import java.nio.charset.StandardCharsets

class ResourceReader(private val featureName: String) {

    operator fun invoke(fileName: String): String {
        val classLoader = this::class.java.classLoader
        val inputStream = classLoader?.getResourceAsStream("$featureName/$fileName")
        return inputStream?.bufferedReader(StandardCharsets.UTF_8)?.use { it.readText() }.orEmpty()
    }
}
