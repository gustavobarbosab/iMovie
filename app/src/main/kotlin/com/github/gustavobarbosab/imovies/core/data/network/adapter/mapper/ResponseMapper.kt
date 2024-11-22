package com.github.gustavobarbosab.imovies.core.data.network.adapter.mapper

import com.github.gustavobarbosab.imovies.core.data.network.adapter.NetworkResponse
import retrofit2.Response

fun <T : Any> Response<T>.mapToServiceResponse(): NetworkResponse<T> {
    val body = this.body()

    if (isSuccessful) {
        return if (body != null) {
            NetworkResponse.Success(body)
        } else {
            NetworkResponse.EmptySuccess(code = this.code(), message = this.message())
        }
    }

    return NetworkResponse.ExternalError(
        code = this.code(),
        message = this.message(),
    )
}


fun <T : Any> Response<T>.mapErrorToServiceResponse(): NetworkResponse<T> {
    return NetworkResponse.ExternalError(
        code = this.code(),
        message = this.message(),
    )
}