package com.github.gustavobarbosab.imovies.core.data.network

sealed class NetworkResponse<T : Any> {
    class Success<T : Any>(val data: T) : NetworkResponse<T>()
    class EmptySuccess<T : Any>(val code: Int, val message: String?) : NetworkResponse<T>()
    class ServerException<T : Any>(
        val code: Int,
        val message: String?,
        val body: okhttp3.ResponseBody? // If the server is returning an standard error body, we can parse it here and return to the previous layers
    ) : NetworkResponse<T>()

    class InternalException<T : Any>(val e: Throwable) : NetworkResponse<T>()
}