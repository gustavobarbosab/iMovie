package com.github.gustavobarbosab.imovies.data.movies.di

import com.github.gustavobarbosab.imovies.data.movies.remote.MovieApi
import com.github.gustavobarbosab.imovies.data.movies.repository.MovieRepositoryImpl
import com.github.gustavobarbosab.imovies.domain.movies.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieSingletonDataModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MoviesRepository

    companion object {
        @Provides
        @Singleton
        fun provideReleaseApi(
            retrofit: Retrofit
        ): MovieApi = retrofit.create(MovieApi::class.java)
    }
}