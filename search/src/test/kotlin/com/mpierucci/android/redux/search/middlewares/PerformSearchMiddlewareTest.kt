package com.mpierucci.android.redux.search.middlewares

import arrow.core.Either
import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.drink.domain.DrinkError
import com.mpierucci.android.redux.drink.domain.DrinkRepository
import com.mpierucci.android.redux.drink.domain.GetDrinksByNameUseCase
import com.mpierucci.android.redux.ristretto.CoroutineTestDispatcherRule
import com.mpierucci.android.redux.search.SearchAction
import com.mpierucci.android.redux.search.SearchAction.DisplayError
import com.mpierucci.android.redux.search.SearchAction.LoadSearchResults
import com.mpierucci.android.redux.search.SearchStore
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify

class PerformSearchMiddlewareTest {

    @get:Rule
    val coroutineRule = CoroutineTestDispatcherRule()

    @Test
    fun `fetches drinks and dispatch load result action on search action when use case return list of drinks`() =
        runTest {
            val drinks = listOf(
                Drink(
                    "id", "name", "tags", null, "", "", emptyList()
                )
            )

            val repository = mock<DrinkRepository>()
            given(repository.getByName("Margarita")).willReturn(Either.Right(drinks))
            val useCase = GetDrinksByNameUseCase(repository)
            val sut = PerformSearchMiddleware(useCase)
            val store = SearchStore(coroutineRule.testDispatcherProvider, sut,createNavigationMiddleWare() )
            val storeSpy = spy(store)


            val chain = sut(storeSpy)

            chain(SearchAction.Search("Margarita"))

            verify(storeSpy).dispatch(LoadSearchResults(drinks))
        }


    @Test
    fun `fetches drinks and dispatch display error action when use case return an error`() =
        runTest {
            val repository = mock<DrinkRepository>()
            given(repository.getByName("Margarita")).willReturn(Either.Left(DrinkError.Unknown))
            val useCase = GetDrinksByNameUseCase(repository)
            val sut = PerformSearchMiddleware(useCase)
            val store = SearchStore(coroutineRule.testDispatcherProvider, sut, createNavigationMiddleWare())
            val storeSpy = spy(store)
            val expected = DisplayError(DrinkError.Unknown)


            val chain = sut(storeSpy)

            chain(SearchAction.Search("Margarita"))

            verify(storeSpy).dispatch(expected)
        }


    private fun createNavigationMiddleWare() = NavigationMiddleware(mock())
}
