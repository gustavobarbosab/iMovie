package com.github.gustavobarbosab.imovies.data.movies.di

import com.github.gustavobarbosab.imovies.data.movies.remote.MovieApi
import com.github.gustavobarbosab.imovies.data.movies.remote.MovieRemoteDataSource
import com.github.gustavobarbosab.imovies.data.movies.remote.MovieRemoteDataSourceImpl
import com.github.gustavobarbosab.imovies.data.movies.repository.MoviesListRepositoryImpl
import com.github.gustavobarbosab.imovies.domain.movies.detail.GetMovieDetailRepository
import com.github.gustavobarbosab.imovies.domain.movies.list.MoviesListRepository
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
    abstract fun bindMovieRepository(
        repository: MoviesListRepositoryImpl
    ): MoviesListRepository

    @Binds
    @Singleton
    abstract fun bindMovieRemoteDataSource(
        dataSource: MovieRemoteDataSourceImpl
    ): MovieRemoteDataSource

    @Binds
    abstract fun bindMoviesRepository(
        repository: MoviesListRepositoryImpl
    ): GetMovieDetailRepository

    companion object {
        @Provides
        @Singleton
        fun provideReleaseApi(
            retrofit: Retrofit
        ): MovieApi = retrofit.create(MovieApi::class.java)
    }
}