package com.github.gustavobarbosab.imovies.domain.movies

import com.github.gustavobarbosab.imovies.core.domain.network.NetworkResponse
import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage

interface MoviesRepository {
    suspend fun getReleaseMovies(page: Int): NetworkResponse<MoviePage>
    suspend fun getUpcomingMovies(page: Int): NetworkResponse<MoviePage>
}
