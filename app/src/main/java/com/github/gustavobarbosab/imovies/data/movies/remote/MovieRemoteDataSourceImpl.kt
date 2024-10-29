package com.github.gustavobarbosab.imovies.data.movies.remote

import com.github.gustavobarbosab.imovies.core.data.network.adapter.NetworkResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MoviePageResponse
import com.github.gustavobarbosab.imovies.data.movies.remote.response.MovieResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: MovieApi
) : MovieRemoteDataSource {

    override suspend fun getUpcomingMovies(page: Int): NetworkResponse<MoviePageResponse> {
        return api.getMovieList(MovieTypePath.Upcoming.path, page)
    }

    override suspend fun getPopularMovies(page: Int): NetworkResponse<MoviePageResponse> {
        // Simulate a delay to show the loading
        delay(2000)
        return api.getMovieList(MovieTypePath.Popular.path, page)
    }

    override suspend fun getTopRatedMovies(page: Int): NetworkResponse<MoviePageResponse> =
        api.getMovieList(MovieTypePath.TopRated.path, page)

    override suspend fun getNowPlayingMovies(page: Int): NetworkResponse<MoviePageResponse> {
        // Simulate a delay to show the loading
        delay(4000)
        return api.getMovieList(MovieTypePath.NowPlaying.path, page)
    }

    override suspend fun getMovieDetail(movieId: Long): NetworkResponse<MovieResponse> {
        // Simulate a delay to show the loading
        delay(2000)
        return api.getMovieDetail(movieId)
    }

    sealed class MovieTypePath(val path: String) {
        data object Upcoming : MovieTypePath("upcoming")
        data object Popular : MovieTypePath("popular")
        data object TopRated : MovieTypePath("top_rated")
        data object NowPlaying : MovieTypePath("now_playing")
    }
}
