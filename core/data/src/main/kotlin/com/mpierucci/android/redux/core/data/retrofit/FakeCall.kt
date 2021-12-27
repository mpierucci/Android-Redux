package com.mpierucci.android.redux.core.data.retrofit

import arrow.core.Either
import com.mpierucci.android.redux.core.data.HttpError
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Provides a fake [Call] implementation for repositories uni test.
 * Because enable supsend function for retrofit was not possible cause it breaks the integration
 * tests,we are opting to use call.execute wrapper in a coroutine dispatcher. So this is needed
 * when uni testing the repositories.
 *
 * This needs to be moved gto test source set once test fixutres is supported by android modules
 * or at the very least make sure proguard removes it
 *
 */
fun <T> fakeSuccessCall(body: T): Call<Either<HttpError, T>> = object : Call<Either<HttpError, T>> {
    override fun clone(): Call<Either<HttpError, T>> {
        TODO("Not yet implemented")
    }

    override fun execute(): Response<Either<HttpError, T>> {
        TODO("Not yet implemented")
    }

    override fun enqueue(callback: Callback<Either<HttpError, T>>) {
        callback.onResponse(this, Response.success(Either.right(body)))
    }

    override fun isExecuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun isCanceled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun request(): Request {
        TODO("Not yet implemented")
    }

    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }
}

fun <T> fakeErrorCall(error: HttpError): Call<Either<HttpError, T>> = object : Call<Either<HttpError, T>> {
    override fun clone(): Call<Either<HttpError, T>> {
        TODO("Not yet implemented")
    }

    override fun execute(): Response<Either<HttpError, T>> {
        TODO("Not yet implemented")
    }

    override fun enqueue(callback: Callback<Either<HttpError, T>>) {
        callback.onResponse(this, Response.success(Either.left(error)))
    }

    override fun isExecuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun isCanceled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun request(): Request {
        TODO("Not yet implemented")
    }

    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }
}

