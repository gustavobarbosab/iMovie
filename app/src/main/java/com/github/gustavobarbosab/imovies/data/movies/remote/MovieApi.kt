package com.github.gustavobarbosab.imovies.data.movies.remote

import com.github.gustavobarbosab.imovies.core.data.network.NetworkResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MoviePageResponse
import retrofit2.http.GET

interface MovieApi {
    @GET("/v1/releases")
    suspend fun getReleases(): NetworkResponse<MoviePageResponse>
}
