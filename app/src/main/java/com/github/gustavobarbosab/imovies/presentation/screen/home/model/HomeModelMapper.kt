package com.github.gustavobarbosab.imovies.presentation.screen.home.model

import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage
import javax.inject.Inject

class HomeModelMapper @Inject constructor() {

    fun map(moviePage: MoviePage) = moviePage.movies.map { movie ->
        HomeMovieModel(
            id = movie.id,
            title = movie.title,
            posterUrl = movie.posterPath
        )
    }
}
