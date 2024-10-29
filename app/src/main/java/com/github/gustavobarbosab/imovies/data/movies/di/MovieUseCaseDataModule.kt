package com.github.gustavobarbosab.imovies.data.movies.di

import com.github.gustavobarbosab.imovies.domain.movies.detail.GetMovieDetailRepository
import com.github.gustavobarbosab.imovies.domain.movies.detail.GetMovieDetailUseCase
import com.github.gustavobarbosab.imovies.domain.movies.detail.GetMovieDetailUseCaseImpl
import com.github.gustavobarbosab.imovies.domain.movies.list.GetMoviesListUseCase
import com.github.gustavobarbosab.imovies.domain.movies.list.GetMoviesListUseCaseImpl
import com.github.gustavobarbosab.imovies.domain.movies.list.MoviesListRepository
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
        useCase: GetMoviesListUseCaseImpl
    ): GetMoviesListUseCase.UpcomingMovies

    @Binds
    abstract fun bindPopularMoviesUseCase(
        useCase: GetMoviesListUseCaseImpl
    ): GetMoviesListUseCase.PopularMovies

    @Binds
    abstract fun bindTopRatedMovieUseCase(
        useCase: GetMoviesListUseCaseImpl
    ): GetMoviesListUseCase.TopRatedMovies

    @Binds
    abstract fun bindNowPlayingMovieUseCase(
        useCase: GetMoviesListUseCaseImpl
    ): GetMoviesListUseCase.NowPlayingMovies

    companion object {
        @Provides
        @ViewModelScoped
        fun provideGetMoviesUseCase(
            repository: MoviesListRepository,
        ): GetMoviesListUseCaseImpl {
            return GetMoviesListUseCaseImpl(repository)
        }

        @Provides
        @ViewModelScoped
        fun provideGetMovieDetailUseCase(
            repository: GetMovieDetailRepository,
        ): GetMovieDetailUseCase {
            return GetMovieDetailUseCaseImpl(repository)
        }
    }
}
