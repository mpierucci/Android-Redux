package com.mpierucci.android.redux.drink.domain

import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.ristretto.CoroutineTestDispatcherRule
import com.mpierucci.android.redux.ristretto.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

class GetDrinksByNameUeCaseTest {

    @get:Rule
    val coroutineRule = CoroutineTestDispatcherRule()

    @Test
    fun `fetches drink through repository`() = coroutineRule.runBlockingTest {
        val expected = listOf(
            Drink(
                "id", "name", "tags", null, "", "", emptyList()
            )
        )
        val repository = mock<DrinkRepository>()
        given(repository.getByName("Margarita")).willReturn(expected)

        val sut = GetDrinksByNameUseCase(repository)

        val result = sut.execute("Margarita")

        assertThat(result).isEqualTo(expected)
    }
}