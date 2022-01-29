package com.mpierucci.android.redux.search.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mpierucci.android.redux.drink.domain.Drink
import com.mpierucci.android.redux.search.SearchAction
import com.mpierucci.android.redux.search.SearchState
import com.mpierucci.android.redux.search.SearchStore


@Composable
fun SearchScreen(
    store: SearchStore
) {
    val state by store.state.collectAsState()
    SearchScreen(
        state = state,
        onQueryChanged =  { querySlice ->
            store.dispatch(SearchAction.AppendSearchQuery(querySlice))
        },
        onSearch = { searchQuery ->
            store.dispatch(SearchAction.Search(searchQuery))
        }
    )
}

@Composable
private fun SearchScreen(
    state: SearchState,
    onQueryChanged : (String) -> Unit,
    onSearch: (String) -> Unit
) {
    val drinks = state.drinks
    Column {
        SearchToolbar(
            query = state.query,
            onQueryValueChanged = onQueryChanged,
            onSearch = onSearch
        )

        if (state.drinks.isEmpty()) {
            EmptySearch()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(drinks, { drink: Drink -> drink.id }) { drink ->
                    Row {
                        Image(
                            //TODO check
                            painter = rememberImagePainter(drink.thumbnail),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp)
                        )

                        Text(
                            text = drink.name,
                            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp),
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Composable
/*
Column is a inline function and thus not skipeable by default recomposition
So to make it skipable we create its own Composable. This might be a case of premature optimization..
 */
private fun EmptySearch() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
        Text(
            text = "Your  query yield  no results",
            color = MaterialTheme.colors.onBackground
        )
    }
}





