package com.github.gustavobarbosab.imovies.data.movies.repository

import com.github.gustavobarbosab.imovies.core.data.network.adapter.mapToDomain
import com.github.gustavobarbosab.imovies.core.domain.DomainResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.MovieRemoteDataSource
import com.github.gustavobarbosab.imovies.domain.movies.detail.GetMovieDetailRepository
import com.github.gustavobarbosab.imovies.domain.movies.entity.Movie
import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage
import com.github.gustavobarbosab.imovies.domain.movies.list.MoviesListRepository
import javax.inject.Inject

class MoviesListRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    // TODO include the local data source to get the image url
    private val mapper: MovieRepositoryMapper
) : MoviesListRepository, GetMovieDetailRepository {

    override suspend fun getTopRatedMovies(page: Int): DomainResponse<MoviePage> =
        remoteDataSource.getTopRatedMovies(page).mapToDomain(mapper::map)

    override suspend fun getNowPlayingMovies(page: Int): DomainResponse<MoviePage> =
        remoteDataSource.getNowPlayingMovies(page).mapToDomain(mapper::map)

    override suspend fun getPopularMovies(page: Int): DomainResponse<MoviePage> =
        remoteDataSource.getPopularMovies(page).mapToDomain(mapper::map)

    override suspend fun getUpcomingMovies(page: Int): DomainResponse<MoviePage> =
        remoteDataSource.getUpcomingMovies(page).mapToDomain(mapper::map)

    override suspend fun getDetails(movieId: Long): DomainResponse<Movie> =
        remoteDataSource.getMovieDetail(movieId).mapToDomain(mapper::map)
}
