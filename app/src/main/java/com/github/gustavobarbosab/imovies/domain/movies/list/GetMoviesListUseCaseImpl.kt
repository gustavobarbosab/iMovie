package com.github.gustavobarbosab.imovies.domain.movies.list

import com.github.gustavobarbosab.imovies.core.domain.DomainResponse
import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage
import com.github.gustavobarbosab.imovies.domain.movies.list.GetMoviesListUseCase.Result

class GetMoviesListUseCaseImpl(
    private val moviesRepository: MoviesListRepository,
) : GetMoviesListUseCase.UpcomingMovies,
    GetMoviesListUseCase.PopularMovies,
    GetMoviesListUseCase.NowPlayingMovies,
    GetMoviesListUseCase.TopRatedMovies {

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

    private suspend fun getMovies(request: suspend () -> DomainResponse<MoviePage>) =
        when (val response = request()) {
            is DomainResponse.Success -> handleSuccess(response.data)
            is DomainResponse.EmptySuccess -> Result.ThereIsNoMovies
            is DomainResponse.InternalError -> Result.Error(response.throwable.message)
            is DomainResponse.ExternalError -> Result.Error(response.message)
        }

    private fun handleSuccess(moviePage: MoviePage) = if (moviePage.movies.isEmpty()) {
        Result.ThereIsNoMovies
    } else {
        Result.Success(moviePage)
    }
}
