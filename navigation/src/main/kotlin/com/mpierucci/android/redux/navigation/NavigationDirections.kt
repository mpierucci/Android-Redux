package com.mpierucci.android.redux.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object NavigationDirections {


    val search = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val destination: String = "search"
    }

    object DrinkDetails {
        const val DRINK_NAME_KEY = "drinkName"
        const val DRINK_DETAILS_PATH = "drinkDetails"
        const val DRINK_DETAILS_ROUTE = "$DRINK_DETAILS_PATH/{$DRINK_NAME_KEY}"

        fun drinkDetails(
            drinkName: String
        ) = object : NavigationCommand {
            override val arguments: List<NamedNavArgument> = listOf(
                navArgument(DRINK_NAME_KEY) {
                    type = NavType.StringType
                }
            )
            override val destination: String = "$DRINK_DETAILS_PATH/$drinkName"
        }
    }
}