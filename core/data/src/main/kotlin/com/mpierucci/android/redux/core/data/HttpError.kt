package com.mpierucci.android.redux.core.data

sealed class HttpError

sealed class ClientError : HttpError() {
    data class BadRequest(val message: String) : ClientError()
    object Unauthorized : ClientError()
    object Forbidden : ClientError()
    object NotFound : ClientError()
    data class Other(val code: Int): ClientError()
}

object NetworkError : HttpError()

object ServerError : HttpError()

data class SerializationError(val throwable: Throwable) : HttpError()

data class UnknownHttpError(val throwable: Throwable) : HttpError()