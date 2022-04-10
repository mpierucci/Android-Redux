package com.mpierucci.android.redux.integration.search

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.drink.domain.Ingredient
import com.mpierucci.android.redux.ristretto.mockwebserver.createResponse
import com.mpierucci.android.redux.search.SearchAction.AppendSearchQuery
import com.mpierucci.android.redux.search.SearchAction.Search
import com.mpierucci.android.redux.search.SearchState
import com.mpierucci.android.redux.search.SearchStore
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@HiltAndroidTest
internal class SearchStoreTest : StoreIntegrationTest() {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
    }

    @Test
    @Ignore("Review new test api in detail")
    fun `test input query and search with results flow_`() = runTest {
        mockWebServer.enqueue(createResponse(filePath = SEARCH_WITH_DRINKS_RESPONSE))
        val expectedInitialState = SearchState()
        val expectedAppendQueryState = expectedInitialState.copy(query = "Mar")
        val expectedSearchSubmittedState = expectedAppendQueryState.copy(loading = true)
        val expectedSearchResultState = expectedSearchSubmittedState.copy(
            loading = false,
            drinks = listOf(
                Drink(
                    id = "11728",
                    name = "Martini",
                    tags = "",
                    videoUrl = "https://www.youtube.com/watch?v=ApMR3IWYZHI",
                    instructions = "Straight: Pour all ingredients into mixing glass with ice cubes. Stir well. Strain in chilled martini cocktail glass. Squeeze oil from lemon peel onto the drink, or garnish with olive.",
                    thumbnail = "https://www.thecocktaildb.com/images/media/drink/71t8581504353095.jpg",
                    ingredients = listOf(
                        Ingredient("Gin", "1 2/3 oz "),
                        Ingredient("Dry Vermouth", "1/3 oz "),
                        Ingredient("Olive", "1 ")
                    )
                )
            )
        )
        val sut = getStore<SearchStore>()
        val results = mutableListOf<SearchState>()
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect {
                results.add(it)
            }
        }


        sut.dispatch(AppendSearchQuery("Mar"))
        sut.dispatch(Search("Mar"))


        val actualInitialState = results.first()
        val actualAppendQueryState = results[1]
        val actualSearchSubmittedState = results[2]
        val actualSearchQueryState = results.last()

        assertThat(actualInitialState).isEqualTo(expectedInitialState)
        assertThat(actualAppendQueryState).isEqualTo(expectedAppendQueryState)
        assertThat(actualSearchSubmittedState).isEqualTo(expectedSearchSubmittedState)
        runCurrent()
        assertThat(actualSearchQueryState).isEqualTo(expectedSearchResultState)

        job.cancel()
    }

    @Test
    fun `test input query and search with results flow`() = runBlocking {
        mockWebServer.enqueue(createResponse(filePath = SEARCH_WITH_DRINKS_RESPONSE))
        val expectedInitialState = SearchState()
        val expectedAppendQueryState = expectedInitialState.copy(query = "Mar")
        val expectedSearchSubmittedState = expectedAppendQueryState.copy(loading = true)
        val expectedSearchResultState = expectedSearchSubmittedState.copy(
            loading = false,
            drinks = listOf(
                Drink(
                    id = "11728",
                    name = "Martini",
                    tags = "",
                    videoUrl = "https://www.youtube.com/watch?v=ApMR3IWYZHI",
                    instructions = "Straight: Pour all ingredients into mixing glass with ice cubes. Stir well. Strain in chilled martini cocktail glass. Squeeze oil from lemon peel onto the drink, or garnish with olive.",
                    thumbnail = "https://www.thecocktaildb.com/images/media/drink/71t8581504353095.jpg",
                    ingredients = listOf(
                        Ingredient("Gin", "1 2/3 oz "),
                        Ingredient("Dry Vermouth", "1/3 oz "),
                        Ingredient("Olive", "1 ")
                    )
                )
            )
        )
        val sut = getStore<SearchStore>()

        sut.state.test {
            assertThat(awaitItem()).isEqualTo(expectedInitialState)
            sut.dispatch(AppendSearchQuery("Mar"))
            assertThat(awaitItem()).isEqualTo(expectedAppendQueryState)
            sut.dispatch(Search("Mar"))
            assertThat(awaitItem()).isEqualTo(expectedSearchSubmittedState)
            assertThat(awaitItem()).isEqualTo(expectedSearchResultState)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private companion object {
        const val SEARCH_WITH_DRINKS_RESPONSE = "search/search_with_drinks_response.json"
    }
}