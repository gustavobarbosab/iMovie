package com.github.gustavobarbosab.imovies.data.movies.remote

import com.github.gustavobarbosab.imovies.core.data.network.adapter.NetworkResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MoviePageResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{type}")
    suspend fun getMovieList(
        @Path("type") type: String,
        @Query("page") page: Int,
    ): NetworkResponse<MoviePageResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Long): NetworkResponse<MovieResponse>
}
