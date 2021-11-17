package com.mpierucci.android.redux.core.data.retrofit

import arrow.core.Either
import com.mpierucci.android.redux.core.data.HttpError
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class EitherCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        /*
          We'll only handle services request that expect a type Either as a result.
         */
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be a parameterized type." }


        //once we identified the call we treat etiher
        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != Either::class.java) return null
        check(responseType is ParameterizedType) { "Response type must be a parameterized type." }

        /*
          Once we know the return type is a Either, we check that the left type of the either is HttpError
         */
        val leftType = getParameterUpperBound(0, responseType)
        if (getRawType(leftType) != HttpError::class.java) return null

        /*
           Then we get the right type of the either which is the expected success type.
         */
        val rightType = getParameterUpperBound(1, responseType)
        return EitherCallAdapter<Any>(rightType)
    }
}