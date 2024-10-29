package com.github.gustavobarbosab.imovies.domain.movies.list

import com.github.gustavobarbosab.imovies.core.domain.DomainResponse
import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage

interface MoviesListRepository {
    suspend fun getTopRatedMovies(page: Int): DomainResponse<MoviePage>
    suspend fun getNowPlayingMovies(page: Int): DomainResponse<MoviePage>
    suspend fun getPopularMovies(page: Int): DomainResponse<MoviePage>
    suspend fun getUpcomingMovies(page: Int): DomainResponse<MoviePage>
}
