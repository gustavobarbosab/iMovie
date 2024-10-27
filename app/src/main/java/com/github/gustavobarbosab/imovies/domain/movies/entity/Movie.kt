package com.github.gustavobarbosab.imovies.domain.movies.entity

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val popularity: Double,
    val voteAverage: Double,
)