package com.github.gustavobarbosab.imovies.presentation.screen.detail.model

import com.github.gustavobarbosab.imovies.domain.movies.entity.Movie
import javax.inject.Inject

class DetailScreenMapper @Inject constructor() {

    fun mapToModel(movie: Movie) = DetailScreenModel(
        title = movie.title,
        overview = movie.overview,
        backdropPath = movie.backdropPath,
        voteAverage = movie.voteAverage
        // TODO add more parameters
    )
}
