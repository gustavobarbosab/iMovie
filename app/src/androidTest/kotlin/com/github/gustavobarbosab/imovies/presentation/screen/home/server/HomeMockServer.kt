package com.github.gustavobarbosab.imovies.presentation.screen.home.server

import com.github.gustavobarbosab.imovies.settings.webserver.notFound
import com.github.gustavobarbosab.imovies.settings.webserver.setBodyFromJson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class HomeMockServer(private val mockServer: MockWebServer = MockWebServer()) {

    private val featureName: String = "home"

    private var popularMoviesResponseMap: Pair<String, MockResponse> =
        "/movie/popular" to MockResponse()
    private var topRatedMoviesResponse: Pair<String, MockResponse> =
        "/movie/top_rated" to MockResponse()
    private var nowPlayingMoviesResponse: Pair<String, MockResponse> =
        "/movie/now_playing" to MockResponse()
    private var upcomingMoviesResponse: Pair<String, MockResponse> =
        "/movie/upcoming" to MockResponse()

    private val responses = mutableMapOf(
        popularMoviesResponseMap,
        topRatedMoviesResponse,
        nowPlayingMoviesResponse,
        upcomingMoviesResponse
    )

    fun popularMoviesSuccess() {
        popularMoviesResponseMap.second.setBodyFromJson(featureName, "movies_200.json")
    }

    fun topRatedMoviesSuccess() {
        topRatedMoviesResponse.second.setBodyFromJson(featureName, "movies_200.json")
    }

    fun nowPlayingMoviesSuccess() {
        nowPlayingMoviesResponse.second.setBodyFromJson(featureName, "movies_200.json")
    }

    fun upcomingMoviesSuccess() {
        upcomingMoviesResponse.second.setBodyFromJson(featureName, "movies_200.json")
    }

    fun popularMoviesFailure() {
        popularMoviesResponseMap.second.setResponseCode(400)
    }

    fun topRatedMoviesFailure() {
        topRatedMoviesResponse.second.setResponseCode(400)
    }

    fun nowPlayingMoviesFailure() {
        nowPlayingMoviesResponse.second.setResponseCode(400)
    }

    fun upcomingMoviesFailure() {
        upcomingMoviesResponse.second.setResponseCode(400)
    }

    operator fun invoke(preparation: HomeMockServer.() -> Unit) {
        this.preparation()
        start()
    }

    private fun start() = mockServer.apply {
        start(8080)
        dispatcher = RequestDispatcher()
    }

    fun stop() {
        mockServer.shutdown()
    }

    private inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse =
            responses.firstNotNullOfOrNull { (path, mock) ->
                if (request.path?.contains(path) == true) {
                    return@firstNotNullOfOrNull mock
                }
                return@firstNotNullOfOrNull null
            } ?: MockResponse().notFound()
    }
}
