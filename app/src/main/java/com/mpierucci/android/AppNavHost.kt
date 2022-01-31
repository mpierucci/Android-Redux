package com.mpierucci.android

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mpierucci.android.redux.navigation.NavigationDirections
import com.mpierucci.android.redux.search.composables.SearchScreen

@Composable

fun AppNavHost(
    navController: NavHostController
) = NavHost(
    navController = navController,
    startDestination = NavigationDirections.search.destination
) {
    composable(NavigationDirections.search.destination) {
        SearchScreen(store = hiltViewModel())
    }
}