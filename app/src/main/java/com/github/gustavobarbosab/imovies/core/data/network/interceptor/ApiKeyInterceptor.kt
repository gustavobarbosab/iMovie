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
                "2072592a7c0e098f0ae84a1e87f6b4f0"
            ) // TODO add the key from the local properties
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        const val PARAM_API_KEY = "api_key"
    }
}
