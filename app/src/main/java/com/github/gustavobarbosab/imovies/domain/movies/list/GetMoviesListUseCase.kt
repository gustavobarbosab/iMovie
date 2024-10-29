package com.github.gustavobarbosab.imovies.domain.movies.list

import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage

interface GetMoviesListUseCase {

    interface UpcomingMovies {
        suspend fun getUpcomingMovies(page: Int = FIRST_PAGE): Response
    }

    interface TopRatedMovies {
        suspend fun getTopRatedMovies(page: Int = FIRST_PAGE): Response
    }

    interface NowPlayingMovies {
        suspend fun getNowPlayingMovies(page: Int = FIRST_PAGE): Response
    }

    interface PopularMovies {
        suspend fun getPopularMovies(page: Int = FIRST_PAGE): Response
    }

    sealed class Response {
        data class Success(val moviePage: MoviePage) : Response()
        data object ThereIsNoMovies : Response()
        data class Error(
            val message: String?
        ) : Response()
    }

    private companion object {
        const val FIRST_PAGE = 1
    }
}
