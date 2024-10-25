package com.github.gustavobarbosab.imovies.core.data.network.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(
                PARAM_API_KEY,
                "eGX7mlWDp2s8CoLfrkorCYqyOSTIQVLaM9dSYlUl"
            ) // TODO add the key from the local properties
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        const val PARAM_API_KEY = "apiKey"
    }
}
