package com.github.gustavobarbosab.imovies.settings.di

import com.github.gustavobarbosab.imovies.core.data.di.NetworkParamsModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkParamsModuleForTests {

    @Provides
    @Singleton
    @Named(NetworkParamsModule.BASE_URL)
    fun provideBaseUrl() = "http://localhost:8080/"
}
