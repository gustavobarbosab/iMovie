package com.github.gustavobarbosab.imovies.domain.movies

import com.github.gustavobarbosab.imovies.core.domain.network.NetworkResponse
import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage

interface MoviesRepository {
    suspend fun getTopRatedMovies(page: Int): NetworkResponse<MoviePage>
    suspend fun getNowPlayingMovies(page: Int): NetworkResponse<MoviePage>
    suspend fun getPopularMovies(page: Int): NetworkResponse<MoviePage>
    suspend fun getUpcomingMovies(page: Int): NetworkResponse<MoviePage>
}
