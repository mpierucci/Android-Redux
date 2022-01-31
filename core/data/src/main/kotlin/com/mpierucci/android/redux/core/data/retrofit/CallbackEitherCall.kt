package com.mpierucci.android.redux.core.data.retrofit

import arrow.core.Either
import arrow.core.left
import com.mpierucci.android.redux.core.data.HttpError
import com.mpierucci.android.redux.core.data.NetworkError
import com.mpierucci.android.redux.core.data.SerializationError
import com.mpierucci.android.redux.core.data.UnknownHttpError
import kotlinx.serialization.SerializationException
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * Call wrapper to handle retrofit errors and return Either<HttpError,T>
 *
 * This call relies son [Call.enqueue] mechanism to handle results.
 *
 * Do not call execute directly.
 */
internal class CallbackEitherCall<T>(
    private val delegate: Call<T>,
    private val successType: Type
) : Call<Either<HttpError, T>> {

    override fun clone(): Call<Either<HttpError, T>> {
        return CallbackEitherCall(delegate.clone(), successType)
    }

    override fun execute(): Response<Either<HttpError, T>> {
        throw IllegalAccessError("Do not call execute on this instance")
    }

    override fun enqueue(callback: Callback<Either<HttpError, T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onResponse(
                    this@CallbackEitherCall,
                    Response.success(response.toEither(successType))
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val error = when (t) {
                    is IOException -> NetworkError
                    is SerializationException ->SerializationError(t)
                    else -> UnknownHttpError(t)
                }

                callback.onResponse(this@CallbackEitherCall, Response.success(Either.Left(error)))
            }
        })
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }
}
