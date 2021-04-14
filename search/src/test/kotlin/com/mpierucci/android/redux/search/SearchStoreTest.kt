package com.mpierucci.android.redux.search

import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.drink.domain.DrinkRepository
import com.mpierucci.android.redux.drink.domain.GetDrinksByNameUseCase
import com.mpierucci.android.redux.ristretto.CoroutineTest
import com.mpierucci.android.redux.search.middlewares.PerformSearchMiddleware
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class SearchStoreTest : CoroutineTest() {


    @Test
    fun `appends query action emits query on state`() {
        val repository = mock<DrinkRepository>()
        val useCase = GetDrinksByNameUseCase(repository)
        val sut =
            SearchStore(dispatcherProvider, PerformSearchMiddleware(useCase, dispatcherProvider))

        sut.dispatch(SearchAction.AppendSearchQuery("Query"))

        val result = sut.state.value

        val expected = SearchState(query = "Query")

        assertThat(result).isEqualTo(expected)
    }


    @Test
    fun `load search results action emits drink results on state`() {
        val drinks = listOf(
            Drink(
                "id", "name", "tags", null, "", "", emptyList()
            )
        )
        val repository = mock<DrinkRepository>()
        val useCase = GetDrinksByNameUseCase(repository)
        val sut =
            SearchStore(dispatcherProvider, PerformSearchMiddleware(useCase, dispatcherProvider))

        sut.dispatch(SearchAction.LoadSearchResults(drinks))

        val result = sut.state.value

        val expected = SearchState(drinks = drinks)

        assertThat(result).isEqualTo(expected)
    }

}