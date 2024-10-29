package com.github.gustavobarbosab.imovies.domain.movies.list

import com.github.gustavobarbosab.imovies.core.domain.DomainResponse
import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage
import com.github.gustavobarbosab.imovies.domain.movies.list.GetMoviesListUseCase.Response

class GetMoviesListUseCaseImpl(
    private val moviesRepository: MoviesListRepository,
) : GetMoviesListUseCase.UpcomingMovies,
    GetMoviesListUseCase.PopularMovies,
    GetMoviesListUseCase.NowPlayingMovies,
    GetMoviesListUseCase.TopRatedMovies {

    override suspend fun getUpcomingMovies(page: Int): Response = getMovies {
        moviesRepository.getUpcomingMovies(page)
    }

    override suspend fun getTopRatedMovies(page: Int): Response = getMovies {
        moviesRepository.getTopRatedMovies(page)
    }

    override suspend fun getNowPlayingMovies(page: Int): Response = getMovies {
        moviesRepository.getNowPlayingMovies(page)
    }

    override suspend fun getPopularMovies(page: Int): Response = getMovies {
        moviesRepository.getPopularMovies(page)
    }

    // As the use cases have the same logic, I split them in different interfaces
    // it helps us to change the code in the future if we need to change the logic
    private suspend fun getMovies(request: suspend () -> DomainResponse<MoviePage>) =
        when (val response = request()) {
            is DomainResponse.Success -> handleSuccess(response.data)
            is DomainResponse.EmptySuccess -> Response.ThereIsNoMovies
            is DomainResponse.InternalError -> Response.Error(response.throwable.message)
            is DomainResponse.ExternalError -> Response.Error(response.message)
        }

    private fun handleSuccess(moviePage: MoviePage) = if (moviePage.movies.isEmpty()) {
        Response.ThereIsNoMovies
    } else {
        Response.Success(moviePage)
    }
}
