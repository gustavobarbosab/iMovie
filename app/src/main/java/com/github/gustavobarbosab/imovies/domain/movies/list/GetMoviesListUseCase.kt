package com.github.gustavobarbosab.imovies.domain.movies.list

import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage

interface GetMoviesListUseCase {

    interface UpcomingMovies {
        suspend fun getUpcomingMovies(page: Int = FIRST_PAGE): Result
    }

    interface TopRatedMovies {
        suspend fun getTopRatedMovies(page: Int = FIRST_PAGE): Result
    }

    interface NowPlayingMovies {
        suspend fun getNowPlayingMovies(page: Int = FIRST_PAGE): Result
    }

    interface PopularMovies {
        suspend fun getPopularMovies(page: Int = FIRST_PAGE): Result
    }

    sealed class Result {
        data class Success(val moviePage: MoviePage) : Result()
        data object ThereIsNoMovies : Result()
        data class Error(
            val message: String?
        ) : Result()
    }

    private companion object {
        const val FIRST_PAGE = 1
    }
}
