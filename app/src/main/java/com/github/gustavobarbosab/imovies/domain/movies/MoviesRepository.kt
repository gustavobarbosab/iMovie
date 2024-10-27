package com.github.gustavobarbosab.imovies.domain.movies

import com.github.gustavobarbosab.imovies.core.data.network.NetworkResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MoviePageResponse

interface MoviesRepository {
    suspend fun getReleaseMovies(page: Int): NetworkResponse<MoviePageResponse>
}
