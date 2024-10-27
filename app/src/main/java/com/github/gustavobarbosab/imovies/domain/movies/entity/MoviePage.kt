package com.github.gustavobarbosab.imovies.domain.movies.entity

data class MoviePage(
    val page: Int,
    val totalPages: Int,
    val movies: List<Movie>
)