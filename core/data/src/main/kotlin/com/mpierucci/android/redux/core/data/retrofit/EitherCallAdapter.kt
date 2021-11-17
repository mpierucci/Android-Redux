package com.mpierucci.android.redux.core.data.retrofit

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class EitherCallAdapter<R>(
    private val successType: Type
) : CallAdapter<R, CallbackEitherCall<R>> {

    override fun adapt(call: Call<R>): CallbackEitherCall<R> {
        return CallbackEitherCall(call, successType)
    }

    override fun responseType(): Type = successType
}