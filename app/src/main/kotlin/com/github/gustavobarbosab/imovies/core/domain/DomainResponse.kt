package com.github.gustavobarbosab.imovies.core.domain

sealed class DomainResponse<T : Any> {
    class Success<T : Any>(val data: T) : DomainResponse<T>()
    class EmptySuccess<T : Any>(
        val code: Int? = null,
        val message: String? = null
    ) : DomainResponse<T>()

    class ExternalError<T : Any>(
        val code: Int,
        val message: String?,
    ) : DomainResponse<T>()

    class InternalError<T : Any>(val throwable: Throwable) : DomainResponse<T>()
}