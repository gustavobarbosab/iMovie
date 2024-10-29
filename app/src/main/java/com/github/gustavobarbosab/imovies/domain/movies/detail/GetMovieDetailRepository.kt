package com.github.gustavobarbosab.imovies.domain.movies.detail

import com.github.gustavobarbosab.imovies.core.domain.DomainResponse
import com.github.gustavobarbosab.imovies.domain.movies.entity.Movie

interface GetMovieDetailRepository {
    suspend fun getDetails(movieId: Long): DomainResponse<Movie>
}
