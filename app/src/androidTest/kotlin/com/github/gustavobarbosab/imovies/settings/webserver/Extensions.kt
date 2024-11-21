package com.github.gustavobarbosab.imovies.settings.webserver

import com.github.gustavobarbosab.imovies.settings.resources.ResourceReader
import okhttp3.mockwebserver.MockResponse

fun MockResponse.setBodyFromJson(
    featureName: String,
    fileName: String
): MockResponse {
    val body = ResourceReader(featureName)(fileName)
    return setBody(body)
}

fun MockResponse.notFound(): MockResponse {
    return setResponseCode(404)
}