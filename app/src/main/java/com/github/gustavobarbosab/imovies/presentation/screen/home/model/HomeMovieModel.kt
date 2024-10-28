package com.github.gustavobarbosab.imovies.presentation.screen.home.model

data class HomeMoviePage(
    val lastPage: Boolean,
    val movies: List<HomeMovieModel>
)

data class HomeMovieModel(
    val id: Long,
    val title: String,
    val posterPath: String,
    val backdropPath: String
)
