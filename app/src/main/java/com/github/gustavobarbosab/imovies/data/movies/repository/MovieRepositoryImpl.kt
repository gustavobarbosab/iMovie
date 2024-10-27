package com.github.gustavobarbosab.imovies.data.movies.repository

import com.github.gustavobarbosab.imovies.core.data.network.NetworkResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MoviePageResponse
import com.github.gustavobarbosab.imovies.domain.movies.MoviesRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor() : MoviesRepository {

    override suspend fun getReleaseMovies(page: Int): NetworkResponse<MoviePageResponse> {
        TODO("Not yet implemented")
    }
}
