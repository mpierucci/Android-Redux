package com.mpierucci.android.redux.navigation

import androidx.navigation.NamedNavArgument

object NavigationDirections {
    val search = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val destination: String = "search"
    }
}