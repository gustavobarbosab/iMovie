package com.github.gustavobarbosab.imovies.domain.movies.detail

import com.github.gustavobarbosab.imovies.core.domain.DomainResponse
import com.github.gustavobarbosab.imovies.domain.movies.entity.Movie

class GetMovieDetailUseCaseImpl(
    private val repository: GetMovieDetailRepository
) : GetMovieDetailUseCase {

    override suspend fun getMovieDetail(movieId: Long): Result<Movie> =
        when (val response = repository.getDetails(movieId)) {
            is DomainResponse.Success -> Result.success(response.data)
            else -> Result.failure(Exception("Unknown error"))
        }
}
