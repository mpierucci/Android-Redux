package com.mpierucci.android.redux.drink.data

import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.ristretto.CoroutineTestDispatcherRule
import com.mpierucci.android.redux.ristretto.TestDispatcherProvider
import com.mpierucci.android.redux.ristretto.retrofit.fakeSuccessCall
import com.mpierucci.android.redux.ristretto.runBlockingTest
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.junit.Test


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

        val expected = emptyList<Drink>()

        val result = sut.getByName("name")

        assertThat(result).isEqualTo(expected)
    }
}

