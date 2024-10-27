package com.github.gustavobarbosab.imovies.data.movies.remote

import com.github.gustavobarbosab.imovies.core.domain.network.NetworkResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MoviePageResponse
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: MovieApi
) : MovieRemoteDataSource {

    override suspend fun getUpcomingMovies(page: Int): NetworkResponse<MoviePageResponse> =
        api.getMovieList(MovieTypePath.Upcoming.path, page)

    override suspend fun getPopularMovies(page: Int): NetworkResponse<MoviePageResponse> =
        api.getMovieList(MovieTypePath.Popular.path, page)

    override suspend fun getTopRatedMovies(page: Int): NetworkResponse<MoviePageResponse> =
        api.getMovieList(MovieTypePath.TopRated.path, page)

    override suspend fun getNowPlayingMovies(page: Int): NetworkResponse<MoviePageResponse> =
        api.getMovieList(MovieTypePath.NowPlaying.path, page)

    sealed class MovieTypePath(val path: String) {
        data object Upcoming : MovieTypePath("upcoming")
        data object Popular : MovieTypePath("popular")
        data object TopRated : MovieTypePath("top_rated")
        data object NowPlaying : MovieTypePath("now_playing")
    }
}
