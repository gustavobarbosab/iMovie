package com.github.gustavobarbosab.imovies.domain.movies

import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage

interface GetMoviesUseCaseContract {

    interface UpcomingMoviesUseCase {
        suspend fun getUpcomingMovies(page: Int): Result
    }

    interface TopRatedMoviesUseCase {
        suspend fun getTopRatedMovies(page: Int): Result
    }

    interface NowPlayingMoviesUseCase {
        suspend fun getNowPlayingMovies(page: Int): Result
    }

    interface PopularMoviesUseCase {
        suspend fun getPopularMovies(page: Int): Result
    }

    sealed class Result {
        data class Success(val moviePage: MoviePage) : Result()
        data object ThereIsNoMovies : Result()
        data class Error(
            val message: String?
        ) : Result()
    }
}
