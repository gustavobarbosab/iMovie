package com.github.gustavobarbosab.imovies.data.movies.repository

import com.github.gustavobarbosab.imovies.core.domain.network.NetworkResponse
import com.github.gustavobarbosab.imovies.core.domain.network.map
import com.github.gustavobarbosab.imovies.data.movies.remote.MovieApi
import com.github.gustavobarbosab.imovies.data.movies.remote.MovieRemoteDataSource
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MoviePageResponse
import com.github.gustavobarbosab.imovies.domain.movies.MoviesRepository
import com.github.gustavobarbosab.imovies.domain.movies.entity.Movie
import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) : MoviesRepository {

    override suspend fun getReleaseMovies(page: Int): NetworkResponse<MoviePage> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcomingMovies(page: Int): NetworkResponse<MoviePage> =
        remoteDataSource.getUpcomingMovies(page).map {
            MoviePage(
                movies = it.results.map { movie ->
                    Movie(
                        id = movie.id,
                        title = movie.title,
                        overview = movie.overview,
                        posterPath = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                        backdropPath = "https://image.tmdb.org/t/p/w1280${movie.backdropPath}",
                        releaseDate = movie.releaseDate,
                        popularity = movie.popularity,
                        voteAverage = movie.voteAverage
                    )
                },
                page = it.page,
                totalPages = it.totalPages
            )
        }
}
