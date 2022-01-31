package com.mpierucci.android.redux.search

import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.drink.domain.DrinkError
import com.mpierucci.android.redux.drink.domain.DrinkRepository
import com.mpierucci.android.redux.drink.domain.GetDrinksByNameUseCase
import com.mpierucci.android.redux.ristretto.CoroutineTestDispatcherRule
import com.mpierucci.android.redux.search.middlewares.PerformSearchMiddleware
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

class SearchStoreTest {

    @get:Rule
    val coroutineRule = CoroutineTestDispatcherRule()


    @Test
    fun `search store initial state is correct`() {
        val repository = mock<DrinkRepository>()
        val useCase = GetDrinksByNameUseCase(repository)
        val sut =
            SearchStore(
                coroutineRule.testDispatcherProvider,
                PerformSearchMiddleware(useCase, coroutineRule.testDispatcherProvider)
            )
        val expected = SearchState()

        val result = sut.state.value

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `appends query action emits query on state`() {
        val repository = mock<DrinkRepository>()
        val useCase = GetDrinksByNameUseCase(repository)
        val sut =
            SearchStore(
                coroutineRule.testDispatcherProvider,
                PerformSearchMiddleware(useCase, coroutineRule.testDispatcherProvider)
            )

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
            SearchStore(
                coroutineRule.testDispatcherProvider,
                PerformSearchMiddleware(useCase, coroutineRule.testDispatcherProvider)
            )

        sut.dispatch(SearchAction.LoadSearchResults(drinks))

        val result = sut.state.value

        val expected = SearchState(drinks = drinks)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `load search results action emits loading false on state`() {
        val drinks = listOf(
            Drink(
                "id", "name", "tags", null, "", "", emptyList()
            )
        )
        val repository = mock<DrinkRepository>()
        val useCase = GetDrinksByNameUseCase(repository)
        val sut =
            SearchStore(
                coroutineRule.testDispatcherProvider,
                PerformSearchMiddleware(useCase, coroutineRule.testDispatcherProvider)
            )

        sut.dispatch(SearchAction.Search(""))
        sut.dispatch(SearchAction.LoadSearchResults(drinks))

        val result = sut.state.value

        val expected = SearchState(drinks = drinks, loading = false)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `load search results action emits null error on state`() {
        val drinks = listOf(
            Drink(
                "id", "name", "tags", null, "", "", emptyList()
            )
        )
        val repository = mock<DrinkRepository>()
        val useCase = GetDrinksByNameUseCase(repository)
        val sut =
            SearchStore(
                coroutineRule.testDispatcherProvider,
                PerformSearchMiddleware(useCase, coroutineRule.testDispatcherProvider)
            )

        sut.dispatch(SearchAction.DisplayError(DrinkError.Unknown))
        sut.dispatch(SearchAction.LoadSearchResults(drinks))

        val result = sut.state.value

        val expected = SearchState(drinks = drinks, error = null)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `search action emits loading true on state`() {
        val repository = mock<DrinkRepository>()
        val useCase = GetDrinksByNameUseCase(repository)
        val sut =
            SearchStore(
                coroutineRule.testDispatcherProvider,
                PerformSearchMiddleware(useCase, coroutineRule.testDispatcherProvider)
            )
        val expected = SearchState(loading = true)

        sut.dispatch(SearchAction.Search("query"))

        val result = sut.state.value

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `display error action emits error and  loading false on state`() {
        val repository = mock<DrinkRepository>()
        val useCase = GetDrinksByNameUseCase(repository)
        val sut =
            SearchStore(
                coroutineRule.testDispatcherProvider,
                PerformSearchMiddleware(useCase, coroutineRule.testDispatcherProvider)
            )
        val expected = SearchState(loading = false, error = DrinkError.NoConnection)

        sut.dispatch(SearchAction.DisplayError(DrinkError.NoConnection))

        val result = sut.state.value

        assertThat(result).isEqualTo(expected)
    }
}