package com.github.gustavobarbosab.imovies.core.data.network.adapter

import com.github.gustavobarbosab.imovies.core.domain.DomainResponse

sealed class NetworkResponse<T : Any> {
    class Success<T : Any>(val data: T) : NetworkResponse<T>()
    class EmptySuccess<T : Any>(val code: Int, val message: String?) : NetworkResponse<T>()
    class ExternalError<T : Any>(
        val code: Int,
        val message: String?,
    ) : NetworkResponse<T>()

    class InternalError<T : Any>(val throwable: Throwable) : NetworkResponse<T>()
}

inline fun <T : Any, R : Any> NetworkResponse<T>.map(transform: (T) -> R): NetworkResponse<R> {
    return when (this) {
        is NetworkResponse.Success -> NetworkResponse.Success(transform(this.data))
        is NetworkResponse.EmptySuccess -> NetworkResponse.EmptySuccess(this.code, this.message)
        is NetworkResponse.ExternalError -> NetworkResponse.ExternalError(
            this.code,
            this.message,
        )

        is NetworkResponse.InternalError -> NetworkResponse.InternalError(this.throwable)
    }
}

inline fun <T : Any, R : Any> NetworkResponse<T>.mapToDomain(
    transform: (T) -> R
): DomainResponse<R> {
    return when (this) {
        is NetworkResponse.Success -> DomainResponse.Success(transform(this.data))
        is NetworkResponse.EmptySuccess -> DomainResponse.EmptySuccess(this.code, this.message)
        is NetworkResponse.ExternalError -> DomainResponse.ExternalError(
            this.code,
            this.message,
        )

        is NetworkResponse.InternalError -> DomainResponse.InternalError(this.throwable)
    }
}