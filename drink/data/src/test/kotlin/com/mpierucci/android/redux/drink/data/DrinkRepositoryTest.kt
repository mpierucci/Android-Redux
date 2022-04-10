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
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class DrinkRepositoryTest {

    @get:Rule
    val coroutineRule = CoroutineTestDispatcherRule()

    @Test
    fun `fetches result from api`() = runTest {
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
    fun `maps network error into no connection error`() = runTest {
        val api = mock<DrinkApi>()
        val call = fakeErrorCall<DrinksByNameResponse>(NetworkError)
        given(api.getDrinksByName("name")).willReturn(call)

        val sut = DrinkRepository(api, TestDispatcherProvider(coroutineRule.testDispatcher))

        val expected: Either<DrinkError, List<Drink>> = Either.Left(DrinkError.NoConnection)

        val result = sut.getByName("name")

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `maps not handled  error into unknown error`() = runTest {
        val api = mock<DrinkApi>()
        val call = fakeErrorCall<DrinksByNameResponse>(ClientError.NotFound)
        given(api.getDrinksByName("name")).willReturn(call)

        val sut = DrinkRepository(api, TestDispatcherProvider(coroutineRule.testDispatcher))

        val expected: Either<DrinkError, List<Drink>> = Either.Left(DrinkError.Unknown)

        val result = sut.getByName("name")

        assertThat(result).isEqualTo(expected)
    }
}

