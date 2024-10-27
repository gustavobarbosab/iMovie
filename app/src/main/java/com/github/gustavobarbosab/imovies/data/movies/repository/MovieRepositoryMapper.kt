package com.github.gustavobarbosab.imovies.data.movies.repository

import com.github.gustavobarbosab.imovies.data.movies.remote.response.MoviePageResponse
import com.github.gustavobarbosab.imovies.domain.movies.entity.Movie
import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage
import javax.inject.Inject

class MovieRepositoryMapper @Inject constructor() {

    fun map(moviePage: MoviePageResponse) = MoviePage(
        movies = moviePage.results.map { movie ->
            Movie(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
                posterPath = "https://image.tmdb.org/t/p/w500${movie.posterPath}", // TODO get this URL from storage
                backdropPath = "https://image.tmdb.org/t/p/w1280${movie.backdropPath}",
                releaseDate = movie.releaseDate,
                popularity = movie.popularity,
                voteAverage = movie.voteAverage
            )
        },
        page = moviePage.page,
        totalPages = moviePage.totalPages
    )
}
