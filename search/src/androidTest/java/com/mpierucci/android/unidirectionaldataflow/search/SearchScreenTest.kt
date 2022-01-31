package com.mpierucci.android.unidirectionaldataflow.search

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.drink.domain.DrinkError
import com.mpierucci.android.redux.search.SearchState
import com.mpierucci.android.redux.search.composables.SearchScreen
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun given_search_state_has_show_loading_true_then_progress_bar_is_shown() {
        val searchState = SearchState(loading = true)

        composeTestRule.setContent {
            SearchScreen(
                state = searchState,
                onQueryChanged = {},
                onSearch = {},
                onDrinkTapped = {}
            )
        }

        composeTestRule.onNode(hasTestTag("searchProgress"))
            .assertExists()

    }

    @Test
    fun given_search_state_has_show_loading_false_then_progress_bar_is_hidden() {
        val searchState = SearchState(loading = false)

        composeTestRule.setContent {
            SearchScreen(
                state = searchState,
                onQueryChanged = {},
                onSearch = {},
                onDrinkTapped = {}
            )
        }

        composeTestRule.onNode(hasTestTag("searchProgress"))
            .assertDoesNotExist()
    }

    @Test
    fun given_search_state_has_null_drinks_then_start_searching_section_is_shown_only() {
        val searchState = SearchState(drinks = null)

        composeTestRule.setContent {
            SearchScreen(
                state = searchState,
                onQueryChanged = {},
                onSearch = {},
                onDrinkTapped = {}
            )
        }

        composeTestRule.onNode(hasTestTag("startSearching")).assertExists()
        composeTestRule.onNode(hasTestTag("drinkList")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("errorScreen")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("emptyResults")).assertDoesNotExist()
    }

    @Test
    fun given_search_state_has_no_drinks_then_empty_results_section_is_shown_only() {
        val searchState = SearchState(drinks = emptyList())

        composeTestRule.setContent {
            SearchScreen(
                state = searchState,
                onQueryChanged = {},
                onSearch = {},
                onDrinkTapped = {}
            )
        }

        composeTestRule.onNode(hasTestTag("startSearching")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("drinkList")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("errorScreen")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("emptyResults")).assertExists()
    }

    @Test
    fun given_search_state_has_drinks_then_drink_list_is_shown_only() {
        val searchState =
            SearchState(
                drinks = listOf(Drink("id", "ma", "", null, "", "", emptyList()))
            )

        composeTestRule.setContent {
            SearchScreen(
                state = searchState,
                onQueryChanged = {},
                onSearch = {},
                onDrinkTapped = {}
            )
        }

        composeTestRule.onRoot().printToLog("Marco")

        composeTestRule.onNode(hasTestTag("startSearching")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("emptyResults")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("errorScreen")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("drinkList")).onChildren().assertCountEquals(1)
    }

    @Test
    fun given_search_state_has_error_then_error_section_is_shown_only() {
        val searchState = SearchState(drinks = emptyList(), error = DrinkError.Unknown)

        composeTestRule.setContent {
            SearchScreen(
                state = searchState,
                onQueryChanged = {},
                onSearch = {},
                onDrinkTapped = {}
            )
        }

        composeTestRule.onNode(hasTestTag("startSearching")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("drinkList")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("errorScreen")).assertExists()
        composeTestRule.onNode(hasTestTag("emptyResults")).assertDoesNotExist()
    }

    @Test
    fun given_search_state_query_then_query_is_shown() {
        val searchState = SearchState(query = "Daikiri")

        composeTestRule.setContent {
            SearchScreen(
                state = searchState,
                onQueryChanged = {},
                onSearch = {},
                onDrinkTapped = {}
            )
        }

        composeTestRule.onRoot().printToLog("Marco")
        composeTestRule.onNode(hasText("Daikiri")).assertExists()
    }

}