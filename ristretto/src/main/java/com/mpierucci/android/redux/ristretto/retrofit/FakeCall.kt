package com.mpierucci.android.redux.ristretto.retrofit

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Provides a fake [Call] implementation for repositories uni test.
 * Because enable supsend function for retrofit was not possible cause it breaks the integration
 * tests,we are opting to use call.execute wrapper in a coroutine dispatcher. So this is needed
 * when uni testing the repositories. This may be obsolete once a custom call adapter is implemented.
 *
 */
fun <T> fakeSuccessCall(body: T): Call<T> = object : Call<T> {
    override fun clone(): Call<T> {
        TODO("Not yet implemented")
    }

    override fun execute(): Response<T> {
        return Response.success(body)
    }

    override fun enqueue(callback: Callback<T>) {
        TODO("Not yet implemented")
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

