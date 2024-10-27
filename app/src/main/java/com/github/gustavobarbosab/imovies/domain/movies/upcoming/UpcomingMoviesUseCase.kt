package com.github.gustavobarbosab.imovies.domain.movies.upcoming

import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage

interface UpcomingMoviesUseCase {

    suspend fun getUpcomingMovies(page: Int): Result

    sealed class Result {
        data class Success(val moviePage: MoviePage) : Result()
        data object ThereIsNoMovies : Result()
        data class ExternalError(
            val code: Int,
            val message: String?
        ) : Result()

        data class InternalError(
            val throwable: Throwable?
        ) : Result()
    }
}
