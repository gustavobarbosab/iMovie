package com.github.gustavobarbosab.imovies.domain.movies

import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage

interface GetMoviesUseCase {

    interface UpcomingMovies {
        suspend fun getUpcomingMovies(page: Int): Result
    }

    interface TopRatedMovies {
        suspend fun getTopRatedMovies(page: Int): Result
    }

    interface NowPlayingMovies {
        suspend fun getNowPlayingMovies(page: Int): Result
    }

    interface PopularMovies {
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
