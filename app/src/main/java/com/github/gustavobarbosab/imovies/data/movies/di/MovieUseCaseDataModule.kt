package com.github.gustavobarbosab.imovies.data.movies.di

import com.github.gustavobarbosab.imovies.domain.movies.MoviesRepository
import com.github.gustavobarbosab.imovies.domain.movies.GetMoviesUseCase
import com.github.gustavobarbosab.imovies.domain.movies.GetMoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MovieUseCaseDataModule {

    @Binds
    abstract fun bindUpcomingMovieUseCase(
        useCase: GetMoviesUseCaseImpl
    ): GetMoviesUseCase.UpcomingMovies

    @Binds
    abstract fun bindTopRatedMovieUseCase(
        useCase: GetMoviesUseCaseImpl
    ): GetMoviesUseCase.TopRatedMovies

    @Binds
    abstract fun bindNowPlayingMovieUseCase(
        useCase: GetMoviesUseCaseImpl
    ): GetMoviesUseCase.NowPlayingMovies

    @Binds
    abstract fun bindPopularMovieUseCase(
        useCase: GetMoviesUseCaseImpl
    ): GetMoviesUseCase.PopularMovies

    companion object {
        @Provides
        @ViewModelScoped
        fun bindUpcomingMovieUseCase(
            repository: MoviesRepository,
        ): GetMoviesUseCaseImpl {
            return GetMoviesUseCaseImpl(repository)
        }
    }
}
