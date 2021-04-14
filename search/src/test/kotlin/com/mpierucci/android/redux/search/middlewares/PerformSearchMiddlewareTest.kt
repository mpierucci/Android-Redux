package com.mpierucci.android.redux.search.middlewares

import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.drink.domain.DrinkRepository
import com.mpierucci.android.redux.drink.domain.GetDrinksByNameUseCase
import com.mpierucci.android.redux.ristretto.CoroutineTest
import com.mpierucci.android.redux.search.SearchAction
import com.mpierucci.android.redux.search.SearchStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class PerformSearchMiddlewareTest : CoroutineTest() {


    @Test
    fun `fetches drinks and dispatch load result action on search action`() =
        testDispatcher.runBlockingTest {
            val drinks = listOf(
                Drink(
                    "id", "name", "tags", null, "", "", emptyList()
                )
            )
            val repository = mock<DrinkRepository>()
            given(repository.getByName("Margarita")).willReturn(drinks)
            val useCase = GetDrinksByNameUseCase(repository)
            val sut = PerformSearchMiddleware(useCase, dispatcherProvider)
            val store = SearchStore(dispatcherProvider, sut)
            val storeSpy = spy(store)

            val chain = sut(storeSpy)

            chain(SearchAction.Search("Margarita"))

            verify(storeSpy).dispatch(SearchAction.LoadSearchResults(drinks))
        }
}