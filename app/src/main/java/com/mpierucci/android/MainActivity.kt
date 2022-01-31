package com.mpierucci.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.mpierucci.android.redux.navigation.NavigationManager
import com.mpierucci.android.redux.search.SearchStore
import com.mpierucci.android.redux.search.composables.SearchScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DrinksTheme {
                Surface {

                    val navController = rememberNavController()
                    LaunchedEffect(navigationManager.commands) {
                        navigationManager.commands.collect { command ->
                            navController.navigate(command.destination)
                        }
                    }
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}