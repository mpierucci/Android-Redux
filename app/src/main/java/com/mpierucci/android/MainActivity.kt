package com.mpierucci.android

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.mpierucci.android.unidirectionaldataflow.search.SearchAction
import com.mpierucci.android.unidirectionaldataflow.search.SearchStore
import com.mpierucci.android.unidirectionaldataflow.search.composables.SearchToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<SearchStore>()
        setContent {
            SearchScreen(store = viewModel)
        }
    }
}


@Composable
fun SearchScreen(store: SearchStore) {


    /**
     * TODO because with redux we observe the state as a whole, new state wil cause
     * the composable to recompositios or is compose smart enough? Doesnt look like
     *
     */
    val state = store.state.collectAsState()
    //TODO maybe collect normally and split the priority in composable states
    Column {
        SearchToolbar(
            query = state.value.query,
            onQueryValueChanged = { querySlice ->
                store.dispatch(SearchAction.AppendSearchQuery(querySlice))
            },
            { searchQuery ->
                store.dispatch(SearchAction.Search(searchQuery))
            })
        Column {
            state.value.drinks.forEach {
                Log.e("Marco", "Composing drinks")
                Text(it.name)
            }
        }
    }
}