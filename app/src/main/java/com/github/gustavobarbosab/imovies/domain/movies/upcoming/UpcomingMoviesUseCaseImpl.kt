package com.github.gustavobarbosab.imovies.domain.movies.upcoming

import com.github.gustavobarbosab.imovies.core.domain.network.NetworkResponse
import com.github.gustavobarbosab.imovies.domain.movies.MoviesRepository
import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage
import com.github.gustavobarbosab.imovies.domain.movies.upcoming.UpcomingMoviesUseCase.Result

class UpcomingMoviesUseCaseImpl(
    private val moviesRepository: MoviesRepository,
) : UpcomingMoviesUseCase {

    override suspend fun getUpcomingMovies(page: Int): Result =
        when (val response = moviesRepository.getUpcomingMovies(page)) {
            is NetworkResponse.Success -> handleSuccess(response.data)
            is NetworkResponse.EmptySuccess -> Result.ThereIsNoMovies
            is NetworkResponse.InternalException -> Result.InternalError(response.throwable)
            is NetworkResponse.ServerException -> Result.ExternalError(
                response.code,
                response.message
            )
        }

    private fun handleSuccess(moviePage: MoviePage) = if (moviePage.movies.isEmpty()) {
        Result.ThereIsNoMovies
    } else {
        Result.Success(moviePage)
    }
}
