package com.mpierucci.android.redux.drink.domain

import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.ristretto.CoroutineTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class GetDrinksByNameUeCase : CoroutineTest() {

    @Test
    fun `fetches drink through repository`() = testDispatcher.runBlockingTest {
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