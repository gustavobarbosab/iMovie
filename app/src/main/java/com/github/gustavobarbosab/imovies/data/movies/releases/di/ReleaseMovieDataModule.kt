package com.github.gustavobarbosab.imovies.data.movies.releases.di

import com.github.gustavobarbosab.imovies.data.movies.releases.remote.ReleaseMovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ReleaseMovieDataModule {

    @Provides
    @Singleton
    fun provideReleaseApi(
        retrofit: Retrofit
    ): ReleaseMovieApi = retrofit.create(ReleaseMovieApi::class.java)
}