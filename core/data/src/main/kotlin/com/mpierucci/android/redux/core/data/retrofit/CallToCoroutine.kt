package com.mpierucci.android.redux.core.data.retrofit

import arrow.core.Either
import com.mpierucci.android.redux.core.data.HttpError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
suspend fun <T> Call<Either<HttpError, T>>.toEither() =
    suspendCoroutine<Either<HttpError, T>> { continuation ->

        enqueue(object : Callback<Either<HttpError, T>> {
            override fun onResponse(
                call: Call<Either<HttpError, T>>,
                response: Response<Either<HttpError, T>>
            ) {
                continuation.resume(response.body()!!)
            }

            override fun onFailure(call: Call<Either<HttpError, T>>, t: Throwable) {
                throw IllegalStateException(
                    """
                    Failure should already be handled by EitherCall.
                    Check that retrofit is implementing that call adapter.
                    """.trimIndent()
                )
            }
        })
    }