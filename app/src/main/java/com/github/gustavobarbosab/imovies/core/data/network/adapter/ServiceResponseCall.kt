package com.github.gustavobarbosab.imovies.core.data.network.adapter

import com.github.gustavobarbosab.imovies.core.data.network.NetworkResponse
import com.github.gustavobarbosab.imovies.core.data.network.adapter.mapper.mapToServiceResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceResponseCall<T : Any>(
    private val proxy: Call<T>,
) : Call<NetworkResponse<T>> {

    override fun enqueue(callback: Callback<NetworkResponse<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResult = response.mapToServiceResponse()
                callback.onResponse(this@ServiceResponseCall, Response.success(networkResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult = NetworkResponse.InternalException<T>(t)
                callback.onResponse(this@ServiceResponseCall, Response.success(networkResult))
            }
        })
    }

    override fun execute(): Response<NetworkResponse<T>> = throw NotImplementedError()

    override fun clone(): Call<NetworkResponse<T>> = ServiceResponseCall(proxy.clone())

    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() {
        proxy.cancel()
    }
}