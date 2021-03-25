package com.mpierucci.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import com.mpierucci.android.unidirectionaldataflow.search.SearchStore
import com.mpierucci.android.unidirectionaldataflow.search.composables.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<SearchStore>()
        setContent {
            MaterialTheme {
                SearchScreen(store = viewModel)
            }
        }
    }
}