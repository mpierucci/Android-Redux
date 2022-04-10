package com.mpierucci.android.redux.search.middlewares

import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.navigation.NavigationCommand
import com.mpierucci.android.redux.navigation.NavigationDirections.DrinkDetails
import com.mpierucci.android.redux.navigation.NavigationManager
import com.mpierucci.android.redux.ristretto.CoroutineTestDispatcherRule
import com.mpierucci.android.redux.search.SearchAction
import com.mpierucci.android.redux.search.SearchStore
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class NavigationMiddlewareTest {
    @get:Rule
    val coroutineRule = CoroutineTestDispatcherRule()

    @Test
    fun `navigates through navigation manager on navigate to drink detail action`() =
        runTest {

            val navigationManager = mock<NavigationManager>()
            val sut = NavigationMiddleware(navigationManager)
            val performSearchMiddleware = PerformSearchMiddleware(mock())
            val store =
                SearchStore(coroutineRule.testDispatcherProvider, performSearchMiddleware, sut)
            val chain = sut(store)
            val commandCaptor = argumentCaptor<NavigationCommand>()

            chain(SearchAction.NavigateToDrinkDetail("Margarita"))

            verify(navigationManager).navigate(commandCaptor.capture())

            commandCaptor.firstValue.run {
                assertThat(destination).isEqualTo("${DrinkDetails.DRINK_DETAILS_PATH}/Margarita")
            }
        }
}