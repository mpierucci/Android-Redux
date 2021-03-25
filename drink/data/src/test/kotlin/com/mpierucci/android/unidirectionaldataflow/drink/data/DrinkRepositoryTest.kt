package com.mpierucci.android.unidirectionaldataflow.drink.data

import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.unidirectionaldatafloew.ristretto.CoroutineTest
import com.mpierucci.android.unidirectionaldatafloew.ristretto.TestDispatcherProvider
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test


@ExperimentalCoroutinesApi
class DrinkRepositoryTest : CoroutineTest() {

    @Test
    fun `fetches result from api`() = testDispatcher.runBlockingTest {
        val api = mock<DrinkApi>()
        val response = DrinksByNameResponse(emptyList())
        given(api.getDrinksByName("name")).willReturn(response)

        val sut = DrinkRepository(api, TestDispatcherProvider(testDispatcher))

        val expected = emptyList<Drink>()

        val result = sut.getByName("name")

        assertThat(result).isEqualTo(expected)
    }
}