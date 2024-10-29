package com.github.gustavobarbosab.imovies.domain.movies.detail

import com.github.gustavobarbosab.imovies.domain.movies.entity.Movie

interface GetMovieDetailUseCase {
    suspend fun getMovieDetail(movieId: Long): Result<Movie>
}
