package com.github.gustavobarbosab.imovies.domain.movies

import com.github.gustavobarbosab.imovies.core.domain.network.NetworkResponse
import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage
import com.github.gustavobarbosab.imovies.domain.movies.GetMoviesUseCase.Result

class GetMoviesUseCaseImpl(
    private val moviesRepository: MoviesRepository,
) : GetMoviesUseCase.UpcomingMovies,
    GetMoviesUseCase.PopularMovies,
    GetMoviesUseCase.NowPlayingMovies,
    GetMoviesUseCase.TopRatedMovies {

    override suspend fun getUpcomingMovies(page: Int): Result = getMovies {
        moviesRepository.getUpcomingMovies(page)
    }

    override suspend fun getTopRatedMovies(page: Int): Result = getMovies {
        moviesRepository.getTopRatedMovies(page)
    }

    override suspend fun getNowPlayingMovies(page: Int): Result = getMovies {
        moviesRepository.getNowPlayingMovies(page)
    }

    override suspend fun getPopularMovies(page: Int): Result = getMovies {
        moviesRepository.getPopularMovies(page)
    }

    private suspend fun getMovies(request: suspend () -> NetworkResponse<MoviePage>) =
        when (val response = request()) {
            is NetworkResponse.Success -> handleSuccess(response.data)
            is NetworkResponse.EmptySuccess -> Result.ThereIsNoMovies
            is NetworkResponse.InternalException -> Result.Error(response.throwable.message)
            is NetworkResponse.ServerException -> Result.Error(response.message)
        }

    private fun handleSuccess(moviePage: MoviePage) = if (moviePage.movies.isEmpty()) {
        Result.ThereIsNoMovies
    } else {
        Result.Success(moviePage)
    }
}
