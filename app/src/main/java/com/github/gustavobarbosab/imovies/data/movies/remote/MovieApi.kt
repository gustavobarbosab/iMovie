package com.github.gustavobarbosab.imovies.data.movies.remote

import com.github.gustavobarbosab.imovies.core.domain.network.NetworkResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MoviePageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{type}")
    suspend fun getMovieList(
        @Path("type") type: String,
        @Query("page") page: Int,
    ): NetworkResponse<MoviePageResponse>
}
