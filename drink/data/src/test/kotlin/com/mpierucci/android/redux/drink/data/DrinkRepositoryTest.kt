package com.mpierucci.android.redux.drink.data

import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.core.data.ClientError
import com.mpierucci.android.redux.core.data.NetworkError
import com.mpierucci.android.redux.core.data.retrofit.fakeErrorCall
import com.mpierucci.android.redux.core.data.retrofit.fakeSuccessCall
import com.mpierucci.android.redux.drink.domain.DrinkError
import com.mpierucci.android.redux.ristretto.CoroutineTestDispatcherRule
import com.mpierucci.android.redux.ristretto.TestDispatcherProvider
import com.mpierucci.android.redux.ristretto.runBlockingTest
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.junit.Test

//TODO https://github.com/mpierucci/Android-Redux/issues/11
class DrinkRepositoryTest {

    @get:Rule
    val coroutineRule = CoroutineTestDispatcherRule()

    @Test
    fun `fetches result from api`() = coroutineRule.runBlockingTest {
        val api = mock<DrinkApi>()
        val response = DrinksByNameResponse(emptyList())
        val call = fakeSuccessCall(response)
        given(api.getDrinksByName("name")).willReturn(call)

        val sut = DrinkRepository(api, TestDispatcherProvider(coroutineRule.testDispatcher))

        val expected: Either<DrinkError, List<Drink>> = Either.Right(emptyList())

        val result = sut.getByName("name")

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `maps network error into no connection error`() = coroutineRule.runBlockingTest {
        val api = mock<DrinkApi>()
        val call = fakeErrorCall<DrinksByNameResponse>(NetworkError)
        given(api.getDrinksByName("name")).willReturn(call)

        val sut = DrinkRepository(api, TestDispatcherProvider(coroutineRule.testDispatcher))

        val expected: Either<DrinkError, List<Drink>> = Either.Left(DrinkError.NoConnection)

        val result = sut.getByName("name")

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `maps not handled  error into unknown error`() = coroutineRule.runBlockingTest {
        val api = mock<DrinkApi>()
        val call = fakeErrorCall<DrinksByNameResponse>(ClientError.NotFound)
        given(api.getDrinksByName("name")).willReturn(call)

        val sut = DrinkRepository(api, TestDispatcherProvider(coroutineRule.testDispatcher))

        val expected: Either<DrinkError, List<Drink>> = Either.Left(DrinkError.Unknown)

        val result = sut.getByName("name")

        assertThat(result).isEqualTo(expected)
    }
}

