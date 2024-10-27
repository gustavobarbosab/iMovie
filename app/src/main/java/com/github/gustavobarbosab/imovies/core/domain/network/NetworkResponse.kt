package com.github.gustavobarbosab.imovies.core.domain.network

sealed class NetworkResponse<T : Any> {
    class Success<T : Any>(val data: T) : NetworkResponse<T>()
    class EmptySuccess<T : Any>(val code: Int, val message: String?) : NetworkResponse<T>()
    class ServerException<T : Any>(
        val code: Int,
        val message: String?,
        val body: okhttp3.ResponseBody? // If the server is returning an standard error body, we can parse it here and return to the previous layers
    ) : NetworkResponse<T>()

    class InternalException<T : Any>(val throwable: Throwable) : NetworkResponse<T>()
}

inline fun <T : Any, R : Any> NetworkResponse<T>.map(transform: (T) -> R): NetworkResponse<R> {
    return when (this) {
        is NetworkResponse.Success -> NetworkResponse.Success(transform(this.data))
        is NetworkResponse.EmptySuccess -> NetworkResponse.EmptySuccess(this.code, this.message)
        is NetworkResponse.ServerException -> NetworkResponse.ServerException(
            this.code,
            this.message,
            this.body
        )

        is NetworkResponse.InternalException -> NetworkResponse.InternalException(this.throwable)
    }
}