package com.mpierucci.android.redux.core.data

import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mpierucci.android.redux.core.data.ClientError.*
import com.mpierucci.android.redux.core.data.retrofit.EitherCallAdapterFactory
import com.mpierucci.android.redux.core.data.retrofit.toEither
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET

class RetrofitCallbackEitherCallTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
    }

    @Test
    fun `successful response without body returns unit`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200))
        val service = createRetrofit().create(DummyInterface::class.java)

        val result = service.emptyBodyRequest().toEither()

        result.fold(
            { fail() },
            { body -> assertThat(body).isEqualTo(Unit) }
        )
    }

    @Test
    fun `successful response with body returns expected body`() = runTest {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(
                """
                {
                  "text": "expectedText"
                }
                 """.trimIndent()
            )
        )
        val service = createRetrofit().create(DummyInterface::class.java)

        val result = service.responseWithBody().toEither()

        result.fold(
            { fail() },
            { body -> assertThat(body.text).isEqualTo("expectedText") }
        )
    }

    @Test
    fun `bad request http response returns bad request http error`() = runTest {

        mockWebServer.enqueue(MockResponse().setResponseCode(400))

        val service = createRetrofit().create(DummyInterface::class.java)

        val result = service.emptyBodyRequest().toEither()

        result.fold(
            { error -> assertThat(error).isEqualTo(BadRequest("")) },
            { fail() }
        )
    }

    @Test
    fun `bad request http response without error body returns bad request http error with empty message`() =
        runTest {

            mockWebServer.enqueue(MockResponse().setResponseCode(400))

            val service = createRetrofit().create(DummyInterface::class.java)

            val result = service.emptyBodyRequest().toEither()

            result.fold(
                { error -> assertThat(error).isEqualTo(BadRequest("")) },
                { fail() }
            )
        }

    @Test
    fun `unauthorized http response without error body returns unauthorized http error`() =
        runTest {

            mockWebServer.enqueue(MockResponse().setResponseCode(401))

            val service = createRetrofit().create(DummyInterface::class.java)

            val result = service.emptyBodyRequest().toEither()

            result.fold(
                { error -> assertThat(error).isEqualTo(Unauthorized) },
                { fail() }
            )
        }

    @Test
    fun `not found http response without error body returns not foun http error`() =
        runTest {

            mockWebServer.enqueue(MockResponse().setResponseCode(404))

            val service = createRetrofit().create(DummyInterface::class.java)

            val result = service.emptyBodyRequest().toEither()

            result.fold(
                { error -> assertThat(error).isEqualTo(NotFound) },
                { fail() }
            )
        }


    @Test
    fun `forbidden http response returns forbidden http error`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(403))

        val service = createRetrofit().create(DummyInterface::class.java)

        val result = service.emptyBodyRequest().toEither()

        result.fold(
            { error -> assertThat(error).isEqualTo(Forbidden) },
            { fail() }
        )
    }

    @Test
    fun `unhandled 4xx http response returns other http client error`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(405))

        val service = createRetrofit().create(DummyInterface::class.java)

        val result = service.emptyBodyRequest().toEither()

        result.fold(
            { error -> assertThat(error).isEqualTo(Other(405)) },
            { fail() }
        )
    }

    @Test
    fun `500 min cap  http response returns server error`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val service = createRetrofit().create(DummyInterface::class.java)

        val result = service.emptyBodyRequest().toEither()

        result.fold(
            { error -> assertThat(error).isEqualTo(ServerError) },
            { fail() }
        )
    }

    @Test
    fun `500 max cap  http response returns server error`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(599))

        val service = createRetrofit().create(DummyInterface::class.java)

        val result = service.emptyBodyRequest().toEither()

        result.fold(
            { error -> assertThat(error).isEqualTo(ServerError) },
            { fail() }
        )
    }

    @Test
    fun `over 5xx unsuccessful  http response returns unknown error`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(600))

        val service = createRetrofit().create(DummyInterface::class.java)

        val result = service.emptyBodyRequest().toEither()

        result.fold(
            { error ->
                assertThat(error).isInstanceOf(UnknownHttpError::class.java)
                assertThat((error as UnknownHttpError).throwable)
                    .isInstanceOf(IllegalArgumentException::class.java)
                assertThat((error.throwable.message)).isEqualTo("Status code: 600 not handled")
            },
            { fail() }
        )
    }

    @Test
    fun `error with serialization returns serialization error`() = runTest {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(
                """
                {
                  "toxt": "expectedText"
                }
                 """.trimIndent()
            )
        )
        val service = createRetrofit().create(DummyInterface::class.java)

        val result = service.responseWithBody().toEither()

        result.fold(
            { error -> assertThat(error).isInstanceOf(SerializationError::class.java) },
            { fail() }
        )
    }

    @Test(expected = IllegalAccessError::class)
    fun `execute on call provided by a service throws exception`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(403))
        val service = createRetrofit().create(DummyInterface::class.java)

        val result = service.emptyBodyRequest().execute().body()!!

        result.fold({ fail() }, { fail() })
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun createRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true

        }
        return Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl("http://127.0.0.1:8080")
            .addConverterFactory(json.asConverterFactory(contentType))
            .addCallAdapterFactory(EitherCallAdapterFactory())
            .build()
    }

    interface DummyInterface {
        @GET("/empty-body")
        fun emptyBodyRequest(): Call<Either<HttpError, Unit>>

        @GET("/response-with-body")
        fun responseWithBody(): Call<Either<HttpError, DummyResponse>>

        @Serializable
        data class DummyResponse(val text: String)
    }
}