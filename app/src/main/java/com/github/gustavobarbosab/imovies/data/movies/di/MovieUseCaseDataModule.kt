package com.github.gustavobarbosab.imovies.data.movies.di

import com.github.gustavobarbosab.imovies.domain.movies.MoviesRepository
import com.github.gustavobarbosab.imovies.domain.movies.upcoming.UpcomingMoviesUseCase
import com.github.gustavobarbosab.imovies.domain.movies.upcoming.UpcomingMoviesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class MovieUseCaseDataModule {

    @Provides
    @ViewModelScoped
    fun bindUpcomingMovieUseCase(repository: MoviesRepository): UpcomingMoviesUseCase {
        return UpcomingMoviesUseCaseImpl(repository)
    }
}