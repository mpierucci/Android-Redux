package com.mpierucci.android.unidirectionaldataflow.search

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import com.mpierucci.android.redux.drink.domain.DrinkError
import com.mpierucci.android.redux.search.composables.ErrorScreen
import org.junit.Rule
import org.junit.Test

class ErrorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun given_no_connection_error_then_display_connection_mesage() {
        val error = DrinkError.NoConnection

        composeTestRule.setContent {
            ErrorScreen(error = error)
        }

        composeTestRule.onNode(hasText("Something went wrong. Check your connection"))
            .assertExists()
    }

    @Test
    fun given_unknown_error_then_display_connection_mesage() {
        val error = DrinkError.Unknown

        composeTestRule.setContent {
            ErrorScreen(error = error)
        }

        composeTestRule.onNode(hasText("Something went wrong. Try again later"))
            .assertExists()
    }
}