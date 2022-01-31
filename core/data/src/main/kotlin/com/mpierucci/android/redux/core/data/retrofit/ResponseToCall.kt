package com.mpierucci.android.redux.core.data.retrofit

import arrow.core.Either
import com.mpierucci.android.redux.core.data.ClientError
import com.mpierucci.android.redux.core.data.HttpError
import com.mpierucci.android.redux.core.data.ServerError
import com.mpierucci.android.redux.core.data.UnknownHttpError
import retrofit2.Response
import java.lang.reflect.Type

internal fun <T> Response<T>.toEither(successType: Type): Either<HttpError, T> {
    /*
     *400 and 500 are not failures for retrofit so we need ot handle it here
     */
    if (!isSuccessful) {
        return Either.Left(toNetworkError())
    }

    body()?.let { body ->
        return Either.Right(body)
    }

    return if (successType == Unit::class.java) {
        // Unit as success type means we expect no response body
        @Suppress("UNCHECKED_CAST")
        Either.Right(Unit) as Either<HttpError, T>
    } else {
        val error = IllegalStateException("response body was null but Unit was not success type")
        Either.Left(UnknownHttpError(error))
    }
}

/*
    Utility function  to convert unsuccessful requests, which retrofit marks as 400-500 status code,
    into NetworkError
 */
private fun <T> Response<T>.toNetworkError(): HttpError {
    return when (val statusCode = code()) {
        400 -> ClientError.BadRequest(errorBody()?.string().orEmpty())
        401 -> ClientError.Unauthorized
        403 -> ClientError.Forbidden
        404 -> ClientError.NotFound
        in 405..499 -> ClientError.Other(statusCode)
        in 500..599 -> ServerError
        else -> UnknownHttpError(IllegalArgumentException("Status code: $statusCode not handled"))
    }
}