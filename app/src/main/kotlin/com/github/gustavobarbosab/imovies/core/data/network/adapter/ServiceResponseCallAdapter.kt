package com.github.gustavobarbosab.imovies.core.data.network.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ServiceResponseCallAdapter(
    private val resultType: Type,
) : CallAdapter<Type, Call<NetworkResponse<Type>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<NetworkResponse<Type>> {
        return ServiceResponseCall(call)
    }
}
