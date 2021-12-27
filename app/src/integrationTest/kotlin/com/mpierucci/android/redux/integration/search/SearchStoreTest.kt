package com.mpierucci.android.redux.integration.search

import com.google.common.truth.Truth.assertThat
import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.drink.domain.Ingredient
import com.mpierucci.android.redux.ristretto.mockwebserver.createResponse
import com.mpierucci.android.redux.ristretto.runBlockingTest
import com.mpierucci.android.redux.search.SearchAction.AppendSearchQuery
import com.mpierucci.android.redux.search.SearchAction.Search
import com.mpierucci.android.redux.search.SearchState
import com.mpierucci.android.redux.search.SearchStore
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@HiltAndroidTest
// TODO https://github.com/mpierucci/Android-Redux/issues/11
internal class SearchStoreTest : StoreIntegrationTest() {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
    }

    @Test
    @Ignore("Waiting for fix in coroutine test library")
    fun `test input query and search with results flow`() =
        coroutineRule.runBlockingTest {
            mockWebServer.enqueue(createResponse(filePath = SEARCH_WITH_DRINKS_RESPONSE))
            val expectedInitialState = SearchState()
            val expectedAppendQueryState = SearchState(query = "Mar")
            val expectedSearchQueryState = SearchState(
                query = "Mar",
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
            val job = launch {
                sut.state.toList(results)
            }

            sut.dispatch(AppendSearchQuery("Mar"))
            sut.dispatch(Search("Mar"))

            val actualInitialState = results.first()
            val actualAppendQueryState = results[1]
            val actualSearchQueryState = results.last()

            assertThat(actualInitialState).isEqualTo(expectedInitialState)
            assertThat(actualAppendQueryState).isEqualTo(expectedAppendQueryState)
            assertThat(actualSearchQueryState).isEqualTo(expectedSearchQueryState)

            job.cancel()
        }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private companion object {
        const val SEARCH_WITH_DRINKS_RESPONSE = "search/search_with_drinks_response.json"
    }
}



