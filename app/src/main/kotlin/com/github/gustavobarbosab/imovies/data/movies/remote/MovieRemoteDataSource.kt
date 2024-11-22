package com.github.gustavobarbosab.imovies.data.movies.remote

import com.github.gustavobarbosab.imovies.core.data.network.adapter.NetworkResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MoviePageResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MovieResponse

interface MovieRemoteDataSource {

    suspend fun getUpcomingMovies(page: Int): NetworkResponse<MoviePageResponse>

    suspend fun getPopularMovies(page: Int): NetworkResponse<MoviePageResponse>

    suspend fun getTopRatedMovies(page: Int): NetworkResponse<MoviePageResponse>

    suspend fun getNowPlayingMovies(page: Int): NetworkResponse<MoviePageResponse>

    suspend fun getMovieDetail(movieId: Long): NetworkResponse<MovieResponse>
}