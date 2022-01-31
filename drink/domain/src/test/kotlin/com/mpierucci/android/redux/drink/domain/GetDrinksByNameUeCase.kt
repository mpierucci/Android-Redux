package com.mpierucci.android.redux.drink.domain

import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.ristretto.CoroutineTestDispatcherRule
import com.mpierucci.android.redux.ristretto.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

class GetDrinksByNameUeCaseTest {

    @get:Rule
    val coroutineRule = CoroutineTestDispatcherRule()

    @Test
    fun `fetches drink through repository`() = coroutineRule.runBlockingTest {
        val drinks = listOf(
            Drink(
                "id", "name", "tags", null, "", "", emptyList()
            )
        )
        val expected = Either.Right(drinks)
        val repository = mock<DrinkRepository>()
        given(repository.getByName("Margarita")).willReturn(expected)

        val sut = GetDrinksByNameUseCase(repository)

        val result = sut("Margarita")

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `fetches drink through repository and return error`() = coroutineRule.runBlockingTest {
        val expected = Either.Left(DrinkError.Unknown)
        val repository = mock<DrinkRepository>()
        given(repository.getByName("Margarita")).willReturn(expected)

        val sut = GetDrinksByNameUseCase(repository)

        val result = sut("Margarita")

        assertThat(result).isEqualTo(expected)
    }
}