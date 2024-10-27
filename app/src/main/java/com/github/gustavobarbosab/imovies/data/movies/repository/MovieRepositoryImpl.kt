package com.github.gustavobarbosab.imovies.data.movies.repository

import com.github.gustavobarbosab.imovies.core.domain.network.NetworkResponse
import com.github.gustavobarbosab.imovies.core.domain.network.map
import com.github.gustavobarbosab.imovies.data.movies.remote.MovieRemoteDataSource
import com.github.gustavobarbosab.imovies.domain.movies.MoviesRepository
import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val mapper: MovieRepositoryMapper
) : MoviesRepository {

    override suspend fun getTopRatedMovies(page: Int): NetworkResponse<MoviePage> =
        remoteDataSource.getTopRatedMovies(page).map(mapper::map)

    override suspend fun getNowPlayingMovies(page: Int): NetworkResponse<MoviePage> =
        remoteDataSource.getNowPlayingMovies(page).map(mapper::map)

    override suspend fun getPopularMovies(page: Int): NetworkResponse<MoviePage> =
        remoteDataSource.getPopularMovies(page).map(mapper::map)

    override suspend fun getUpcomingMovies(page: Int): NetworkResponse<MoviePage> =
        remoteDataSource.getUpcomingMovies(page).map(mapper::map)
}
