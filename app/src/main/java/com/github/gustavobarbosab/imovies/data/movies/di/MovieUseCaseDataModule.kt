package com.github.gustavobarbosab.imovies.data.movies.di

import com.github.gustavobarbosab.imovies.domain.movies.MoviesRepository
import com.github.gustavobarbosab.imovies.domain.movies.GetMoviesUseCaseContract
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
    ): GetMoviesUseCaseContract.UpcomingMoviesUseCase

    @Binds
    abstract fun bindTopRatedMovieUseCase(
        useCase: GetMoviesUseCaseImpl
    ): GetMoviesUseCaseContract.TopRatedMoviesUseCase

    @Binds
    abstract fun bindNowPlayingMovieUseCase(
        useCase: GetMoviesUseCaseImpl
    ): GetMoviesUseCaseContract.NowPlayingMoviesUseCase

    @Binds
    abstract fun bindPopularMovieUseCase(
        useCase: GetMoviesUseCaseImpl
    ): GetMoviesUseCaseContract.PopularMoviesUseCase

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
