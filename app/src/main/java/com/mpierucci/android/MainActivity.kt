package com.mpierucci.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import com.mpierucci.android.redux.search.SearchStore
import com.mpierucci.android.redux.search.composables.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<SearchStore>()
        // https://proandroiddev.com/jetpack-compose-navigation-architecture-with-viewmodels-1de467f19e1c navigation
        // https://medium.com/google-developer-experts/modular-navigation-with-jetpack-compose-fda9f6b2bef7
        setContent {
            DrinksTheme {
                SearchScreen(store = viewModel)
            }
        }
    }
}