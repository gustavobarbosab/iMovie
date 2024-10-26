package com.github.gustavobarbosab.imovies.core.data.di

import com.github.gustavobarbosab.imovies.core.data.network.adapter.ServiceResponseCallAdapterFactory
import com.github.gustavobarbosab.imovies.core.data.network.interceptor.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/") // TODO add the base url from the local properties
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ServiceResponseCallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        // TODO change it...
        // Because of the new kotlin version the BuildConfig is not available anymore
//        level = if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor.Level.BODY
//        } else {
//            HttpLoggingInterceptor.Level.NONE
//        }
        level = HttpLoggingInterceptor.Level.BODY
    }
}