package com.github.gustavobarbosab.imovies.data.movies.remote

import com.github.gustavobarbosab.imovies.core.domain.network.NetworkResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MoviePageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/releases")
    suspend fun getReleases(): NetworkResponse<MoviePageResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): NetworkResponse<MoviePageResponse>
}
